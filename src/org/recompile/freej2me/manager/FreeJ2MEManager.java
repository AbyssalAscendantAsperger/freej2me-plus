package org.recompile.freej2me.manager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.recompile.freej2me.session.*;

/*
	Session manager for running multiple isolated FreeJ2ME instances inside
	a single JVM. Each session gets its own ChildFirstFreeJ2MEClassLoader so
	that static globals (Mobile, MobilePlatform, Display, Manager, RMS, ...)
	are duplicated and do not collide across sessions.

	Communication with the outside world is done through in-memory queues
	(FramePacket / AudioPacket) so that no object type crosses classloader
	boundaries in a problematic way.

	v0.8 features:
	- Max concurrent sessions limit (maxConcurrentSessions).
	- Automatic idle session timeout cleanup daemon (sessionTimeoutMs).
	- High-level helper createSessionForJar(...) to launch JAR games easily.
*/
public class FreeJ2MEManager
{
	private final Map<String, LibretroEmbeddedSession> sessions = new ConcurrentHashMap<String, LibretroEmbeddedSession>();
	private final ClassLoader parentLoader;
	private final URL coreJarUrl;

	private volatile int maxConcurrentSessions = 16;
	private volatile long sessionTimeoutMs = 300_000L; // 5 phút
	private volatile boolean daemonRunning = true;

	public FreeJ2MEManager()
	{
		this.parentLoader = FreeJ2MEManager.class.getClassLoader();
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		this.coreJarUrl = url;
		startTimeoutCleanupDaemon();
	}

	public void setMaxConcurrentSessions(int max) { this.maxConcurrentSessions = Math.max(1, max); }
	public int getMaxConcurrentSessions() { return maxConcurrentSessions; }

	public void setSessionTimeoutMs(long timeoutMs) { this.sessionTimeoutMs = timeoutMs; }
	public long getSessionTimeoutMs() { return sessionTimeoutMs; }

	private void startTimeoutCleanupDaemon()
	{
		Thread t = new Thread(() -> {
			while(daemonRunning && !Thread.currentThread().isInterrupted())
			{
				try
				{
					Thread.sleep(15_000L); // Kiểm tra định kỳ mỗi 15 giây
					cleanupTimedOutSessions();
				}
				catch(InterruptedException e)
				{
					break;
				}
				catch(Exception e)
				{
					// Ignore
				}
			}
		}, "FreeJ2MEManager-IdleCleanupDaemon");
		t.setDaemon(true);
		t.start();
	}

	public int cleanupTimedOutSessions()
	{
		if(sessionTimeoutMs <= 0) return 0;
		long now = System.currentTimeMillis();
		int cleaned = 0;
		for(Map.Entry<String, LibretroEmbeddedSession> entry : sessions.entrySet())
		{
			LibretroEmbeddedSession s = entry.getValue();
			if(now - s.getLastActivityTime() > sessionTimeoutMs)
			{
				System.out.println("FreeJ2MEManager: Tự động hủy session '" + entry.getKey() + "' do timeout (" + sessionTimeoutMs + "ms idle)");
				destroySession(entry.getKey());
				cleaned++;
			}
		}
		return cleaned;
	}

	/*
		Tạo nhanh session dành riêng cho một file JAR với các cấu hình tối ưu sẵn.
	*/
	public String createSessionForJar(String sessionId, String jarPath, int width, int height, String dataDir)
	{
		return createSessionForJar(sessionId, jarPath, width, height, 0, "Standard", 30, true, dataDir, null);
	}

	public String createSessionForJar(String sessionId, String jarPath, int width, int height, int rotate, String phoneType, int fps, boolean sound, String dataDir, Map<String, String> props)
	{
		int phoneInt = 0; // Standard
		if("LG".equalsIgnoreCase(phoneType)) phoneInt = 1;
		else if("Motorola".equalsIgnoreCase(phoneType)) phoneInt = 2;
		else if("MotoTriplets".equalsIgnoreCase(phoneType)) phoneInt = 3;
		else if("MotoV8".equalsIgnoreCase(phoneType)) phoneInt = 4;
		else if("MotoA1000".equalsIgnoreCase(phoneType)) phoneInt = 5;
		else if("NokiaKeyboard".equalsIgnoreCase(phoneType)) phoneInt = 6;
		else if("Sagem".equalsIgnoreCase(phoneType)) phoneInt = 7;
		else if("Siemens".equalsIgnoreCase(phoneType)) phoneInt = 8;
		else if("Sharp".equalsIgnoreCase(phoneType)) phoneInt = 9;
		else if("SKT".equalsIgnoreCase(phoneType)) phoneInt = 10;
		else if("KDDI".equalsIgnoreCase(phoneType)) phoneInt = 11;

		String[] args = new String[]{
			String.valueOf(width),
			String.valueOf(height),
			String.valueOf(rotate / 90),
			String.valueOf(phoneInt),
			String.valueOf(fps),
			sound ? "1" : "0",
			"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"
		};

		String id = createSession(sessionId, dataDir, args, props);
		if(id != null)
		{
			LibretroEmbeddedSession s = getSession(id);
			if(s != null)
			{
				s.setDataPath(dataDir);
				s.loadJar(jarPath);
			}
		}
		return id;
	}

	/*
		Create a new session with isolated static state.
		Returns sessionId on success, null on failure.
	*/
	public String createSession(String sessionId, String dataDir, String[] args)
	{
		return createSession(sessionId, dataDir, args, null);
	}

	public String createSession(String sessionId, String dataDir, String[] args, Map<String, String> sessionProperties)
	{
		cleanupTimedOutSessions(); // Trước khi tạo mới, dọn dẹp các session đã hết hạn
		if(sessions.size() >= maxConcurrentSessions)
		{
			System.err.println("FreeJ2MEManager: Từ chối tạo session '" + sessionId + "' do vượt ngưỡng tối đa (" + maxConcurrentSessions + ")");
			return null;
		}

		if(sessions.containsKey(sessionId)) { return null; }
		try
		{
			File dir = new File(dataDir);
			if(!dir.isDirectory()) { dir.mkdirs(); }

			ChildFirstFreeJ2MEClassLoader loader = new ChildFirstFreeJ2MEClassLoader(new URL[]{coreJarUrl}, parentLoader);

			Class<?> sessionClass = loader.loadClass("org.recompile.freej2me.session.LibretroEmbeddedSession");

			Object sessionObj = sessionClass.getConstructor(String.class).newInstance(sessionId);
			LibretroEmbeddedSession session = (LibretroEmbeddedSession)sessionObj;

			QueueInputSource input = new QueueInputSource();
			QueueFrameSink frames = new QueueFrameSink();
			QueueAudioSink audio = new QueueAudioSink();

			Method setInput = sessionClass.getMethod("setInputSource", InputSource.class);
			Method setFrame = sessionClass.getMethod("setFrameSink", FrameSink.class);
			Method setAudio = sessionClass.getMethod("setAudioSink", AudioSink.class);
			setInput.invoke(session, input);
			setFrame.invoke(session, frames);
			setAudio.invoke(session, audio);

			Method setDataDir = sessionClass.getMethod("setDataDir", String.class);
			setDataDir.invoke(session, dataDir);

			if(sessionProperties != null)
			{
				Method setSessionProperties = sessionClass.getMethod("setSessionProperties", Map.class);
				setSessionProperties.invoke(session, sessionProperties);
			}

			Method start = sessionClass.getMethod("start", String[].class, ClassLoader.class);
			start.invoke(session, args, loader);

			sessions.put(sessionId, session);
			return sessionId;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean destroySession(String sessionId)
	{
		LibretroEmbeddedSession session = sessions.remove(sessionId);
		if(session == null) { return false; }
		try
		{
			session.stop();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public void shutdown()
	{
		daemonRunning = false;
		for(String id : sessions.keySet())
		{
			destroySession(id);
		}
	}

	public LibretroEmbeddedSession getSession(String sessionId)
	{
		LibretroEmbeddedSession s = sessions.get(sessionId);
		if(s != null) { s.touch(); }
		return s;
	}

	public boolean sendRaw(String sessionId, byte[] data)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			session.sendRaw(data);
			return true;
		}
		catch(Exception e) { return false; }
	}

	public boolean sendKey(String sessionId, int key, boolean down)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			if(down) { session.sendKeyDown(key); }
			else { session.sendKeyUp(key); }
			return true;
		}
		catch(Exception e) { return false; }
	}

	public boolean sendTouch(String sessionId, int x, int y, int state)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			if(state == 0) { session.sendPointerPressed(x, y); }
			else if(state == 1) { session.sendPointerReleased(x, y); }
			else { session.sendPointerDragged(x, y); }
			return true;
		}
		catch(Exception e) { return false; }
	}

	public boolean requestFrame(String sessionId)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			session.requestFrame();
			return true;
		}
		catch(Exception e) { return false; }
	}

	public FramePacket takeFrame(String sessionId)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return null; }
		try
		{
			FrameSink sink = session.getFrameSink();
			if(sink instanceof QueueFrameSink)
			{
				FramePacket pkt = ((QueueFrameSink)sink).poll();
				if(pkt != null) { session.touch(); }
				return pkt;
			}
			return null;
		}
		catch(Exception e) { return null; }
	}

	public AudioPacket takeAudio(String sessionId)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return null; }
		try
		{
			AudioSink sink = session.getAudioSink();
			if(sink instanceof QueueAudioSink)
			{
				AudioPacket pkt = ((QueueAudioSink)sink).poll();
				if(pkt != null) { session.touch(); }
				return pkt;
			}
			return null;
		}
		catch(Exception e) { return null; }
	}
}

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
*/
public class FreeJ2MEManager
{
	private final Map<String, LibretroEmbeddedSession> sessions = new ConcurrentHashMap<String, LibretroEmbeddedSession>();
	private final ClassLoader parentLoader;
	private final URL coreJarUrl;

	public FreeJ2MEManager()
	{
		this.parentLoader = FreeJ2MEManager.class.getClassLoader();
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		this.coreJarUrl = url;
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
		if(sessions.containsKey(sessionId)) { return null; }
		try
		{
			File dir = new File(dataDir);
			if(!dir.isDirectory()) { dir.mkdirs(); }

			ChildFirstFreeJ2MEClassLoader loader = new ChildFirstFreeJ2MEClassLoader(new URL[]{coreJarUrl}, parentLoader);

			// LibretroEmbeddedSession is in org.recompile.freej2me.session, which is
			// parent-first, so this effectively loads the class from the parent loader.
			// However, the session's Libretro / Mobile classes will be loaded from the child.
			Class<?> sessionClass = loader.loadClass("org.recompile.freej2me.session.LibretroEmbeddedSession");

			Object sessionObj = sessionClass.getConstructor(String.class).newInstance(sessionId);
			LibretroEmbeddedSession session = (LibretroEmbeddedSession)sessionObj;

			// Prepare queues
			QueueInputSource input = new QueueInputSource();
			QueueFrameSink frames = new QueueFrameSink();
			QueueAudioSink audio = new QueueAudioSink();

			// Inject sinks via reflection (cross-loader safe because the interfaces are parent-first)
			Method setInput = sessionClass.getMethod("setInputSource", InputSource.class);
			Method setFrame = sessionClass.getMethod("setFrameSink", FrameSink.class);
			Method setAudio = sessionClass.getMethod("setAudioSink", AudioSink.class);
			setInput.invoke(session, input);
			setFrame.invoke(session, frames);
			setAudio.invoke(session, audio);

			// Set per-session data directory
			Method setDataDir = sessionClass.getMethod("setDataDir", String.class);
			setDataDir.invoke(session, dataDir);

			if(sessionProperties != null)
			{
				Method setSessionProperties = sessionClass.getMethod("setSessionProperties", Map.class);
				setSessionProperties.invoke(session, sessionProperties);
			}

			// Start with the child loader as context
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

	public LibretroEmbeddedSession getSession(String sessionId)
	{
		return sessions.get(sessionId);
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

	/* Poll a frame packet from the session's queue. */
	public FramePacket takeFrame(String sessionId)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return null; }
		try
		{
			return session.getFrameSink().poll();
		}
		catch(Exception e) { return null; }
	}

	/* Poll an audio packet from the session's queue. */
	public AudioPacket takeAudio(String sessionId)
	{
		LibretroEmbeddedSession session = sessions.get(sessionId);
		if(session == null) { return null; }
		try
		{
			return session.getAudioSink().poll();
		}
		catch(Exception e) { return null; }
	}
}

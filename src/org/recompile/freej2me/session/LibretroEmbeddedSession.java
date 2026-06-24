package org.recompile.freej2me.session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.recompile.freej2me.Libretro;

/*
	Embeddable libretro runtime wrapper.

	v1.0 MAX ULTIMATE changes:
	- Fixed instanceof guard in setters so custom InputSource/FrameSink/AudioSink implementations are accepted.
	- Changed internal field types to interfaces (InputSource, FrameSink, AudioSink).
	- Added convenience constructor LibretroEmbeddedSession(sessionId, input, frames, audio).
	- Added lastActivityTime tracking (touch()) for automatic idle session timeout cleanup.
	- Bulletproof ThreadGroup Isolation: Intercepts crashes in ANY child thread via uncaughtException().
	- Flight Recorder Black Box (flightRecorder): Tracks last 32 input packets to replay crash triggers.
	- Automatic Crash Recovery (autoRestartOnCrash): Configurable respawn up to N times when game crashes.
	- Added SessionListener support for real-time error callbacks.
	- Guaranteed graceful resource cleanup (input, frames, audio streams) on crash or stop.
	- Kept standard thread interrupt stacktraces visible on console per user request.
*/
public final class LibretroEmbeddedSession
{
	private final String sessionId;
	private InputSource input;
	private FrameSink frames;
	private AudioSink audio;
	private String dataDir;
	private ThreadGroup threadGroup;
	private Thread thread;
	private volatile boolean running = false;
	private ClassLoader customLoader;
	private java.util.Map<String, String> sessionProperties;
	private volatile long lastActivityTime = System.currentTimeMillis();

	// Error recovery & Flight recorder state
	private volatile Throwable lastException = null;
	private volatile String crashReason = null;
	private SessionListener listener = null;
	private volatile boolean intentionallyStopped = false;
	private String[] savedArgs = null;

	// Auto-Recovery configuration
	private volatile boolean autoRestartOnCrash = false;
	private volatile int maxCrashRestarts = 3;
	private volatile int currentRestartCount = 0;

	// Black box flight recorder (tracks last 32 input packets)
	private final ConcurrentLinkedQueue<byte[]> flightRecorder = new ConcurrentLinkedQueue<byte[]>();
	private static final int MAX_FLIGHT_RECORDER_PACKETS = 32;

	public LibretroEmbeddedSession(String sessionId)
	{
		this(sessionId, null, null, null);
	}

	public LibretroEmbeddedSession(String sessionId, InputSource input, FrameSink frames, AudioSink audio)
	{
		this.sessionId = sessionId == null ? "session" : sessionId;
		this.input = input != null ? input : new QueueInputSource();
		this.frames = frames != null ? frames : new QueueFrameSink();
		this.audio = audio != null ? audio : new QueueAudioSink();
	}

	public String getSessionId() { return sessionId; }
	public InputSource getInputSource() { return input; }
	public FrameSink getFrameSink() { return frames; }
	public AudioSink getAudioSink() { return audio; }
	public boolean isRunning() { return running && !intentionallyStopped && lastException == null; }

	public Throwable getLastException() { return lastException; }
	public String getCrashReason() { return crashReason; }
	public void setSessionListener(SessionListener listener) { this.listener = listener; }

	public void setAutoRestartOnCrash(boolean autoRestart, int maxRestarts)
	{
		this.autoRestartOnCrash = autoRestart;
		this.maxCrashRestarts = Math.max(1, maxRestarts);
	}
	public boolean isAutoRestartOnCrash() { return autoRestartOnCrash; }
	public int getCurrentRestartCount() { return currentRestartCount; }

	public List<byte[]> getFlightRecorderHistory()
	{
		return new ArrayList<byte[]>(flightRecorder);
	}

	public void touch() { this.lastActivityTime = System.currentTimeMillis(); }
	public long getLastActivityTime() { return lastActivityTime; }

	public void setInputSource(InputSource input)
	{
		if(input != null) { this.input = input; }
	}

	public void setFrameSink(FrameSink frames)
	{
		if(frames != null) { this.frames = frames; }
	}

	public void setAudioSink(AudioSink audio)
	{
		if(audio != null) { this.audio = audio; }
	}

	public void setDataDir(String dir) { this.dataDir = dir; }
	public void setSessionProperties(java.util.Map<String, String> props) { this.sessionProperties = props; }

	public void start(final String[] args)
	{
		start(args, null);
	}

	public void start(final String[] args, final ClassLoader loader)
	{
		if(running) { return; }
		running = true;
		intentionallyStopped = false;
		lastException = null;
		crashReason = null;
		this.savedArgs = args != null ? args.clone() : null;
		touch();
		this.customLoader = loader;

		this.threadGroup = new ThreadGroup("fj2me-session-" + sessionId) {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				if(!running || intentionallyStopped) {
					super.uncaughtException(t, e);
					return;
				}
				// Đón đầu toàn bộ ngoại lệ văng ra từ bất kỳ luồng con nào trong game
				handleSessionCrash(e, "Uncaught exception in child thread '" + t.getName() + "'");
			}
		};

		thread = new Thread(threadGroup, new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					if(listener != null) { try { listener.onSessionStarted(sessionId); } catch(Throwable cb) {} }
					if(dataDir != null)
					{
						org.recompile.mobile.Mobile.setDataDir(dataDir);
					}
					if(loader != null)
					{
						Class<?> mobileClass = Class.forName("org.recompile.mobile.Mobile", true, loader);
						mobileClass.getMethod("initSessionProperties").invoke(null);
						if(sessionProperties != null)
						{
							for(java.util.Map.Entry<String, String> e : sessionProperties.entrySet())
							{
								mobileClass.getMethod("setSessionProperty", String.class, String.class).invoke(null, e.getKey(), e.getValue());
							}
						}
						mobileClass.getField("isManagedSession").setBoolean(null, true);
						Class<?> libretroClass = Class.forName("org.recompile.freej2me.Libretro", true, loader);
						Constructor<?> ctor = libretroClass.getConstructor(String[].class, InputSource.class, FrameSink.class, AudioSink.class);
						ctor.newInstance(args, input, frames, audio);
					}
					else
					{
						org.recompile.mobile.Mobile.initSessionProperties();
						if(sessionProperties != null)
						{
							for(java.util.Map.Entry<String, String> e : sessionProperties.entrySet())
							{
								org.recompile.mobile.Mobile.setSessionProperty(e.getKey(), e.getValue());
							}
						}
						new Libretro(args, input, frames, audio);
					}
				}
				catch(Throwable e)
				{
					handleSessionCrash(e, "Crash in main session thread");
				}
				finally
				{
					// Bootstrap thread đã hoàn tất việc nạp game.
					// Tuyệt đối KHÔNG đóng resource ở đây vì game tiếp tục chạy trên các luồng nền!
				}
			}
		}, "FreeJ2ME-LibretroSession-" + sessionId);
		thread.start();
	}

	private synchronized void handleSessionCrash(Throwable e, String context)
	{
		if(!running || intentionallyStopped || lastException != null) return;
		e.printStackTrace(); // Giữ nguyên in ra console theo đúng hành vi chuẩn và yêu cầu user
		running = false;
		lastException = e;
		crashReason = context + ": " + (e.getMessage() != null ? e.getMessage() : e.getClass().getName());

		if(listener != null) { try { listener.onSessionCrashed(sessionId, e); } catch(Throwable cb) {} }

		cleanupResourcesGracefully();

		if(autoRestartOnCrash && currentRestartCount < maxCrashRestarts && !intentionallyStopped)
		{
			currentRestartCount++;
			System.err.println("[" + sessionId + "] CRASH RECOVERY: Auto-restarting session (Attempt " + currentRestartCount + "/" + maxCrashRestarts + ") in 1500ms...");
			try { Thread.sleep(1500L); } catch(InterruptedException ie) {}
			lastException = null;
			crashReason = null;
			start(savedArgs, customLoader);
		}
	}

	public void stop()
	{
		intentionallyStopped = true;
		running = false;
		shutdownKnownSessionStatics();
		if(thread != null)
		{
			thread.interrupt();
		}
		if(threadGroup != null)
		{
			interruptAndJoinSessionThreads(300L);
			// Some MIDlets create non-cooperative game loops that catch InterruptedException
			// and continue forever.  Such live threads pin the child-first classloader and
			// all session statics.  After graceful shutdown has been requested, hard-stop
			// only the remaining threads in this isolated session ThreadGroup.
			if(Boolean.parseBoolean(System.getProperty("freej2me.sessionHardStop", "true")))
			{
				hardStopRemainingSessionThreads();
				interruptAndJoinSessionThreads(500L);
			}
		}
		cleanupResourcesGracefully();
		if(frames instanceof QueueFrameSink) { while(((QueueFrameSink)frames).poll() != null); }
		if(audio instanceof QueueAudioSink) { while(((QueueAudioSink)audio).poll() != null); }
		if(listener != null) { try { listener.onSessionStopped(sessionId); } catch(Throwable cb) {} }
	}

	private void interruptAndJoinSessionThreads(long joinMs)
	{
		if(threadGroup == null) { return; }
		Thread[] threads = new Thread[threadGroup.activeCount() * 2 + 20];
		int count = threadGroup.enumerate(threads, true);
		for(int i = 0; i < count; i++)
		{
			Thread t = threads[i];
			if(t != null && t != Thread.currentThread()) { t.interrupt(); }
		}
		for(int i = 0; i < count; i++)
		{
			Thread t = threads[i];
			if(t == null || t == Thread.currentThread()) { continue; }
			try { t.join(joinMs); }
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void hardStopRemainingSessionThreads()
	{
		if(threadGroup == null) { return; }
		Thread[] threads = new Thread[threadGroup.activeCount() * 2 + 20];
		int count = threadGroup.enumerate(threads, true);
		for(int i = 0; i < count; i++)
		{
			Thread t = threads[i];
			if(t == null || t == Thread.currentThread() || !t.isAlive()) { continue; }
			try
			{
				System.err.println("[" + sessionId + "] Hard-stopping non-cooperative session thread: " + t.getName());
				t.stop();
			}
			catch(Throwable ignored) { }
		}
	}

	private void shutdownKnownSessionStatics()
	{
		ClassLoader loader = customLoader;
		if(loader == null) { return; }
		try
		{
			Class<?> libretro = Class.forName("org.recompile.freej2me.Libretro", false, loader);
			libretro.getMethod("shutdownManagedSession").invoke(null);
		}
		catch(Throwable ignored) { }
		try
		{
			Class<?> display = Class.forName("javax.microedition.lcdui.Display", false, loader);
			display.getMethod("shutdownEventLoop").invoke(null);
		}
		catch(Throwable ignored) { }
		try
		{
			Class<?> audioPipe = Class.forName("org.recompile.mobile.AudioPipe", false, loader);
			audioPipe.getMethod("clearThreadLocalSink").invoke(null);
		}
		catch(Throwable ignored) { }
	}

	private void cleanupResourcesGracefully()
	{
		if(input != null) { try { input.close(); } catch(IOException e) { } }
		if(frames != null) { try { frames.close(); } catch(IOException e) { } }
		if(audio != null) { try { audio.close(); } catch(IOException e) { } }
	}

	public void sendRaw(byte[] bytes)
	{
		touch();
		recordFlightPacket(bytes);
		pushInput(bytes);
	}

	public void sendKeyDown(int keyIndex) { touch(); byte[] b = createCommand(3, keyIndex); recordFlightPacket(b); pushInput(b); }
	public void sendKeyUp(int keyIndex) { touch(); byte[] b = createCommand(2, keyIndex); recordFlightPacket(b); pushInput(b); }

	public void sendPointerReleased(int x, int y) { touch(); byte[] b = createPointerCommand(4, x, y); recordFlightPacket(b); pushInput(b); }
	public void sendPointerPressed(int x, int y) { touch(); byte[] b = createPointerCommand(5, x, y); recordFlightPacket(b); pushInput(b); }
	public void sendPointerDragged(int x, int y) { touch(); byte[] b = createPointerCommand(6, x, y); recordFlightPacket(b); pushInput(b); }

	public void loadJar(String path)
	{
		touch();
		byte[] data;
		try { data = path.getBytes("UTF-8"); }
		catch(UnsupportedEncodingException e) { data = path.getBytes(); }
		byte[] cmd = createCommand(10, data.length);
		recordFlightPacket(cmd);
		pushInput(cmd);
		pushInput(data);
	}

	public void setDataPath(String path)
	{
		touch();
		byte[] data;
		try { data = path.getBytes("UTF-8"); }
		catch(UnsupportedEncodingException e) { data = path.getBytes(); }
		byte[] cmd = createCommand(11, data.length);
		recordFlightPacket(cmd);
		pushInput(cmd);
		pushInput(data);
	}

	public void requestFrame()
	{
		requestFrame(0, false, false);
	}

	public void requestFrame(int fastForwardMultiplierScaled, boolean frontendPausedAck, boolean fastForward)
	{
		touch();
		byte[] b = new byte[5];
		b[0] = 15;
		b[1] = (byte)((fastForwardMultiplierScaled >> 8) & 0xFF);
		b[2] = (byte)(fastForwardMultiplierScaled & 0xFF);
		b[3] = (byte)(frontendPausedAck ? 1 : 0);
		b[4] = (byte)(fastForward ? 1 : 0);
		pushInput(b);
	}

	private void recordFlightPacket(byte[] pkt)
	{
		if(pkt != null && pkt.length > 0)
		{
			flightRecorder.offer(pkt.clone());
			while(flightRecorder.size() > MAX_FLIGHT_RECORDER_PACKETS)
			{
				flightRecorder.poll();
			}
		}
	}

	private byte[] createCommand(int command, int value)
	{
		byte[] b = new byte[5];
		b[0] = (byte)(command & 0xFF);
		b[1] = (byte)((value >> 24) & 0xFF);
		b[2] = (byte)((value >> 16) & 0xFF);
		b[3] = (byte)((value >> 8) & 0xFF);
		b[4] = (byte)(value & 0xFF);
		return b;
	}

	private byte[] createPointerCommand(int command, int x, int y)
	{
		byte[] b = new byte[5];
		b[0] = (byte)(command & 0xFF);
		b[1] = (byte)((x >> 8) & 0xFF);
		b[2] = (byte)(x & 0xFF);
		b[3] = (byte)((y >> 8) & 0xFF);
		b[4] = (byte)(y & 0xFF);
		return b;
	}

	private void pushInput(byte[] bytes)
	{
		if(input instanceof QueueInputSource)
		{
			((QueueInputSource)input).push(bytes);
		}
	}
}

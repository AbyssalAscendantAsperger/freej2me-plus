package org.recompile.freej2me.session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;

import org.recompile.freej2me.Libretro;

/*
	Embeddable libretro runtime wrapper.

	v0.8 changes:
	- Fixed instanceof guard in setters so custom InputSource/FrameSink/AudioSink implementations are accepted.
	- Changed internal field types to interfaces (InputSource, FrameSink, AudioSink).
	- Added convenience constructor LibretroEmbeddedSession(sessionId, input, frames, audio).
	- Updated push helpers and stop() cleanup to handle interface types safely.
	- Added lastActivityTime tracking (touch()) for automatic idle session timeout cleanup.
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
	public boolean isRunning() { return running; }

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
		touch();
		this.customLoader = loader;
		this.threadGroup = new ThreadGroup("fj2me-session-" + sessionId);
		thread = new Thread(threadGroup, new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
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
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					running = false;
				}
			}
		}, "FreeJ2ME-LibretroSession-" + sessionId);
		thread.start();
	}

	public void stop()
	{
		running = false;
		if(input != null) { try { input.close(); } catch(IOException e) { } }
		if(frames != null) { try { frames.close(); } catch(IOException e) { } }
		if(audio != null) { try { audio.close(); } catch(IOException e) { } }
		if(thread != null)
		{
			thread.interrupt();
		}
		if(threadGroup != null)
		{
			Thread[] threads = new Thread[threadGroup.activeCount() * 2 + 10];
			int count = threadGroup.enumerate(threads);
			for(int i = 0; i < count; i++)
			{
				threads[i].interrupt();
			}
		}
		// Drain queues so the session doesn't hold references
		if(input != null) { try { input.close(); } catch(IOException e) { } }
		if(frames instanceof QueueFrameSink) { while(((QueueFrameSink)frames).poll() != null); }
		if(audio instanceof QueueAudioSink) { while(((QueueAudioSink)audio).poll() != null); }
	}

	public void sendRaw(byte[] bytes)
	{
		touch();
		pushInput(bytes);
	}

	public void sendKeyDown(int keyIndex) { touch(); sendCommandInt(3, keyIndex); }
	public void sendKeyUp(int keyIndex) { touch(); sendCommandInt(2, keyIndex); }

	public void sendPointerReleased(int x, int y) { touch(); sendPointerCommand(4, x, y); }
	public void sendPointerPressed(int x, int y) { touch(); sendPointerCommand(5, x, y); }
	public void sendPointerDragged(int x, int y) { touch(); sendPointerCommand(6, x, y); }

	public void loadJar(String path)
	{
		touch();
		byte[] data;
		try { data = path.getBytes("UTF-8"); }
		catch(UnsupportedEncodingException e) { data = path.getBytes(); }
		sendCommandInt(10, data.length);
		pushInput(data);
	}

	public void setDataPath(String path)
	{
		touch();
		byte[] data;
		try { data = path.getBytes("UTF-8"); }
		catch(UnsupportedEncodingException e) { data = path.getBytes(); }
		sendCommandInt(11, data.length);
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

	private void sendCommandInt(int command, int value)
	{
		byte[] b = new byte[5];
		b[0] = (byte)(command & 0xFF);
		b[1] = (byte)((value >> 24) & 0xFF);
		b[2] = (byte)((value >> 16) & 0xFF);
		b[3] = (byte)((value >> 8) & 0xFF);
		b[4] = (byte)(value & 0xFF);
		pushInput(b);
	}

	private void sendPointerCommand(int command, int x, int y)
	{
		byte[] b = new byte[5];
		b[0] = (byte)(command & 0xFF);
		b[1] = (byte)((x >> 8) & 0xFF);
		b[2] = (byte)(x & 0xFF);
		b[3] = (byte)((y >> 8) & 0xFF);
		b[4] = (byte)(y & 0xFF);
		pushInput(b);
	}

	private void pushInput(byte[] bytes)
	{
		if(input instanceof QueueInputSource)
		{
			((QueueInputSource)input).push(bytes);
		}
	}
}

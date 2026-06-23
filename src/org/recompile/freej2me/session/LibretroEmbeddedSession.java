package org.recompile.freej2me.session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.recompile.freej2me.Libretro;

/*
	Embeddable libretro runtime wrapper.

	IMPORTANT: this class is the I/O/session foundation only. The current FreeJ2ME
	core still contains many static globals (Mobile, MobilePlatform, Display,
	MIDletLoader, Manager, RMS, ...). For true multi-session inside one JVM, create
	each LibretroEmbeddedSession through an isolated child-first ClassLoader, or
	continue refactoring those globals into a real per-session context.
*/
public final class LibretroEmbeddedSession
{
	private final String sessionId;
	private final QueueInputSource input;
	private final QueueFrameSink frames;
	private Thread thread;
	private volatile boolean running = false;

	public LibretroEmbeddedSession(String sessionId)
	{
		this.sessionId = sessionId == null ? "session" : sessionId;
		this.input = new QueueInputSource();
		this.frames = new QueueFrameSink();
	}

	public String getSessionId() { return sessionId; }
	public QueueInputSource getInputSource() { return input; }
	public QueueFrameSink getFrameSink() { return frames; }
	public boolean isRunning() { return running; }

	public void start(final String[] args)
	{
		if(running) { return; }
		running = true;
		thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try { new Libretro(args, input, frames); }
				finally { running = false; }
			}
		}, "FreeJ2ME-LibretroSession-" + sessionId);
		thread.start();
	}

	public void stop()
	{
		running = false;
		try { input.close(); } catch(IOException e) { }
		try { frames.close(); } catch(IOException e) { }
		if(thread != null) { thread.interrupt(); }
	}

	public void sendRaw(byte[] bytes)
	{
		input.push(bytes);
	}

	public void sendKeyDown(int keyIndex) { sendCommandInt(3, keyIndex); }
	public void sendKeyUp(int keyIndex) { sendCommandInt(2, keyIndex); }

	public void sendPointerReleased(int x, int y) { sendPointerCommand(4, x, y); }
	public void sendPointerPressed(int x, int y) { sendPointerCommand(5, x, y); }
	public void sendPointerDragged(int x, int y) { sendPointerCommand(6, x, y); }

	public void loadJar(String path)
	{
		byte[] data;
		try { data = path.getBytes("UTF-8"); }
		catch(UnsupportedEncodingException e) { data = path.getBytes(); }
		sendCommandInt(10, data.length);
		input.push(data);
	}

	public void setDataPath(String path)
	{
		byte[] data;
		try { data = path.getBytes("UTF-8"); }
		catch(UnsupportedEncodingException e) { data = path.getBytes(); }
		sendCommandInt(11, data.length);
		input.push(data);
	}

	public void requestFrame()
	{
		requestFrame(0, false, false);
	}

	public void requestFrame(int fastForwardMultiplierScaled, boolean frontendPausedAck, boolean fastForward)
	{
		byte[] b = new byte[5];
		b[0] = 15;
		b[1] = (byte)((fastForwardMultiplierScaled >> 8) & 0xFF);
		b[2] = (byte)(fastForwardMultiplierScaled & 0xFF);
		b[3] = (byte)(frontendPausedAck ? 1 : 0);
		b[4] = (byte)(fastForward ? 1 : 0);
		input.push(b);
	}

	private void sendCommandInt(int command, int value)
	{
		byte[] b = new byte[5];
		b[0] = (byte)(command & 0xFF);
		b[1] = (byte)((value >> 24) & 0xFF);
		b[2] = (byte)((value >> 16) & 0xFF);
		b[3] = (byte)((value >> 8) & 0xFF);
		b[4] = (byte)(value & 0xFF);
		input.push(b);
	}

	private void sendPointerCommand(int command, int x, int y)
	{
		byte[] b = new byte[5];
		b[0] = (byte)(command & 0xFF);
		b[1] = (byte)((x >> 8) & 0xFF);
		b[2] = (byte)(x & 0xFF);
		b[3] = (byte)((y >> 8) & 0xFF);
		b[4] = (byte)(y & 0xFF);
		input.push(b);
	}
}

package org.recompile.freej2me.session;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
	FrameSink for embedded/session-managed mode.
	A future WebSocket sender can consume packets from this queue and route them
	to the correct user/session.
*/
public final class QueueFrameSink implements FrameSink
{
	private final BlockingQueue<FramePacket> frames = new LinkedBlockingQueue<FramePacket>(5);
	private volatile boolean ready = false;
	private volatile boolean closed = false;

	@Override
	public void ready() throws IOException { ready = true; }

	@Override
	public void sendFrame(byte[] header, int headerOffset, int headerLength, byte[] rgb, int rgbOffset, int rgbLength) throws IOException
	{
		if(closed) { throw new IOException("Frame sink is closed"); }
		byte[] h = new byte[headerLength];
		System.arraycopy(header, headerOffset, h, 0, headerLength);
		byte[] r = new byte[rgbLength];
		System.arraycopy(rgb, rgbOffset, r, 0, rgbLength);
		frames.offer(new FramePacket(h, r, rgbLength));
	}

	public boolean isReady() { return ready; }

	public FramePacket take() throws InterruptedException { return frames.take(); }

	public FramePacket poll() { return frames.poll(); }

	@Override
	public void flush() throws IOException { }

	@Override
	public void close() throws IOException { closed = true; }
}

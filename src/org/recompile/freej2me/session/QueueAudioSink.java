package org.recompile.freej2me.session;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.sound.sampled.AudioFormat;

/*
	AudioSink for embedded/session-managed mode.
	Audio packets are queued for consumption by a manager/WebSocket transport.
*/
public final class QueueAudioSink implements AudioSink
{
	private final BlockingQueue<AudioPacket> queue = new LinkedBlockingQueue<AudioPacket>(128);
	private volatile boolean closed = false;

	@Override
	public void sendFormat(AudioFormat format) throws IOException
	{
		if(closed) { throw new IOException("Audio sink is closed"); }
		queue.offer(new AudioPacket(format));
	}

	@Override
	public void sendPcm(AudioFormat format, byte[] data, int off, int len) throws IOException
	{
		if(closed) { throw new IOException("Audio sink is closed"); }
		byte[] copy = new byte[len];
		System.arraycopy(data, off, copy, 0, len);
		queue.offer(new AudioPacket(format, copy, 0, len));
	}

	public AudioPacket take() throws InterruptedException
	{
		return queue.take();
	}

	public AudioPacket poll()
	{
		return queue.poll();
	}

	public boolean isEmpty()
	{
		return queue.isEmpty();
	}

	@Override
	public void flush() throws IOException { }

	@Override
	public void close() throws IOException
	{
		closed = true;
		queue.offer(new AudioPacket(null, null, 0, 0)); // sentinel
	}
}

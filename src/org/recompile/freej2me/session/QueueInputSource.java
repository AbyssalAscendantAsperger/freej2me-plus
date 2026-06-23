package org.recompile.freej2me.session;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
	InputSource backed by an in-memory byte queue.
	Future WebSocket/session managers can push the same 5-byte libretro commands
	without touching System.in.
*/
public final class QueueInputSource implements InputSource
{
	private static final int EOF = -1;
	private final BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
	private volatile boolean closed = false;

	public void push(byte[] data)
	{
		if(data == null || closed) { return; }
		for(int i = 0; i < data.length; i++) { queue.offer(Integer.valueOf(data[i] & 0xFF)); }
	}

	public void push(byte[] data, int offset, int length)
	{
		if(data == null || closed) { return; }
		int end = Math.min(data.length, offset + length);
		for(int i = Math.max(0, offset); i < end; i++) { queue.offer(Integer.valueOf(data[i] & 0xFF)); }
	}

	public void pushByte(int value)
	{
		if(!closed) { queue.offer(Integer.valueOf(value & 0xFF)); }
	}

	@Override
	public int read() throws IOException
	{
		try
		{
			Integer value = queue.take();
			return value.intValue() == EOF ? -1 : value.intValue();
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new IOException("Interrupted while waiting for queued input", e);
		}
	}

	@Override
	public int read(byte[] buffer) throws IOException { return read(buffer, 0, buffer.length); }

	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException
	{
		if(buffer == null) { throw new NullPointerException("buffer"); }
		if(length == 0) { return 0; }

		int first = read();
		if(first < 0) { return -1; }
		buffer[offset] = (byte)first;
		int count = 1;
		while(count < length)
		{
			Integer value = queue.poll();
			if(value == null) { break; }
			if(value.intValue() == EOF) { closed = true; break; }
			buffer[offset + count] = (byte)(value.intValue() & 0xFF);
			count++;
		}
		return count;
	}

	@Override
	public void close() throws IOException
	{
		closed = true;
		queue.offer(Integer.valueOf(EOF));
	}
}

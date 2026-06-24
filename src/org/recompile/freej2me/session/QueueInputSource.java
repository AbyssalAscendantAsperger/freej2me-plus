package org.recompile.freej2me.session;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
	InputSource backed by an in-memory byte packet queue.
	Future WebSocket/session managers can push libretro commands
	without touching System.in and with thread-safe anti-key-spam backpressure.
*/
public final class QueueInputSource implements InputSource
{
	private static final byte[] EOF_PACKET = new byte[0];
	private final BlockingQueue<byte[]> queue = new LinkedBlockingQueue<byte[]>(2048);
	private volatile boolean closed = false;
	private byte[] currentPacket = null;
	private int currentOffset = 0;

	public void push(byte[] data)
	{
		if(data == null || closed) { return; }
		push(data, 0, data.length);
	}

	public void push(byte[] data, int offset, int length)
	{
		if(data == null || closed || length <= 0) { return; }
		byte[] copy = new byte[length];
		System.arraycopy(data, offset, copy, 0, length);
		offerPacket(copy);
	}

	public void pushByte(int value)
	{
		if(!closed) { offerPacket(new byte[]{(byte)(value & 0xFF)}); }
	}

	private void offerPacket(byte[] pkt)
	{
		if(pkt.length > 0)
		{
			int cmd = pkt[0] & 0xFF;
			int qSize = queue.size();

			// Rule 1: Drop touch move (cmd=6) if queue starts to back up
			if(cmd == 6 && qSize >= 32) { return; }

			// Rule 2: Drop frame request (cmd=15) if queue has many items
			if(cmd == 15 && qSize >= 128) { return; }

			// Rule 3: Drop press events (cmd=3 or cmd=5) if queue is critically flooded
			if((cmd == 3 || cmd == 5) && qSize >= 1500) { return; }
		}

		if(!queue.offer(pkt))
		{
			// Queue capacity reached.
			// Guarantee that release events (KeyUp=2, TouchUp=4) and lifecycle commands (>=10) NEVER get lost
			if(pkt.length > 0)
			{
				int cmd = pkt[0] & 0xFF;
				if(cmd == 2 || cmd == 4 || cmd >= 10)
				{
					while(!queue.offer(pkt))
					{
						byte[] removed = queue.poll();
						if(removed == null) { break; }
					}
				}
			}
		}
	}

	@Override
	public int read() throws IOException
	{
		if(currentPacket == null || currentOffset >= currentPacket.length)
		{
			if(closed && queue.isEmpty())
			{
				return -1;
			}
			try
			{
				currentPacket = queue.take();
				currentOffset = 0;
				if(currentPacket == EOF_PACKET)
				{
					closed = true;
					currentPacket = null;
					return -1;
				}
			}
			catch(InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new IOException("Interrupted while waiting for queued input", e);
			}
		}
		return currentPacket[currentOffset++] & 0xFF;
	}

	@Override
	public int read(byte[] buffer) throws IOException { return read(buffer, 0, buffer.length); }

	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException
	{
		if(buffer == null) { throw new NullPointerException("buffer"); }
		if(length == 0) { return 0; }

		int count = 0;
		while(count < length)
		{
			if(currentPacket == null || currentOffset >= currentPacket.length)
			{
				if(closed && queue.isEmpty())
				{
					return count == 0 ? -1 : count;
				}
				if(count > 0 && queue.isEmpty())
				{
					break;
				}
				try
				{
					currentPacket = queue.take();
					currentOffset = 0;
					if(currentPacket == EOF_PACKET)
					{
						closed = true;
						currentPacket = null;
						return count == 0 ? -1 : count;
					}
				}
				catch(InterruptedException e)
				{
					Thread.currentThread().interrupt();
					throw new IOException("Interrupted while waiting for queued input", e);
				}
			}
			int toCopy = Math.min(length - count, currentPacket.length - currentOffset);
			System.arraycopy(currentPacket, currentOffset, buffer, offset + count, toCopy);
			currentOffset += toCopy;
			count += toCopy;
		}
		return count;
	}

	@Override
	public void close() throws IOException
	{
		closed = true;
		queue.offer(EOF_PACKET);
	}
}

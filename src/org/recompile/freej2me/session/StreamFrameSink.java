package org.recompile.freej2me.session;

import java.io.IOException;
import java.io.OutputStream;

public final class StreamFrameSink implements FrameSink
{
	private static final byte[] READY = new byte[]{ '+', 'R', 'E', 'A', 'D', 'Y', '\n' };
	private final OutputStream out;

	public StreamFrameSink(OutputStream out)
	{
		if(out == null) { throw new NullPointerException("OutputStream cannot be null"); }
		this.out = out;
	}

	@Override
	public synchronized void ready() throws IOException
	{
		out.write(READY);
		out.flush();
	}

	@Override
	public synchronized void sendFrame(byte[] header, int headerOffset, int headerLength, byte[] rgb, int rgbOffset, int rgbLength) throws IOException
	{
		out.write(header, headerOffset, headerLength);
		out.write(rgb, rgbOffset, rgbLength);
		out.flush();
	}

	@Override
	public synchronized void flush() throws IOException { out.flush(); }

	@Override
	public void close() throws IOException { out.close(); }
}

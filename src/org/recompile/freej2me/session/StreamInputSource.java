package org.recompile.freej2me.session;

import java.io.IOException;
import java.io.InputStream;

public final class StreamInputSource implements InputSource
{
	private final InputStream in;

	public StreamInputSource(InputStream in)
	{
		if(in == null) { throw new NullPointerException("InputStream cannot be null"); }
		this.in = in;
	}

	@Override
	public int read() throws IOException { return in.read(); }

	@Override
	public int read(byte[] buffer) throws IOException { return in.read(buffer); }

	@Override
	public int read(byte[] buffer, int offset, int length) throws IOException { return in.read(buffer, offset, length); }

	@Override
	public void close() throws IOException { in.close(); }
}

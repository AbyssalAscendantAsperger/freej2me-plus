package org.recompile.freej2me.session;

import java.io.IOException;
import java.io.OutputStream;
import javax.sound.sampled.AudioFormat;

/*
	Stream-backed AudioSink using the FJ2A binary protocol.
	This is the direct replacement for AudioPipe's System.err output.
*/
public final class StreamAudioSink implements AudioSink
{
	private static final byte[] MAGIC = new byte[]{ 'F', 'J', '2', 'A' };
	private static final int TYPE_FORMAT = 1;
	private static final int TYPE_PCM = 2;

	private final OutputStream out;
	private AudioFormat currentFormat;
	private final Object lock = new Object();

	public StreamAudioSink(OutputStream out)
	{
		this.out = out;
	}

	@Override
	public void sendFormat(AudioFormat format) throws IOException
	{
		if(format == null) { return; }
		synchronized(lock)
		{
			if(sameFormat(currentFormat, format)) { return; }
			currentFormat = format;
			out.write(MAGIC);
			out.write(TYPE_FORMAT);
			writeInt(out, (int)format.getSampleRate());
			out.write(format.getChannels() & 0xFF);
			out.write(format.getSampleSizeInBits() & 0xFF);
			out.write(AudioFormat.Encoding.PCM_SIGNED.equals(format.getEncoding()) ? 1 : 0);
			out.write(format.isBigEndian() ? 1 : 0);
			out.flush();
		}
	}

	@Override
	public void sendPcm(AudioFormat format, byte[] data, int off, int len) throws IOException
	{
		if(data == null || len <= 0) { return; }
		synchronized(lock)
		{
			sendFormat(format);
			out.write(MAGIC);
			out.write(TYPE_PCM);
			writeInt(out, len);
			out.write(data, off, len);
			out.flush();
		}
	}

	@Override
	public void flush() throws IOException
	{
		synchronized(lock) { out.flush(); }
	}

	@Override
	public void close() throws IOException
	{
		synchronized(lock) { out.close(); }
	}

	private static boolean sameFormat(AudioFormat a, AudioFormat b)
	{
		if(a == b) { return true; }
		if(a == null || b == null) { return false; }
		return a.getSampleRate() == b.getSampleRate()
			&& a.getChannels() == b.getChannels()
			&& a.getSampleSizeInBits() == b.getSampleSizeInBits()
			&& a.isBigEndian() == b.isBigEndian()
			&& a.getEncoding().equals(b.getEncoding());
	}

	private static void writeInt(OutputStream out, int value) throws IOException
	{
		out.write((value >>> 24) & 0xFF);
		out.write((value >>> 16) & 0xFF);
		out.write((value >>> 8) & 0xFF);
		out.write(value & 0xFF);
	}
}

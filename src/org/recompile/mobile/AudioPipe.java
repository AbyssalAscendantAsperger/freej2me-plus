/*
	Audio pipe support for FreeJ2ME libretro/web bridge.
	When enabled with -Dfreej2me.audioPipe=1, raw PCM packets are written to stderr
	using the FJ2A protocol used by freej2me-lr-audiopipe-v2-debug.jar.
*/
package org.recompile.mobile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;

public final class AudioPipe
{
	private static final byte[] MAGIC = new byte[]{ 'F', 'J', '2', 'A' };
	private static final int TYPE_FORMAT = 1;
	private static final int TYPE_PCM = 2;
	private static final Object LOCK = new Object();

	private static AudioFormat currentFormat;
	private static boolean announced = false;

	private AudioPipe() { }

	public static boolean enabled()
	{
		boolean enabled = "1".equals(System.getProperty("freej2me.audioPipe", "0"));
		if(enabled && !announced)
		{
			announced = true;
			try
			{
				System.err.println("[AudioPipe] enabled");
				System.err.flush();
			}
			catch(Exception e) { }
		}
		return enabled;
	}

	public static void setFormat(AudioFormat format)
	{
		if(!enabled() || format == null) { return; }

		synchronized(LOCK)
		{
			if(sameFormat(currentFormat, format)) { return; }
			currentFormat = format;
			try
			{
				try
				{
					System.err.println("[AudioPipe] format " + (int)format.getSampleRate() + "Hz ch=" + format.getChannels() + " bits=" + format.getSampleSizeInBits());
				}
				catch(Exception e) { }

				PrintStream out = System.err;
				((OutputStream)out).write(MAGIC);
				((OutputStream)out).write(TYPE_FORMAT);
				writeInt(out, (int)format.getSampleRate());
				((OutputStream)out).write(format.getChannels() & 0xFF);
				((OutputStream)out).write(format.getSampleSizeInBits() & 0xFF);
				((OutputStream)out).write(AudioFormat.Encoding.PCM_SIGNED.equals(format.getEncoding()) ? 1 : 0);
				((OutputStream)out).write(format.isBigEndian() ? 1 : 0);
				((OutputStream)out).flush();
			}
			catch(IOException e) { }
		}
	}

	public static void writePcm(AudioFormat format, byte[] data, int offset, int length)
	{
		if(!enabled() || data == null || length <= 0) { return; }

		synchronized(LOCK)
		{
			setFormat(format);
			try
			{
				PrintStream out = System.err;
				((OutputStream)out).write(MAGIC);
				((OutputStream)out).write(TYPE_PCM);
				writeInt(out, length);
				((OutputStream)out).write(data, offset, length);
				((OutputStream)out).flush();
			}
			catch(IOException e) { }
		}
	}

	public static void paceBytes(AudioFormat format, int bytes)
	{
		if(format == null || bytes <= 0) { return; }
		float frameRate = format.getFrameRate();
		int frameSize = format.getFrameSize();
		if(frameRate <= 0.0f || frameSize <= 0) { return; }

		long nanos = (long)(((double)bytes / (double)frameSize) * 1000000000.0 / (double)frameRate);
		if(nanos <= 0) { return; }
		try
		{
			long millis = nanos / 1000000L;
			int ns = (int)(nanos % 1000000L);
			if(millis > 0 || ns > 0) { Thread.sleep(millis, ns); }
		}
		catch(InterruptedException e) { Thread.currentThread().interrupt(); }
	}

	public static void playTone(final int note, final int duration, final int volume)
	{
		if(!enabled()) { return; }

		final AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 1, 2, 44100.0f, false);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int effectiveDuration = Math.max(50, duration);
				int totalSamples = (int)((format.getSampleRate() * effectiveDuration) / 1000.0);
				int chunkSamples = 1024;
				double freq = 440.0 * Math.pow(2.0, (double)(note - 69) / 12.0);
				double phase = 0.0;
				double step = Math.PI * 2 * freq / format.getSampleRate();
				double amp = Math.max(0.0, Math.min(1.0, (double)volume / 100.0)) * 0.35;
				byte[] buffer = new byte[chunkSamples * 2];

				AudioPipe.setFormat(format);
				for(int written = 0; written < totalSamples; )
				{
					int n = Math.min(chunkSamples, totalSamples - written);
					for(int i = 0; i < n; i++)
					{
						short sample = (short)((Math.sin(phase) >= 0.0 ? 1.0 : -1.0) * amp * 32767.0);
						buffer[i * 2] = (byte)(sample & 0xFF);
						buffer[i * 2 + 1] = (byte)((sample >>> 8) & 0xFF);
						phase += step;
					}
					AudioPipe.writePcm(format, buffer, 0, n * 2);
					AudioPipe.paceBytes(format, n * 2);
					written += n;
				}
			}
		}, "FreeJ2ME-Tone-Pipe").start();
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

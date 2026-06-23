/*
	MIDI-to-PCM helper for AudioPipe. It uses the JRE software synthesizer's
	openStream(AudioFormat, Map) method when available, then pumps synthesized PCM
	through AudioPipe using the same FJ2A protocol as freej2me-lr-audiopipe-v2-debug.jar.
*/
package org.recompile.mobile;

import java.lang.reflect.Method;
import java.util.HashMap;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public final class AudioPipeMidi
{
	private static final AudioFormat FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0f, 16, 2, 4, 44100.0f, false);

	private AudioPipeMidi() { }

	public static Handle open(Soundbank soundbank) throws Exception
	{
		try { System.err.println("[AudioPipeMidi] open requested"); System.err.flush(); } catch(Exception e) { }

		Synthesizer synth = MidiSystem.getSynthesizer();
		Method openStream = null;
		Class<?> clazz = synth.getClass();
		while(clazz != null && openStream == null)
		{
			try { openStream = clazz.getMethod("openStream", AudioFormat.class, java.util.Map.class); }
			catch(NoSuchMethodException e) { clazz = clazz.getSuperclass(); }
		}
		if(openStream == null)
		{
			Method[] methods = synth.getClass().getMethods();
			for(int i = 0; i < methods.length; i++)
			{
				if("openStream".equals(methods[i].getName()) && methods[i].getParameterTypes().length == 2)
				{
					openStream = methods[i];
					break;
				}
			}
		}
		if(openStream == null)
		{
			try { System.err.println("[AudioPipeMidi] openStream method not found on " + synth.getClass().getName()); System.err.flush(); } catch(Exception e) { }
			throw new IllegalStateException("JRE synthesizer does not support openStream");
		}

		try { openStream.setAccessible(true); } catch(Exception e) { }
		AudioInputStream stream = (AudioInputStream)openStream.invoke(synth, FORMAT, new HashMap<Object, Object>());
		try { System.err.println("[AudioPipeMidi] stream opened fmt=" + stream.getFormat()); System.err.flush(); } catch(Exception e) { }

		try { if(soundbank != null) { synth.loadAllInstruments(soundbank); } } catch(Exception e) { }

		Handle h = new Handle();
		h.synth = synth;
		h.stream = stream;
		h.receiver = synth.getReceiver();
		h.format = stream.getFormat() == null ? FORMAT : stream.getFormat();
		AudioPipe.setFormat(h.format);
		return h;
	}

	public static final class Handle
	{
		public Synthesizer synth;
		public AudioInputStream stream;
		public Receiver receiver;
		public AudioFormat format;
		public volatile boolean running;
		public Thread pumpThread;

		public void startPump(String name)
		{
			if(stream == null || format == null || pumpThread != null) { return; }
			running = true;
			pumpThread = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					byte[] buffer = new byte[4096];
					try
					{
						int n;
						AudioPipe.setFormat(format);
						while(running && (n = stream.read(buffer, 0, buffer.length)) > 0)
						{
							AudioPipe.writePcm(format, buffer, 0, n);
							AudioPipe.paceBytes(format, n);
						}
					}
					catch(Exception e) { }
				}
			}, name == null ? "FreeJ2ME-MIDI-Pipe" : name);
			pumpThread.setDaemon(true);
			pumpThread.start();
		}

		public void stopPump()
		{
			running = false;
			if(pumpThread != null) { pumpThread.interrupt(); }
			pumpThread = null;
		}

		public void close()
		{
			stopPump();
			try { if(receiver != null) { receiver.close(); } } catch(Exception e) { }
			try { if(stream != null) { stream.close(); } } catch(Exception e) { }
			try { if(synth != null) { synth.close(); } } catch(Exception e) { }
			receiver = null;
			stream = null;
			synth = null;
		}
	}
}

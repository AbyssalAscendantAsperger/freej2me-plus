/*
	Audio output abstraction for FreeJ2ME runtimes.
	Allows routing raw PCM/format packets to a per-session sink instead of
	process-wide System.err.
*/
package org.recompile.freej2me.session;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;

public interface AudioSink
{
	public void sendFormat(AudioFormat format) throws IOException;
	public void sendPcm(AudioFormat format, byte[] data, int off, int len) throws IOException;
	public void flush() throws IOException;
	public void close() throws IOException;
}

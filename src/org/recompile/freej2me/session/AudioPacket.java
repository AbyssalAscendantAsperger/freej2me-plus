/*
	Audio packet container for per-session routing.
*/
package org.recompile.freej2me.session;

import javax.sound.sampled.AudioFormat;

public final class AudioPacket
{
	public final AudioFormat format;
	public final byte[] data;
	public final int off;
	public final int len;
	public final boolean isFormat;

	public AudioPacket(AudioFormat format)
	{
		this.format = format;
		this.data = null;
		this.off = 0;
		this.len = 0;
		this.isFormat = true;
	}

	public AudioPacket(AudioFormat format, byte[] data, int off, int len)
	{
		this.format = format;
		this.data = data;
		this.off = off;
		this.len = len;
		this.isFormat = false;
	}
}

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

	/* Serialize this packet into the FJ2A wire format. */
	public byte[] toFJ2ABytes()
	{
		if(isFormat)
		{
			if(format == null) { return null; }
			byte[] magic = new byte[]{'F','J','2','A'};
			byte[] type = new byte[]{1}; // FORMAT
			byte[] sr = new byte[4];
			int sampleRate = (int)format.getSampleRate();
			sr[0] = (byte)((sampleRate >>> 24) & 0xFF);
			sr[1] = (byte)((sampleRate >>> 16) & 0xFF);
			sr[2] = (byte)((sampleRate >>> 8) & 0xFF);
			sr[3] = (byte)(sampleRate & 0xFF);
			byte[] extra = new byte[5];
			extra[0] = (byte)(format.getChannels() & 0xFF);
			extra[1] = (byte)(format.getSampleSizeInBits() & 0xFF);
			extra[2] = (byte)(AudioFormat.Encoding.PCM_SIGNED.equals(format.getEncoding()) ? 1 : 0);
			extra[3] = (byte)(format.isBigEndian() ? 1 : 0);
			extra[4] = 0; // reserved
			byte[] r = new byte[4 + 1 + 4 + 5];
			System.arraycopy(magic, 0, r, 0, 4);
			System.arraycopy(type, 0, r, 4, 1);
			System.arraycopy(sr, 0, r, 5, 4);
			System.arraycopy(extra, 0, r, 9, 5);
			return r;
		}
		else
		{
			if(data == null) { return null; }
			byte[] magic = new byte[]{'F','J','2','A'};
			byte[] type = new byte[]{2}; // PCM
			byte[] lenBytes = new byte[4];
			lenBytes[0] = (byte)((len >>> 24) & 0xFF);
			lenBytes[1] = (byte)((len >>> 16) & 0xFF);
			lenBytes[2] = (byte)((len >>> 8) & 0xFF);
			lenBytes[3] = (byte)(len & 0xFF);
			byte[] r = new byte[4 + 1 + 4 + len];
			System.arraycopy(magic, 0, r, 0, 4);
			System.arraycopy(type, 0, r, 4, 1);
			System.arraycopy(lenBytes, 0, r, 5, 4);
			System.arraycopy(data, off, r, 9, len);
			return r;
		}
	}
}

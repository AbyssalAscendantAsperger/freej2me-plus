package org.recompile.freej2me.session;

public final class FramePacket
{
	public final byte[] header;
	public final byte[] rgb;
	public final int rgbLength;

	public FramePacket(byte[] header, byte[] rgb, int rgbLength)
	{
		this.header = header;
		this.rgb = rgb;
		this.rgbLength = rgbLength;
	}
}

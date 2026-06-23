/*
	Frame output abstraction for FreeJ2ME runtimes.
	Stdout is only one implementation; WebSocket or in-memory routing can be added
	without changing emulator logic.
*/
package org.recompile.freej2me.session;

import java.io.IOException;

public interface FrameSink
{
	public void ready() throws IOException;
	public void sendFrame(byte[] header, int headerOffset, int headerLength, byte[] rgb, int rgbOffset, int rgbLength) throws IOException;
	public void flush() throws IOException;
	public void close() throws IOException;
}

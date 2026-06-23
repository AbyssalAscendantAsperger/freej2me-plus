/*
	Session input abstraction for FreeJ2ME runtimes.
	This is the first step toward running emulators from something other than
	process-wide System.in, e.g. WebSocket/session queues.
*/
package org.recompile.freej2me.session;

import java.io.IOException;

public interface InputSource
{
	public int read() throws IOException;
	public int read(byte[] buffer) throws IOException;
	public int read(byte[] buffer, int offset, int length) throws IOException;
	public void close() throws IOException;
}

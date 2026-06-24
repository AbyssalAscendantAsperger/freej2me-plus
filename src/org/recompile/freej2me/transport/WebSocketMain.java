package org.recompile.freej2me.transport;

import org.recompile.freej2me.manager.FreeJ2MEManager;

/*
	Entry point for running FreeJ2ME-Plus as a multi-session WebSocket server.

	Usage: java org.recompile.freej2me.transport.WebSocketMain <port>

	Each WebSocket connection gets its own isolated FreeJ2ME session with a
	child-first ClassLoader, per-session data directories, and session-local
	System properties.

	The client must send a JSON config text frame immediately after the
	WebSocket handshake. Example:

	{
	  "width": 240,
	  "height": 320,
	  "rotate": 0,
	  "phone": 0,
	  "fps": 0,
	  "sound": 1,
	  "jar": "mygame.jar"
	}

	After config is acknowledged, the client can send raw 5-byte libretro
	binary commands and will receive binary frame (FE) / audio (FJ2A) packets.
*/
public class WebSocketMain
{
	public static void main(String[] args) throws Exception
	{
		int port = 8080;
		if(args.length > 0)
		{
			try { port = Integer.parseInt(args[0]); }
			catch(NumberFormatException e) { }
		}
		FreeJ2MEManager manager = new FreeJ2MEManager();
		WebSocketServer server = new WebSocketServer(port, manager);
		server.start();
		System.out.println("FreeJ2ME-Plus LeakFix-V3 WebSocket server listening on port " + port);
		System.out.println("LeakFix-V3 active: stoppable Display event loop, managed Libretro shutdown, EOF-on-input-interrupt, isolated hard-stop fallback");
		System.out.println("Each WebSocket connection spawns an isolated session.");
		// Keep main thread alive
		while(true)
		{
			try { Thread.sleep(60000); }
			catch(InterruptedException e) { break; }
		}
	}
}

package org.recompile.freej2me.transport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.recompile.freej2me.manager.FreeJ2MEManager;

/*
	Lightweight WebSocket server that accepts connections and maps each
	connection to a FreeJ2ME session via FreeJ2MEManager.
*/
public class WebSocketServer implements Runnable
{
	private final int port;
	private final FreeJ2MEManager manager;
	private ServerSocket server;
	private volatile boolean running = true;

	public WebSocketServer(int port, FreeJ2MEManager manager)
	{
		this.port = port;
		this.manager = manager;
	}

	public void start() throws IOException
	{
		server = new ServerSocket(port);
		new Thread(this, "WebSocketServer").start();
	}

	public void stop()
	{
		running = false;
		try { server.close(); }
		catch(IOException e) { }
	}

	public void run()
	{
		while(running)
		{
			try
			{
				Socket socket = server.accept();
				new WebSocketSession(socket, manager).start();
			}
			catch(IOException e)
			{
				if(running) { e.printStackTrace(); }
			}
		}
	}
}

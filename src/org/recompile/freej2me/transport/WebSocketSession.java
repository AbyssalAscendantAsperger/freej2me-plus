package org.recompile.freej2me.transport;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;

import org.recompile.freej2me.manager.FreeJ2MEManager;
import org.recompile.freej2me.session.AudioPacket;
import org.recompile.freej2me.session.FramePacket;
import org.recompile.mobile.Base64Util;

/*
	Lightweight WebSocket session per socket.
	Each accepted socket becomes one FreeJ2ME session.
	Protocol:
	  Client -> Server : raw binary libretro 5-byte commands (keys, touch, etc.)
	  Server -> Client : binary frames carrying FE/FJ2A packets (frame/audio)
*/
public class WebSocketSession implements Runnable
{
	private final Socket socket;
	private final FreeJ2MEManager manager;
	private String sessionId;
	private volatile boolean running = true;
	private DataInputStream in;
	private DataOutputStream out;
	private Thread readThread, writeThread;

	public WebSocketSession(Socket socket, FreeJ2MEManager manager)
	{
		this.socket = socket;
		this.manager = manager;
	}

	public void start() throws IOException
	{
		in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		if(!doHandshake())
		{
			try { socket.close(); } catch(IOException e) { }
			return;
		}
		sessionId = "ws-" + socket.getInetAddress().getHostAddress() + "-" + socket.getPort() + "-" + System.currentTimeMillis();
		manager.createSession(sessionId, "data/" + sessionId, new String[0]);
		readThread = new Thread(this, "WebSocketReader-" + sessionId);
		writeThread = new Thread(new Writer(), "WebSocketWriter-" + sessionId);
		readThread.start();
		writeThread.start();
	}

	/* ---------- WebSocket handshake ---------- */
	private boolean doHandshake() throws IOException
	{
		String line;
		String key = null;
		while((line = readLine()) != null && !line.isEmpty())
		{
			if(line.startsWith("Sec-WebSocket-Key: "))
			{
				key = line.substring(19).trim();
			}
		}
		if(key == null) { return false; }
		String accept = computeAccept(key);
		String response = "HTTP/1.1 101 Switching Protocols\r\n" +
			"Upgrade: websocket\r\n" +
			"Connection: Upgrade\r\n" +
			"Sec-WebSocket-Accept: " + accept + "\r\n\r\n";
		out.writeBytes(response);
		out.flush();
		return true;
	}

	private String readLine() throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int c;
		while((c = in.read()) != -1)
		{
			if(c == '\r')
			{
				int next = in.read();
				if(next == '\n') { break; }
				sb.append((char)c);
				if(next != -1) { sb.append((char)next); }
			}
			else
			{
				sb.append((char)c);
			}
		}
		return sb.toString();
	}

	private String computeAccept(String key)
	{
		try
		{
			String input = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(input.getBytes("ISO-8859-1"));
			return Base64Util.encode(hash);
		}
		catch(Exception e) { throw new RuntimeException(e); }
	}

	/* ---------- Reader thread ---------- */
	public void run()
	{
		try
		{
			while(running)
			{
				int b0 = in.read();
				if(b0 == -1) { break; }
				int b1 = in.read();
				if(b1 == -1) { break; }
				int opcode = b0 & 0x0F;
				boolean masked = (b1 & 0x80) != 0;
				long len = b1 & 0x7F;
				if(len == 126)
				{
					len = ((in.read() & 0xFFL) << 8) | (in.read() & 0xFFL);
				}
				else if(len == 127)
				{
					len = 0;
					for(int i = 0; i < 8; i++) { len = (len << 8) | (in.read() & 0xFFL); }
				}
				if(len > 65536)
				{
					for(long i = 0; i < len; i++) { in.read(); }
					continue;
				}
				byte[] mask = null;
				if(masked)
				{
					mask = new byte[4];
					in.readFully(mask);
				}
				byte[] payload = new byte[(int)len];
				in.readFully(payload);
				if(masked)
				{
					for(int i = 0; i < payload.length; i++) { payload[i] ^= mask[i % 4]; }
				}
				if(opcode == 0x8) { break; } // close
				if(opcode == 0x1 || opcode == 0x2)
				{
					// Raw libretro input commands from client
					manager.sendRaw(sessionId, payload);
				}
			}
		}
		catch(IOException e)
		{
			if(running) { e.printStackTrace(); }
		}
		finally
		{
			running = false;
			manager.destroySession(sessionId);
			try { socket.close(); } catch(IOException e) { }
		}
	}

	/* ---------- Writer thread ---------- */
	private class Writer implements Runnable
	{
		public void run()
		{
			try
			{
				while(running)
				{
					FramePacket frame = manager.takeFrame(sessionId);
					if(frame != null)
					{
						byte[] packet = concat(frame.header, frame.rgb);
						sendBinary(packet);
					}
					AudioPacket audio = manager.takeAudio(sessionId);
					if(audio != null)
					{
						byte[] packet = audio.toFJ2ABytes();
						if(packet != null) { sendBinary(packet); }
					}
					if(frame == null && audio == null)
					{
						try { Thread.sleep(1); }
						catch(InterruptedException e) { }
					}
				}
			}
			catch(IOException e)
			{
				if(running) { e.printStackTrace(); }
			}
		}

		private byte[] concat(byte[] a, byte[] b)
		{
			byte[] r = new byte[a.length + b.length];
			System.arraycopy(a, 0, r, 0, a.length);
			System.arraycopy(b, 0, r, a.length, b.length);
			return r;
		}

		private void sendBinary(byte[] data) throws IOException
		{
			synchronized(out)
			{
				out.write(0x82); // FIN + binary
				if(data.length < 126)
				{
					out.write(data.length);
				}
				else if(data.length <= 65535)
				{
					out.write(126);
					out.write((data.length >> 8) & 0xFF);
					out.write(data.length & 0xFF);
				}
				else
				{
					out.write(127);
					for(int i = 7; i >= 0; i--) { out.write((data.length >> (i * 8)) & 0xFF); }
				}
				out.write(data);
				out.flush();
			}
		}
	}
}

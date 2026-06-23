package org.recompile.freej2me.transport;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.util.Map;

import org.recompile.freej2me.manager.FreeJ2MEManager;
import org.recompile.freej2me.session.AudioPacket;
import org.recompile.freej2me.session.FramePacket;
import org.recompile.mobile.Base64Util;

/*
	Lightweight WebSocket session per socket.
	Handshake flow:
	  1. HTTP Upgrade handshake
	  2. Client sends first TEXT frame with JSON config:
	       {"width":240,"height":320,"phone":0,"rotate":0,"fps":30,
	        "sound":1,"midi":0,"dumpAudio":0,"logLevel":2,
	        "noAlpha":1,"backlight":1,"fantasyZone":0,
	        "transToOrigin":0,"immediateRepaints":0,
	        "overridePlatform":1,"siemensFriendly":0,
	        "fontOffset":0,"dumpGraphics":0,"deleteKJX":1,
	        "M3GUntextured":0,"M3GWireframe":0,
	        "fpsHack":0,"textFont":0,"fontOffset2":0,
	        "M3GHalfRes":0,"DoJaVersion":200,
	        "ignoreVolume":0,"MCV3HalfRes":0,
	        "MCV3NoLight":0,"MCV3HFOV":0,
	        "MCV3Heap":0,"MCV3Time":0,
	        "jar":"game.jar"}
	  3. Server creates FreeJ2ME session from config
	  4. Bidirectional binary frame mode begins
	     Client -> binary libretro commands (keys, pointer, frame request)
	     Server -> binary frame (FE header + RGB) or audio (FJ2A)
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
	private boolean configured = false;

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
		readThread = new Thread(this, "WebSocketReader-" + socket.getPort());
		readThread.start();
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
				if(opcode == 0x1 && !configured)
				{
					String json = new String(payload, "UTF-8");
					if(!handleConfig(json))
					{
						sendText("{\"error\":\"invalid config\"}");
						break;
					}
					configured = true;
					continue;
				}
				if(opcode == 0x1 || opcode == 0x2)
				{
					if(!configured)
					{
						sendText("{\"error\":\"send config first\"}");
						continue;
					}
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
			if(sessionId != null) { manager.destroySession(sessionId); }
			try { socket.close(); } catch(IOException e) { }
		}
	}

	private boolean handleConfig(String json)
	{
		try
		{
			Map<String, String> cfg = SimpleJSON.parse(json);
			String[] args = buildArgs(cfg);
			String jar = cfg.get("jar");
			if(jar == null || jar.isEmpty())
			{
				System.err.println("WebSocketSession: no 'jar' in config");
				return false;
			}
			sessionId = "ws-" + socket.getInetAddress().getHostAddress() + "-" + socket.getPort() + "-" + System.currentTimeMillis();
			String dataDir = "data/" + sessionId;
			manager.createSession(sessionId, dataDir, args);
			// Send load jar command
			manager.sendRaw(sessionId, buildLoadJarCommand(jar));
			// Start writer thread now that we have a session
			writeThread = new Thread(new Writer(), "WebSocketWriter-" + sessionId);
			writeThread.start();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	private byte[] buildLoadJarCommand(String jar)
	{
		try
		{
			byte[] path = jar.getBytes("UTF-8");
			byte[] cmd = new byte[5 + path.length];
			cmd[0] = 10; // load jar opcode
			cmd[1] = (byte)((path.length >> 24) & 0xFF);
			cmd[2] = (byte)((path.length >> 16) & 0xFF);
			cmd[3] = (byte)((path.length >> 8) & 0xFF);
			cmd[4] = (byte)(path.length & 0xFF);
			System.arraycopy(path, 0, cmd, 5, path.length);
			return cmd;
		}
		catch(Exception e) { throw new RuntimeException(e); }
	}

	private String[] buildArgs(Map<String, String> cfg)
	{
		String[] args = new String[32];
		args[0]  = getInt(cfg, "width", 240);
		args[1]  = getInt(cfg, "height", 320);
		args[2]  = getInt(cfg, "rotate", 0);
		args[3]  = getInt(cfg, "phone", 0);
		args[4]  = getInt(cfg, "fps", 0);
		args[5]  = getInt(cfg, "sound", 1);
		args[6]  = getInt(cfg, "midi", 0);
		args[7]  = getInt(cfg, "dumpAudio", 0);
		args[8]  = getInt(cfg, "logLevel", 2);
		args[9]  = getInt(cfg, "noAlpha", 1);
		args[10] = getInt(cfg, "backlight", 1);
		args[11] = getInt(cfg, "fantasyZone", 0);
		args[12] = getInt(cfg, "transToOrigin", 0);
		args[13] = getInt(cfg, "textFont", 0);
		args[14] = getInt(cfg, "fontOffset", 0);
		args[15] = getInt(cfg, "dumpGraphics", 0);
		args[16] = getInt(cfg, "deleteKJX", 1);
		args[17] = getInt(cfg, "M3GUntextured", 0);
		args[18] = getInt(cfg, "M3GWireframe", 0);
		args[19] = getInt(cfg, "fpsHack", 0);
		args[20] = getInt(cfg, "immediateRepaints", 0);
		args[21] = getInt(cfg, "overridePlatform", 1);
		args[22] = getInt(cfg, "siemensFriendly", 0);
		args[23] = getInt(cfg, "M3GHalfRes", 0);
		args[24] = getInt(cfg, "DoJaVersion", 200);
		args[25] = getInt(cfg, "ignoreVolume", 0);
		args[26] = getInt(cfg, "MCV3HalfRes", 0);
		args[27] = getInt(cfg, "MCV3NoLight", 0);
		args[28] = getInt(cfg, "MCV3HFOV", 0);
		args[29] = getInt(cfg, "MCV3Heap", 0);
		args[30] = getInt(cfg, "MCV3Time", 0);
		args[31] = getInt(cfg, "fontOffset2", 0);
		return args;
	}

	private String getInt(Map<String, String> cfg, String key, int def)
	{
		String v = cfg.get(key);
		if(v == null || v.isEmpty()) { return String.valueOf(def); }
		try { return String.valueOf(Integer.parseInt(v)); }
		catch(NumberFormatException e) { return String.valueOf(def); }
	}

	private void sendText(String text) throws IOException
	{
		byte[] data = text.getBytes("UTF-8");
		synchronized(out)
		{
			out.write(0x81); // FIN + text
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

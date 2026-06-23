package org.recompile.freej2me.manager;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.recompile.freej2me.session.*;

/*
	Session manager for running multiple isolated FreeJ2ME instances inside
	a single JVM. Each session gets its own ChildFirstFreeJ2MEClassLoader so
	that static globals (Mobile, MobilePlatform, Display, Manager, RMS, ...)
	are duplicated and do not collide across sessions.

	Communication with the outside world is done through in-memory queues
	(FramePacket / AudioPacket) so that no object type crosses classloader
	boundaries in a problematic way.
*/
public class FreeJ2MEManager
{
	private final Map<String, Object> sessions = new ConcurrentHashMap<String, Object>();
	private final ClassLoader parentLoader;
	private final URL coreJarUrl;

	public FreeJ2MEManager()
	{
		this.parentLoader = FreeJ2MEManager.class.getClassLoader();
		URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
		this.coreJarUrl = url;
	}

	/*
		Create a new session with isolated static state.
		Returns sessionId on success, null on failure.
	*/
	public String createSession(String sessionId, String dataDir, String[] args)
	{
		if(sessions.containsKey(sessionId)) { return null; }
		try
		{
			File dir = new File(dataDir);
			if(!dir.isDirectory()) { dir.mkdirs(); }

			ChildFirstFreeJ2MEClassLoader loader = new ChildFirstFreeJ2MEClassLoader(new URL[]{coreJarUrl}, parentLoader);

			// LibretroEmbeddedSession is in org.recompile.freej2me.session, which is
			// parent-first, so this effectively loads the class from the parent loader.
			// However, the session's Libretro / Mobile classes will be loaded from the child.
			Class<?> sessionClass = loader.loadClass("org.recompile.freej2me.session.LibretroEmbeddedSession");

			Object session = sessionClass.getConstructor(String.class).newInstance(sessionId);

			// Prepare queues
			QueueInputSource input = new QueueInputSource();
			QueueFrameSink frames = new QueueFrameSink();
			QueueAudioSink audio = new QueueAudioSink();

			// Inject sinks via reflection (cross-loader safe because the interfaces are parent-first)
			Method setInput = sessionClass.getMethod("setInputSource", InputSource.class);
			Method setFrame = sessionClass.getMethod("setFrameSink", FrameSink.class);
			Method setAudio = sessionClass.getMethod("setAudioSink", AudioSink.class);
			setInput.invoke(session, input);
			setFrame.invoke(session, frames);
			setAudio.invoke(session, audio);

			// Set per-session data directory
			Method setDataDir = sessionClass.getMethod("setDataDir", String.class);
			setDataDir.invoke(session, dataDir);

			// Start with the child loader as context
			Method start = sessionClass.getMethod("start", String[].class, ClassLoader.class);
			start.invoke(session, args, loader);

			sessions.put(sessionId, session);
			return sessionId;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean destroySession(String sessionId)
	{
		Object session = sessions.remove(sessionId);
		if(session == null) { return false; }
		try
		{
			Method stop = session.getClass().getMethod("stop");
			stop.invoke(session);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public Object getSession(String sessionId)
	{
		return sessions.get(sessionId);
	}

	public boolean sendKey(String sessionId, int key, boolean down)
	{
		Object session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			Class<?> c = session.getClass();
			if(down) { c.getMethod("sendKeyDown", int.class).invoke(session, key); }
			else { c.getMethod("sendKeyUp", int.class).invoke(session, key); }
			return true;
		}
		catch(Exception e) { return false; }
	}

	public boolean sendTouch(String sessionId, int x, int y, int state)
	{
		Object session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			Class<?> c = session.getClass();
			if(state == 0) { c.getMethod("sendPointerPressed", int.class, int.class).invoke(session, x, y); }
			else if(state == 1) { c.getMethod("sendPointerReleased", int.class, int.class).invoke(session, x, y); }
			else { c.getMethod("sendPointerDragged", int.class, int.class).invoke(session, x, y); }
			return true;
		}
		catch(Exception e) { return false; }
	}

	public boolean requestFrame(String sessionId)
	{
		Object session = sessions.get(sessionId);
		if(session == null) { return false; }
		try
		{
			session.getClass().getMethod("requestFrame").invoke(session);
			return true;
		}
		catch(Exception e) { return false; }
	}

	/* Poll a frame packet from the session's queue. */
	public FramePacket takeFrame(String sessionId)
	{
		Object session = sessions.get(sessionId);
		if(session == null) { return null; }
		try
		{
			Object sink = session.getClass().getMethod("getFrameSink").invoke(session);
			return ((QueueFrameSink)sink).poll();
		}
		catch(Exception e) { return null; }
	}

	/* Poll an audio packet from the session's queue. */
	public AudioPacket takeAudio(String sessionId)
	{
		Object session = sessions.get(sessionId);
		if(session == null) { return null; }
		try
		{
			Object sink = session.getClass().getMethod("getAudioSink").invoke(session);
			return ((QueueAudioSink)sink).poll();
		}
		catch(Exception e) { return null; }
	}
}

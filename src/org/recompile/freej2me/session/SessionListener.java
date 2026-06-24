package org.recompile.freej2me.session;

/*
	Callback interface for session lifecycle and error recovery.
	Allows external frontends (Web Bridge, Discord Bot) to be notified immediately
	when an embedded session starts, stops, or crashes.
*/
public interface SessionListener
{
	public void onSessionStarted(String sessionId);
	public void onSessionStopped(String sessionId);
	public void onSessionCrashed(String sessionId, Throwable error);
}

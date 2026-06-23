/*
	This file is part of FreeJ2ME.

	FreeJ2ME is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	FreeJ2ME is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with FreeJ2ME.  If not, see http://www.gnu.org/licenses/
*/
package javax.microedition.sip;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class SipRefreshHelper 
{

    private static SipRefreshHelper instance;
    private final Map<Integer, SipRefreshListener> refreshListeners;
    private int nextRefreshID;

    private SipRefreshHelper() 
    {
        refreshListeners = new HashMap<Integer, SipRefreshListener>();
        nextRefreshID = 1;
    }

    public static synchronized SipRefreshHelper getInstance() 
    {
        if (instance == null) { instance = new SipRefreshHelper(); }
        return instance;
    }

    public int enableRefresh(SipRefreshListener listener) 
    {
        if (listener == null) { throw new NullPointerException("Listener cannot be null"); }
        int refreshID = nextRefreshID++;
        refreshListeners.put(refreshID, listener);
        return refreshID;
    }

    public void stop(int refreshID) throws SipException 
    {
        if (!refreshListeners.containsKey(refreshID)) { throw new SipException(SipException.INVALID_STATE); }

        refreshListeners.remove(refreshID);
        notifyListener(refreshID, 0, "refresh stopped");
    }

    public OutputStream update(int refreshID, String[] contact, String type, int length, int expires) throws SipException 
    {
        if (!refreshListeners.containsKey(refreshID)) { throw new SipException(SipException.INVALID_STATE); }

        if (expires == 0) 
        {
            stop(refreshID);
            return null; // No content, refresh stopped
        }

        return null;
    }

    private void notifyListener(int refreshID, int statusCode, String reasonPhrase) 
    {
        SipRefreshListener listener = refreshListeners.get(refreshID);
        if (listener != null) 
        {
            listener.refreshEvent(refreshID, statusCode, reasonPhrase);
        }
    }
}
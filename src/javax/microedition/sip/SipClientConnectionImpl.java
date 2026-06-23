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

import java.io.IOException;
import java.io.OutputStream;

public class SipClientConnectionImpl extends SipConnectionImpl implements SipClientConnection 
{
    private String state;
    private String method;
    private SipConnectionNotifier notifier;
    private SipClientConnectionListener listener;

    public SipClientConnectionImpl() { super(); }

    public void initRequest(String method, SipConnectionNotifier scn) throws SipException 
    {
        if (!state.equals("Created")) { throw new SipException(SipException.INVALID_STATE); }
        
        this.method = method;
        this.notifier = scn;
        state = "Initialized";
    }

    public void initAck() throws SipException 
    {
        if (!state.equals("Completed")) { throw new SipException(SipException.INVALID_STATE); }

        state = "Initialized";
    }

    public SipClientConnection initCancel() throws SipException 
    {
        if (!state.equals("Proceeding")) { throw new SipException(SipException.INVALID_STATE); }

        SipClientConnectionImpl cancelConnection = new SipClientConnectionImpl();

        cancelConnection.state = "Initialized";
        return cancelConnection;
    }

    public boolean receive(long timeout) throws SipException, IOException 
    {
        if (state.equals("Terminated")) { throw new SipException(SipException.INVALID_STATE); }

        return true; 
    }

    public int enableRefresh(SipRefreshListener srl) throws SipException 
    {
        if (!state.equals("Initialized")) { throw new SipException(SipException.INVALID_STATE); }

        return 1; 
    }

    public void setCredentials(String username, String password, String realm) throws SipException 
    {
        if (!state.equals("Initialized") && !state.equals("Unauthorized")) { throw new SipException(SipException.INVALID_STATE); }
    }

    public void setCredentials(String[] usernames, String[] passwords, String[] realms) throws SipException 
    {
        if (!state.equals("Initialized") && !state.equals("Unauthorized")) { throw new SipException(SipException.INVALID_STATE); }
    }

    public void setListener(SipClientConnectionListener sccl) throws IOException 
    {
        if (state.equals("Terminated")) { throw new IOException("Connection is closed"); }

        listener = sccl;
    }

    public void setRequestURI(String URI) throws SipException 
    {
        if (!state.equals("Initialized")) { throw new SipException(SipException.INVALID_STATE); }
    }

    public void close() 
    {
        state = "Terminated";
        super.close();
    }
}
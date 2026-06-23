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

public interface SipClientConnection extends SipConnection 
{

    int enableRefresh(SipRefreshListener srl) throws SipException;

    void initAck() throws SipException;

    SipClientConnection initCancel() throws SipException;

    void initRequest(String method, SipConnectionNotifier scn) throws SipException;

    boolean receive(long timeout) throws SipException, IOException;

    void setCredentials(String[] usernames, String[] passwords, String[] realms) throws SipException;

    void setCredentials(String username, String password, String realm) throws SipException;

    void setListener(SipClientConnectionListener sccl) throws IOException;

    void setRequestURI(String URI) throws SipException;
}
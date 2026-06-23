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

import javax.microedition.io.Connection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SipConnectionImpl implements SipConnection 
{
    private String method;
    private String requestURI;
    private int statusCode;
    private String reasonPhrase;
    private final Map<String, String> headers = new HashMap<String, String>();
    private String state;
    private SipDialog dialog;
    private SipErrorListener errorListener;

    public SipConnectionImpl() { this.state = "Created"; }

    public void addHeader(String name, String value) throws SipException { headers.put(name, value); }

    public SipDialog getDialog() { return state.equals("Confirmed") || state.equals("Early") ? dialog : null; }

    public String getHeader(String name) { return headers.get(name); }

    public String[] getHeaders(String name) 
    {
        List<String> result = new ArrayList<String>();
        for (String header : headers.keySet()) 
        {
            if (header.equals(name)) { result.add(headers.get(header)); }
        }
        return result.toArray(new String[0]);
    }

    public String getMethod() { return method; }

    public String getReasonPhrase() { return reasonPhrase; }

    public String getRequestURI() { return requestURI; }

    public int getStatusCode() { return statusCode; }

    public InputStream openContentInputStream() throws IOException, SipException 
    {
        if (!state.equals("Received")) { throw new SipException(SipException.INVALID_STATE); }

        return null;
    }

    public OutputStream openContentOutputStream() throws IOException, SipException 
    {
        if (!state.equals("Initialized")) { throw new SipException(SipException.INVALID_STATE); }

        return null;
    }

    @Override
    public void removeHeader(String name) throws SipException {
        if (!state.equals("Initialized")) { throw new SipException(SipException.INVALID_STATE); }

        headers.remove(name);
    }

    @Override
    public void send() throws IOException, SipException 
    {
        if (!state.equals("Initialized")) { throw new SipException(SipException.INVALID_STATE); }
        
        state = "Sent";
    }

    public void setErrorListener(SipErrorListener sel) { this.errorListener = sel; }

    public void setHeader(String name, String value) throws SipException { headers.put(name, value); }

    public void close() { }
}
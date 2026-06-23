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

public interface SipConnection extends Connection 
{

    void addHeader(String name, String value) throws SipException;

    SipDialog getDialog();

    String getHeader(String name);

    String[] getHeaders(String name);

    String getMethod();

    String getReasonPhrase();

    String getRequestURI();

    int getStatusCode();

    InputStream openContentInputStream() throws IOException, SipException;

    OutputStream openContentOutputStream() throws IOException, SipException;

    void removeHeader(String name) throws SipException;

    void send() throws IOException, SipException;

    void setErrorListener(SipErrorListener sel) throws SipException;

    void setHeader(String name, String value) throws SipException;
}
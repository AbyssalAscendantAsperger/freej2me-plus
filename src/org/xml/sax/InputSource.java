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
package org.xml.sax;

import java.io.InputStream;
import java.io.Reader;

public class InputSource 
{
    private InputStream byteStream;
    private Reader characterStream;
    private String publicId;
    private String systemId;
    private String encoding;

    public InputSource() { }

    public InputSource(String systemId) 
    {
        this.systemId = systemId;
    }

    public InputSource(InputStream byteStream) 
    {
        this.byteStream = byteStream;
    }

    public InputSource(Reader characterStream) 
    {
        this.characterStream = characterStream;
    }

    public InputStream getByteStream() 
    {
        return byteStream;
    }

    public void setByteStream(InputStream byteStream) 
    {
        this.byteStream = byteStream;
    }

    public Reader getCharacterStream() 
    {
        return characterStream;
    }

    public void setCharacterStream(Reader characterStream) 
    {
        this.characterStream = characterStream;
    }

    public String getPublicId() 
    {
        return publicId;
    }

    public void setPublicId(String publicId) 
    {
        this.publicId = publicId;
    }

    public String getSystemId() 
    {
        return systemId;
    }

    public void setSystemId(String systemId) 
    {
        this.systemId = systemId;
    }

    public String getEncoding() 
    {
        return encoding;
    }

    public void setEncoding(String encoding) 
    {
        this.encoding = encoding;
    }
}
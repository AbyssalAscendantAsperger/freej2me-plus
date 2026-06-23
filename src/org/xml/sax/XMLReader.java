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

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface XMLReader 
{

    public void setContentHandler(ContentHandler handler);
    
    public ContentHandler getContentHandler();
    
    public void setDTDHandler(DTDHandler handler);

    public DTDHandler getDTDHandler();
    
    public void setEntityResolver(EntityResolver resolver);

    public EntityResolver getEntityResolver();
    
    public void setErrorHandler(ErrorHandler handler);

    public ErrorHandler getErrorHandler();
    
    public boolean getFeature(String name) throws SAXException;

    public void setFeature(String name, boolean value) throws SAXException;
    
    public Object getProperty(String name) throws SAXException;

    public void setProperty(String name, Object value) throws SAXException;
    
    public void parse(InputSource input) throws IOException, SAXException;

    public void parse(String systemId) throws IOException, SAXException;
}
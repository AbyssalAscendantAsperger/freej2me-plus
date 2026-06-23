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
package javax.xml.stream;

import java.io.OutputStream;
import java.io.Writer;

public abstract class XMLOutputFactory 
{

    public static final String IS_REPAIRING_NAMESPACES = "javax.xml.stream.isRepairingNamespaces";

    protected XMLOutputFactory() { }

    public static XMLOutputFactory newInstance() throws FactoryConfigurationError { return null; }

    public abstract XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException;
    
    public abstract XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException;
    
    public abstract XMLStreamWriter createXMLStreamWriter(Writer stream) throws XMLStreamException;
    
    public abstract Object getProperty(String name) throws IllegalArgumentException;
    
    public abstract boolean isPropertySupported(String name);
    
    public abstract void setProperty(String name, Object value) throws IllegalArgumentException;
}
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

public interface XMLStreamReader extends XMLStreamConstants 
{

    public void close() throws XMLStreamException;

    public int getAttributeCount();

    public String getAttributeLocalName(int index);

    public String getAttributeNamespace(int index);

    public String getAttributePrefix(int index);

    public String getAttributeType(int index);
    
    public String getAttributeValue(int index);
    
    public String getAttributeValue(String namespaceURI, String localName);
    
    public String getCharacterEncodingScheme();
    
    public String getElementText() throws XMLStreamException;
    
    public String getEncoding();
    
    public int getEventType();
    
    public String getLocalName();
    
    public Location getLocation();
    
    public int getNamespaceCount();
    
    public String getNamespacePrefix(int index);
    
    public String getNamespaceURI();
    
    public String getNamespaceURI(int index);
    
    public String getNamespaceURI(String prefix);
    
    public String getPIData();
    
    public String getPITarget();
    
    public String getPrefix();
    
    public Object getProperty(String name) throws IllegalArgumentException;
    
    public String getText();
    
    public char[] getTextCharacters();
    
    public int getTextLength();
    
    public int getTextStart();
    
    public String getVersion();
    
    public boolean hasNext() throws XMLStreamException;
    
    public boolean isAttributeSpecified(int index);
    
    public boolean isStandalone();
    
    public boolean isWhiteSpace();
    
    public int next() throws XMLStreamException;
    
    public int nextTag() throws XMLStreamException;
    
    public void require(int type, String namespaceURI, String localName) throws XMLStreamException;
    
    public boolean standaloneSet();
}
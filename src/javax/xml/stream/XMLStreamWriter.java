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

public interface XMLStreamWriter 
{
    
    public void close() throws XMLStreamException;
    
    public void flush() throws XMLStreamException;
    
    public String getPrefix(String uri) throws XMLStreamException;
    
    public Object getProperty(String name) throws IllegalArgumentException;
    
    public void setDefaultNamespace(String uri) throws XMLStreamException;
    
    public void setPrefix(String prefix, String uri) throws XMLStreamException;
    
    public void writeAttribute(String localName, String value) throws XMLStreamException;
    
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException;
    
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException;
    
    public void writeCData(String data) throws XMLStreamException;
    
    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException;
    
    public void writeCharacters(String text) throws XMLStreamException;
    
    public void writeComment(String data) throws XMLStreamException;
    
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException;
    
    public void writeDTD(String dtd) throws XMLStreamException;
    
    public void writeEmptyElement(String localName) throws XMLStreamException;
    
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException;
    
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException;
    
    public void writeEndDocument() throws XMLStreamException;
    
    public void writeEndElement() throws XMLStreamException;
    
    public void writeEntityRef(String name) throws XMLStreamException;
    
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException;
    
    public void writeProcessingInstruction(String target) throws XMLStreamException;
    
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException;
    
    public void writeStartDocument() throws XMLStreamException;
    
    public void writeStartDocument(String version) throws XMLStreamException;
    
    public void writeStartDocument(String encoding, String version) throws XMLStreamException;
    
    public void writeStartElement(String localName) throws XMLStreamException;
    
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException;
    
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException;
}
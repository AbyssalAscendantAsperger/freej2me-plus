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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.Locator;

public interface ContentHandler 
{

    public void setDocumentLocator(Locator locator);

    public void startDocument() throws SAXException;

    public void endDocument() throws SAXException;

    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException;

    public void endElement(String uri, String localName, String qName) throws SAXException;

    public void characters(char[] ch, int start, int length) throws SAXException;

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException;

    public void processingInstruction(String target, String data) throws SAXException;

    public void skippedEntity(String name) throws SAXException;

    public void startPrefixMapping(String prefix, String uri) throws SAXException;

    public void endPrefixMapping(String prefix) throws SAXException;
}
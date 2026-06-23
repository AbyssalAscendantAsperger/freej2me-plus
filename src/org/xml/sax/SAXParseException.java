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

import org.xml.sax.Locator;

public class SAXParseException extends SAXException 
{
    private final String publicId;
    private final String systemId;
    private final int lineNumber;
    private final int columnNumber;

    public SAXParseException(String message, Locator locator) 
    {
        super(message);
        if (locator != null) 
        {
            this.publicId = locator.getPublicId();
            this.systemId = locator.getSystemId();
            this.lineNumber = locator.getLineNumber();
            this.columnNumber = locator.getColumnNumber();
        } else 
        {
            this.publicId = null;
            this.systemId = null;
            this.lineNumber = -1;
            this.columnNumber = -1;
        }
    }

    public String getPublicId() { return publicId; }

    public String getSystemId() { return systemId; }

    public int getLineNumber() { return lineNumber; }

    public int getColumnNumber() { return columnNumber; }
}
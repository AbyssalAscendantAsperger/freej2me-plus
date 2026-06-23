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

public interface DTDStreamReader 
{

    static final int START_DTD = 1;
    static final int END_DTD = 2;
    static final int ENTITY_DECLARATION = 3;
    static final int UNPARSED_ENTITY_DECLARATION = 4;
    static final int NOTATION_DECLARATION = 5;
    static final int PROCESSING_INSTRUCTION = 6;
    static final int COMMENT = 7;

    public void close() throws XMLStreamException;

    public int getEventType();

    public Location getLocation();

    public String getName();

    public String getNotationName();

    public String getPITarget();

    public String getPIData();

    public String getPublicIdentifier();

    public String getSystemIdentifier();

    public String getText();

    public char[] getTextCharacters();

    public int getTextLength();

    public int getTextStart();

    public boolean hasNext();

    public int next() throws XMLStreamException;
}
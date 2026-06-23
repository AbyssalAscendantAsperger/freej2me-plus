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
package org.w3c.dom;

public class DOMException extends RuntimeException 
{
    public short code;

    public static final short INDEX_SIZE_ERR = 1;
    public static final short DOMSTRING_SIZE_ERR = 2;
    public static final short HIERARCHY_REQUEST_ERR = 3;
    public static final short WRONG_DOCUMENT_ERR = 4;
    public static final short INVALID_CHARACTER_ERR = 5;
    public static final short NO_DATA_ALLOWED_ERR = 6;
    public static final short NO_MODIFICATION_ALLOWED_ERR = 7;
    public static final short NOT_FOUND_ERR = 8;
    public static final short NOT_SUPPORTED_ERR = 9;
    public static final short INUSE_ATTRIBUTE_ERR = 10;
    public static final short INVALID_STATE_ERR = 11;
    public static final short SYNTAX_ERR = 12;
    public static final short INVALID_MODIFICATION_ERR = 13;
    public static final short NAMESPACE_ERR = 14;
    public static final short INVALID_ACCESS_ERR = 15;
    public static final short TYPE_MISMATCH_ERR = 16;

    public DOMException(short code, String message) 
    {
        super(message);
        this.code = code;
    }
}
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
package com.nttdocomo.device;

public class CodeReader 
{
    public static final int CODE_39 = 8;
    public static final int CODE_AUTO = 0;
    public static final int CODE_JAN13 = 2;
    public static final int CODE_JAN8 = 1;
    public static final int CODE_MICRO_QR = 6;
    public static final int CODE_NW7 = 7;
    public static final int CODE_OCR = 4;
    public static final int CODE_QR = 3;
    public static final int CODE_UNKNOWN = -1;
    public static final int CODE_UNSUPPORTED = -2;
    public static final int TYPE_ASCII = 2;
    public static final int TYPE_BINARY = 0;
    public static final int TYPE_NUMBER = 1;
    public static final int TYPE_STRING = 3;
    public static final int TYPE_UNKNOWN = -1;

    protected CodeReader() { }

    public static CodeReader getCodeReader(int id) 
    {
        return null;
    }

    public int[] getAvailableCodes() 
    {
        return null;
    }

    public int[] getAvailableFocusModes() 
    {
        return null;
    }

    public byte[] getBytes() 
    {
        return null;
    }

    public int getFocusMode() 
    {
        return 0;
    }

    public int getResultCode() 
    {
        return 0;
    }

    public int getResultType() 
    {
        return 0;
    }

    public String getString() 
    {
        return null;
    }

    public void read() 
    {

    }

    public void setCode(int code) 
    {

    }

    public void setFocusMode(int mode) 
    {

    }
}
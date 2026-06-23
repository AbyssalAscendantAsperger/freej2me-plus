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
package com.nttdocomo.system;

import com.nttdocomo.lang.XString;

public final class CallRecord 
{

    public static final int CALL_IN = 0;
    public static final int CALL_OUT = 1;
    public static final int TYPE_TEL = 0;
    public static final int TYPE_TEL_AV = 1;
    public static final int TYPE_DATA_CONNECTION = 2;
    public static final int TYPE_PPP_PACKET_CONNECTION = 3;
    public static final int TYPE_OTHER = 4;

    public static CallRecord getLastRecord(int type) 
    {
        return null;
    }

    public XString getDateString(String pattern) 
    {
        return null;
    }

    public XString getDateString(String pattern, java.util.TimeZone zone) 
    {
        return null;
    }

    public int[][] getPhoneBookID() 
    {
        return null;
    }

    public XString getPhoneNumber() 
    {
        return null;
    }

    public Integer getTelType() 
    {
        return null;
    }

    public Boolean isSucceeded() 
    {
        return null;
    }
}
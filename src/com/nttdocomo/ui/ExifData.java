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
package com.nttdocomo.ui;

public class ExifData 
{
    public static final int GPS_INFO_TAG = 34853;
    public static final int SUPPORT_GET = 1;
    public static final int SUPPORT_SET = 2;

    public ExifData() { }

    public java.util.Enumeration enumerateTags() 
    {
        return null;
    }

    public String getAsciiTag(int tagGroup, int tagID) 
    {
        return null;
    }

    public long[] getIntegerTag(int tagGroup, int tagID) 
    {
        return null;
    }

    public long[][] getRationalTag(int tagGroup, int tagID) 
    {
        return null;
    }

    public static int getSupportStatus(int tagGroup, int tagID) 
    {
        return 0;
    }

    public byte[] getUndefinedTag(int tagGroup, int tagID) 
    {
        return null;
    }

    public void setAsciiTag(int tagGroup, int tagID, String value) 
    {

    }

    public void setIntegerTag(int tagGroup, int tagID, long[] values) 
    {

    }

    public void setRationalTag(int tagGroup, int tagID, long[][] rational) 
    {

    }

    public void setUndefinedTag(int tagGroup, int tagID, byte[] value) 
    {

    }

    public com.nttdocomo.device.location.Location toLocation() 
    {
        return null;
    }

    public void update(com.nttdocomo.device.location.Location location) 
    {
        
    }
}
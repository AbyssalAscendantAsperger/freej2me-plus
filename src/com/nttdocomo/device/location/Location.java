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
package com.nttdocomo.device.location;

public class Location 
{

    public static final int ACCURACY_COARSE = 2147483647;
    public static final int ACCURACY_FINE = 49;
    public static final int ACCURACY_NORMAL = 299;
    public static final int ACCURACY_UNKNOWN = -1;
    public static final int ALTITUDE_UNKNOWN = 0x80000000;
    public static final int PREFIX_DIRECTION = 0;
    public static final int PREFIX_SIGN = 1;

    public Location(Degree latitude, Degree longitude) 
    {
    
    }

    public Location(Degree latitude, Degree longitude, int altitude, int datum, long timestamp, int accuracy) 
    {
    
    }

    public Location(String url) 
    {
    
    }

    public Location(String url, int altitude, long timestamp) 
    {
    
    }

    public Location(String latitude, String longitude, int altitude, int datum, long timestamp, int accuracy) 
    {
   
    }

    public Degree calculateAzimuth(Location dst) 
    {
        return null;
    }

    public double calculateDistance(Location dst) 
    {
        return 0.0;
    }

    public int getAccuracy() 
    {
        return 0;
    }

    public int getAltitude() 
    {
        return 0;
    }

    public int getDatum() 
    {
        return 0;
    }

    public String getJointRFIURL() 
    {
        return null;
    }
}
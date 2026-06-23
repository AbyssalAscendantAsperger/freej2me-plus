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

public final class GPSLocationProvider extends LocationProvider 
{
    public static final int MODE_STANDALONE = 2;

    public static GPSLocationProvider getLocationProvider() 
    {
        return (GPSLocationProvider) LocationProvider.getLocationProvider(METHOD_GPS);
    }

    public int[] getAvailableMeasurementMode() 
    {
        return new int[]{MODE_STANDALONE, MODE_STANDARD, MODE_QUALITY_PRIORITY};
    }

    public Location getLocation(int timeout) throws LocationException 
    {
        return null;
    }

    public int getMinimalInterval() { return interval; }

    public void interrupt() { /* TODO */ }

    public void setMeasurementMode(int mode) 
    {
        if (mode != MODE_STANDALONE && mode != MODE_STANDARD && mode != MODE_QUALITY_PRIORITY) 
        {
            throw new IllegalArgumentException("Invalid measurement mode.");
        }
        super.setMeasurementMode(mode);
    }

    public int getMeasurementMode() 
    {
        return mode;
    }
}
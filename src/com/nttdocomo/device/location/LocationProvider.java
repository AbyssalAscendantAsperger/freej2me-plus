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

public abstract class LocationProvider 
{
    public static final int CAPABILITY_TRACKING_MODE = 0;
    public static final int DATUM_TOKYO = 1;
    public static final int DATUM_WGS84 = 0;
    public static final int METHOD_GPS = 0;
    public static final int MODE_QUALITY_PRIORITY = 1;
    public static final int MODE_STANDARD = 0;
    public static final int UNIT_DEGREE = 1;
    public static final int UNIT_DMS = 0;
    public static final int UNIT_DMS_2 = 2;

    protected TrackingListener listener;
    protected int interval, threshold, mode;

    public static int[] getAvailableLocationMethod() 
    {
        return new int[]{METHOD_GPS};
    }

    public static int[] getAvailableLocationMethod(int capability) 
    {
        return new int[]{METHOD_GPS};
    }

    public abstract int[] getAvailableMeasurementMode();

    public Location getLocation() 
    {
        return null;
    }

    public abstract Location getLocation(int timeout) throws LocationException ;

    public static LocationProvider getLocationProvider() 
    {
        return getLocationProvider(METHOD_GPS);
    }

    public static LocationProvider getLocationProvider(int method) 
    {
        return null;
    }

    public abstract int getMeasurementMode();

    public abstract int getMinimalInterval();

    public abstract void interrupt();

    public void setMeasurementMode(int mode) 
    {
        this.mode = mode;
    }

    public void setTrackingListener(TrackingListener listener, int interval, int threshold) 
    {
        this.listener = listener;
        this.interval = interval;
        this.threshold = threshold;
    }
}
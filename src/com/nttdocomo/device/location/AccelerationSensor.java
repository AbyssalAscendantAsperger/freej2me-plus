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

public class AccelerationSensor 
{
    public static final int ACCELERATION_X = 1;
    public static final int ACCELERATION_Y = 2;
    public static final int ACCELERATION_Z = 3;
    public static final int ROLL = 4;
    public static final int PITCH = 5;
    public static final int SCREEN_ORIENTATION = 6;
    public static final int EVENT_SCREEN_ORIENTATION = 1;
    public static final int EVENT_DOUBLE_TAP = 2;
    public static final int DOUBLE_TAP_LEFT = 0;
    public static final int DOUBLE_TAP_RIGHT = 1;
    public static final int DOUBLE_TAP_FRONT = 2;
    public static final int DOUBLE_TAP_BACK = 3;

    private static final AccelerationSensor sensor = new AccelerationSensor();
    public AccelerationEventListener listener;

    public static AccelerationSensor getAccelerationSensor() { return sensor; }

    public void disposeData() 
    {

    }

    public int[] getAvailableData() 
    {

        return new int[]{ACCELERATION_X, ACCELERATION_Y, ACCELERATION_Z, ROLL, PITCH, SCREEN_ORIENTATION};
    }

    public int[] getAvailableEvent() 
    {

        return new int[]{EVENT_SCREEN_ORIENTATION, EVENT_DOUBLE_TAP};
    }

    public AccelerationData getCurrentData() 
    {
        return null;
    }

    public AccelerationData getData() 
    {
        return null;
    }

    public int getDataSize() 
    {
        return 0;
    }

    public int getIntervalResolution() 
    {
        return 1;
    }

    public int getMaxDataSize() 
    {
        return 0;
    }

    public int getMaxDataValue(int type) 
    {
        return Integer.MAX_VALUE;
    }

    public int getMinDataValue(int type) 
    {
        return Integer.MIN_VALUE;
    }

    public AccelerationData peekLatestData() 
    {
        return null;
    }

    public void setEventListener(AccelerationEventListener listener) 
    {
        this.listener = listener;
    }

    public void start(int interval) 
    {

    }

    public void startEventDetection(int event) 
    {

    }

    public void stop() 
    {

    }

    public void stopEventDetection() 
    {

    }
}
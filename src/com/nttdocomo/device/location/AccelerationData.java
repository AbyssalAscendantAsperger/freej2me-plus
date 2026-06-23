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

public class AccelerationData 
{
    private int[] accelerationX;
    private int[] accelerationY;
    private int[] accelerationZ;
    private int[] pitch;
    private int[] roll;
    private int[] screenOrientation;

    public int[] getAccelerationX() 
    {
        if (accelerationX == null) { throw new UnsupportedOperationException(); }
        return accelerationX;
    }

    public int[] getAccelerationY() 
    {
        if (accelerationY == null) { throw new UnsupportedOperationException(); }
        return accelerationY;
    }

    public int[] getAccelerationZ() 
    {
        if (accelerationZ == null) { throw new UnsupportedOperationException(); }
        return accelerationZ;
    }

    public int[] getPitch() 
    {
        if (pitch == null) { throw new UnsupportedOperationException(); }
        return pitch;
    }

    public int[] getRoll() 
    {
        if (roll == null) { throw new UnsupportedOperationException(); }
        return roll;
    }

    public int[] getScreenOrientation() 
    {
        if (screenOrientation == null) { throw new UnsupportedOperationException(); }
        return screenOrientation;
    }

    public int size() 
    {
        return Math.min
        (
            Math.min(accelerationX.length, accelerationY.length),
            Math.min(accelerationZ.length, Math.min(pitch.length, Math.min(roll.length, screenOrientation.length)))
        );
    }
}
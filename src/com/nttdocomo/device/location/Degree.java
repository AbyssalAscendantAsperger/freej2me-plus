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

public class Degree extends Object 
{
    public Degree(double degree) 
    { 

    }

    public Degree(int degree, int minute, float second) 
    {

    }

    public Degree(long degree) 
    {

    }

    public int getDegreePart() 
    {
        return 0;
    }

    public int getMinutePart() 
    {
        return 0;
    }

    public int getCentisecondPart() 
    {
        return 0;
    }

    public float getSecondPart() 
    {
        return 0.0f;
    }

    public long getFixedPointNumber() 
    {
        return 0L;
    }

    public double getFloatingPointNumber() 
    {
        return 0.0;
    }
}
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

public class IrRemoteControl 
{
    public static final int PATTERN_HL = 0;
    public static final int PATTERN_LH = 1;

    protected IrRemoteControl() { }

    public static IrRemoteControl getIrRemoteControl() 
    {
        return null;
    }

    public void send(int numFrames, IrRemoteControlFrame[] frames) 
    {
        
    }

    public void send(int numFrames, IrRemoteControlFrame[] frames, int timeout) 
    {

    }

    public void send(int numFrames, IrRemoteControlFrame[] frames, int timeout, int count) 
    {

    }

    public void setCarrier(int highDuration, int lowDuration) 
    {

    }

    public void setCode0(int pattern, int highDuration, int lowDuration) 
    {

    }

    public void setCode1(int pattern, int highDuration, int lowDuration) 
    {

    }

    public void stop() 
    {

    }
}
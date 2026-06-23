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

import com.nttdocomo.io.BTConnection;
import com.nttdocomo.system.InterruptedOperationException;

public class RemoteDevice 
{
    public static final int SNIFF_MODE = 1;

    public RemoteDevice() { }

    public BTConnection accept(int profile) throws InterruptedOperationException 
    {
        return null;
    }

    public void changePowerMode(int type) 
    {

    }

    public BTConnection connect(int profile) throws InterruptedOperationException 
    {
        return null;
    }

    public void dispose() 
    {
    
    }

    public String getAddress() 
    {
        return null;
    }

    public String getDeviceClass() 
    {
        return null;
    }

    public String getDeviceName() 
    {
        return null;
    }

    public void interruptAcceptance() 
    {

    }

    public boolean isAvailable(int profile) 
    {
        return false;
    }
}
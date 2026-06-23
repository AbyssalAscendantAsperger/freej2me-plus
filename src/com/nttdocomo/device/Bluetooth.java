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

import com.nttdocomo.system.InterruptedOperationException;

public class Bluetooth 
{
    public static final int SPP = 0;

    public static Bluetooth getInstance() 
    {
        return null;
    }

    public int getDiscoveredDevice() 
    {
        return 0;
    }

    public int getInquiryTimeout() 
    {
        return 0;
    }

    public boolean isConnectable(int type) 
    {
        return false;
    }

    public boolean isDetachmentMode() 
    {
        return false;
    }

    public RemoteDevice scan() throws InterruptedOperationException 
    {
        return null;
    }

    public RemoteDevice searchAndSelectDevice() throws InterruptedOperationException 
    {
        return null;
    }

    public RemoteDevice selectDevice() throws InterruptedOperationException 
    {
        return null;
    }

    public void setDetachmentMode(boolean flag) 
    {

    }

    public void setInquiryTimeout(int time) 
    {

    }

    public void turnOff() 
    {

    }
}
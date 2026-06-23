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
package com.nttdocomo.device.felica;

public final class OnlineFelica 
{

    public static final int DEVICE_ID_FELICA = 0x0001;
    public static final int DEVICE_ID_DISPLAY = 0x0002;

    public void setOnlineListener(OnlineListener listener) 
    {

    }

    public int addDevice(String type, String name) 
    {
        return 0;
    }

    public void clearDeviceList() 
    {

    }

    public int getDeviceID(String type, String name) 
    {
        return -1;
    }

    public String getDeviceName(int deviceID) 
    {
        return null;
    }

    public String getDeviceType(int deviceID) 
    {
        return null;
    }

    public void start(String url) throws com.nttdocomo.io.ConnectionException, javax.microedition.io.ConnectionNotFoundException 
    {

    }

    public void stop() 
    {

    }
}
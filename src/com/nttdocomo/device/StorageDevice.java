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

import com.nttdocomo.fs.AccessToken;
import com.nttdocomo.fs.Folder;

public class StorageDevice
{
    
    public static final String CAPABILITY_FAT_LONG_NAME = "FAT_LONG_NAME";
    public static final String CAPABILITY_FAT12 = "FAT12";
    public static final String CAPABILITY_FAT16 = "FAT16";
    public static final String CAPABILITY_FAT32 = "FAT32";
    public static final String CAPABILITY_MINISD = "miniSD";
    public static final String CAPABILITY_SD = "SD";
    public static final String CAPABILITY_SD_BINDING = "SD-Binding";
    public static final String CATEGORY_ENCRYPTION = "encryption";
    public static final String CATEGORY_FILESYSTEM = "filesystem";
    public static final String CATEGORY_HARDWARE = "hardware";

    private String deviceName;

    private StorageDevice(String deviceName) { this.deviceName = deviceName; }

    public static StorageDevice getInstance(String deviceName) 
    {
        return new StorageDevice(deviceName);
    }

    public String getDeviceName() { return deviceName; }

    public String getPrintName() { return "FreeJ2ME-DoJa SD"; }

    public boolean isAccessible() 
    {
        return true;
    }

    public boolean isReadable() 
    {
        return true; 
    }

    public boolean isRemovable() 
    {
        return false;
    }

    public boolean isWritable() 
    {
        return true;
    }

    public String[] getCapability(String category) 
    {
        return null;
    }

    public Folder getFolder(AccessToken accessToken) 
    {
        return null; 
    }

    public String getMediaId() { return "mediaID"; }
}
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

import com.nttdocomo.ui.MediaImage;

public class Camera 
{
    public static final int ATTR_CONTINUOUS_SHOT_OFF = 0;
    public static final int ATTR_CONTINUOUS_SHOT_ON = 1;
    public static final int ATTR_FRAME_OFF = 0;
    public static final int ATTR_FRAME_ON = 1;
    public static final int ATTR_QUALITY_HIGH = 0;
    public static final int ATTR_QUALITY_LOW = 2;
    public static final int ATTR_QUALITY_STANDARD = 1;
    public static final int ATTR_VOLUME_MAX = 127;
    public static final int ATTR_VOLUME_MIN = 0;
    public static final int DEV_CONTINUOUS_IMAGES = 3;
    public static final int DEV_CONTINUOUS_SHOT = 0;
    public static final int DEV_FRAME_SHOT = 4;
    public static final int DEV_QUALITY = 1;
    public static final int DEV_SOUND = 2;
    public static final int FOCUS_HARDWARE_SWITCH = -1;
    public static final int FOCUS_MACRO_MODE = 1;
    public static final int FOCUS_NORMAL_MODE = 0;

    protected Camera() { }

    public static Camera getCamera(int id) 
    {
        return null;
    }

    public void disposeImages() 
    {

    }

    public int getAttribute(int attr) 
    {
        return 0;
    }

    public int[] getAvailableFocusModes() 
    {
        return null;
    }

    public int[][] getAvailableFrameSizes() 
    {
        return null;
    }

    public int[][] getAvailableMovieSizes() 
    {
        return null;
    }

    public int[][] getAvailablePictureSizes() 
    {
        return null;
    }

    public int getFocusMode() 
    {
        return 0;
    }

    public MediaImage getImage(int index) 
    {
        return null;
    }

    public long getImageLength(int index) 
    {
        return 0;
    }

    public java.io.InputStream getInputStream(int index) 
    {
        return null;
    }

    public long getMaxImageLength() 
    {
        return 0;
    }

    public int getNumberOfImages() 
    {
        return 0;
    }

    public boolean isAvailable(int attr) 
    {
        return false;
    }

    public void setAttribute(int attr, int value) 
    {

    }

    public void setFocusMode(int mode) 
    {

    }

    public void takePicture() 
    {

    }

    public void takeMovie() 
    {

    }
}
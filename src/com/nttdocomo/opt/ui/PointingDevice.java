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
package com.nttdocomo.opt.ui;

import org.recompile.mobile.Mobile;

public class PointingDevice 
{

    private static int x = -1, y = -1;
    private static boolean enabled = false;

    public static boolean isEnabled() { return enabled; }

    public static void setEnabled(boolean enabled) 
    { 
        if(!enabled) 
        {
            x = -1;
            y = -1;
        }
        PointingDevice.enabled = enabled; 
    }

    public static int getX() { return x; }

    public static int getY() { return y; }

    public static void setX(int x) { PointingDevice.x = x; }

    public static void setY(int y) { PointingDevice.y = y; }
}
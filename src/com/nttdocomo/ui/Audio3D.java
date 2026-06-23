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
package com.nttdocomo.ui;

public class Audio3D 
{

    // TODO: Properly implement this class
    public static final int MODE_CONTROL_BY_APP = 2;
    public static final int MODE_CONTROL_BY_DATA = 1;
    public static final int SOUND_MOTION_COMPLETE = 1;

    private boolean enabled;
    private int resources;

    private Audio3DListener listener;
    private Audio3DLocalization localization;

    public Audio3D() 
    {
        this.enabled = false;
        this.resources = 0;
    }

    public void disable() 
    {
        if (!enabled) { return; }
        enabled = false;
        resources = 0;
    }

    public void enable(int mode) 
    {
        if (enabled) throw new IllegalStateException();
        if (mode != MODE_CONTROL_BY_APP && mode != MODE_CONTROL_BY_DATA) throw new IllegalArgumentException();
        int res = (mode == MODE_CONTROL_BY_APP) ? 1 : getFreeResources();
        enable(mode, res);
    }

    public void enable(int mode, int resources) 
    {
        if (enabled) throw new IllegalStateException();
        if (resources <= 0) throw new IllegalArgumentException();
        if (mode != MODE_CONTROL_BY_APP && mode != MODE_CONTROL_BY_DATA) throw new IllegalArgumentException();
        this.resources = resources;
        enabled = true;
    }

    

    public MediaResource getMediaResource() { return null; }

    public static int getFreeResources() { return 1; }

    public static int getResources() { return 1; }

    public int getTimeResolution()  { return 1; }

    public boolean isEnabled() { return enabled; }

    public void setListener(Audio3DListener listener) { this.listener = listener; }

    public void setLocalization(Audio3DLocalization localization) { this.localization = localization; }
}
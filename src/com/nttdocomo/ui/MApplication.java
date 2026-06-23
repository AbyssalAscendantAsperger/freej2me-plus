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

public abstract class MApplication extends IApplication 
{

    // TODO: Actually implement this
    public static final int CLOCK_TICK_EVENT = 3;
    public static final int FOLD_CHANGED_EVENT = 4;
    public static final int MODE_CHANGED_EVENT = 1;
    public static final int WAKEUP_TIMER_EVENT = 2;

    private int wakeupTimer;
    private boolean isActive = false;

    public MApplication() { }

    public final void deactivate() { }

    public final int getWakeupTimer() 
    {
        return wakeupTimer;
    }

    public final boolean isActive() 
    {
        return isActive;
    }

    public void processSystemEvent(int type, int param) 
    {
        switch (type) 
        {
            case CLOCK_TICK_EVENT:
                break;
            case WAKEUP_TIMER_EVENT:
                break;
            case MODE_CHANGED_EVENT:
                break;
            case FOLD_CHANGED_EVENT:
                break;
            default:
                break;
        }
    }

    public final void resetWakeupTimer() { wakeupTimer = -1; }

    public final void setClockTick(boolean b) { }

    public final void setWakeupTimer(int time) 
    {
        if (time < 0) { throw new IllegalArgumentException("Time must be non-negative."); }
        wakeupTimer = time;
    }

    public final void sleep() { isActive = false; }
}
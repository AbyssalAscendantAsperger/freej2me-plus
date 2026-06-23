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
package com.nttdocomo.util;

import com.nttdocomo.ui.UIException;
import java.util.TimerTask;

public final class Timer implements TimeKeeper 
{

    private TimerListener listener;
    private int interval;
    private boolean repeat;
    private boolean isRunning;
    private boolean isDisposed = false;
    private java.util.Timer timer;
    
    public Timer() 
    {
        this.interval = getMinTimeInterval();
        this.repeat = false; 
        this.listener = null; 
        this.isRunning = false;
    }

    @Override
    public void dispose() 
    {
        stop();
        isDisposed = true;
        listener = null;
        timer = null;
    }

	public int getMinTimeInterval() { return getResolution(); } // 1 ms is also the min time interval

    @Override
    public int getResolution() { return 1; } // 1 ms resolution

    public void setListener(TimerListener listener) 
    {
        if (isRunning) { throw new UIException(1, "Cannot set listener while timer is running."); }
        this.listener = listener;
    }

    public void setRepeat(boolean repeat) 
    {
        if (isRunning) { throw new UIException(1, "Cannot set repeat while timer is running."); }
        this.repeat = repeat;
    }

    public void setTime(int interval) 
    {
        if (isRunning) { throw new UIException(1, "Cannot set time while timer is running."); }
        if (interval < 0) { throw new IllegalArgumentException("Interval cannot be negative."); }
        this.interval = interval;
    }

    @Override
    public void start() 
    {
        if (isRunning) { throw new UIException(1, "Timer is already running."); }

        timer = new java.util.Timer();
        TimerTask task = new TimerTask() 
        {
            @Override
            public void run() 
            {
                if (listener != null) { listener.timerExpired(Timer.this); }
                if (!repeat) { stop(); }
            }
        };

        timer.schedule(task, interval, repeat ? interval : 0);
        isRunning = true;
    }

    @Override
    public void stop() 
    {
        if (!isRunning) { return; }
        timer.cancel();
        isRunning = false;
    }
}
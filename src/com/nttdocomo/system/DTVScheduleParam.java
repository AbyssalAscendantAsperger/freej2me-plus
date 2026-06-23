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
package com.nttdocomo.system;

import com.nttdocomo.util.ScheduleDate;

public final class DTVScheduleParam 
{

    private int affiliationId = DTVSchedule.AFFILIATION_ID_NONE;
    private int frequency = DTVSchedule.FREQUENCY_NONE;
    private int serviceId = DTVSchedule.SERVICE_ID_NONE;
    private String serviceName = null;
    private java.util.Calendar startTime = null;
    private java.util.Calendar endTime = null;
    private int repeatType = ScheduleDate.ONETIME;
    private String eventName = null;

    public DTVScheduleParam() { }

    public void setAffiliationId(int affiliationId) 
    {
        if (affiliationId < -1 || affiliationId > 11) 
        {
            throw new IllegalArgumentException("Invalid affiliationId");
        }
        this.affiliationId = affiliationId;
    }

    public void setFrequency(int frequency) 
    {
        if (frequency < 13 || frequency > 62) 
        {
            throw new IllegalArgumentException("Invalid frequency");
        }
        this.frequency = frequency;
    }

    public void setServiceId(int serviceId) 
    {
        if (serviceId < -1 || serviceId > 216) 
        {
            throw new IllegalArgumentException("Invalid serviceId");
        }
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) 
    {
        this.serviceName = serviceName;
    }

    public void setStartTime(java.util.Calendar startTime) 
    {
        if (!(startTime instanceof java.util.Calendar)) 
        {
            throw new IllegalArgumentException("Invalid startTime object");
        }
        this.startTime = startTime;
    }

    public void setEndTime(java.util.Calendar endTime) 
    {
        if (!(endTime instanceof java.util.Calendar)) 
        {
            throw new IllegalArgumentException("Invalid endTime object");
        }
        this.endTime = endTime;
    }

    public void setRepeatType(int type) 
    {
        if (type < ScheduleDate.ONETIME || type > ScheduleDate.YEARLY) 
        {
            throw new IllegalArgumentException("Invalid repeatType");
        }
        this.repeatType = type;
    }

    public void setEventName(String eventName) 
    {
        this.eventName = eventName;
    }
}
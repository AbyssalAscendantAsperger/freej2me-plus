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

import java.util.Calendar;
import java.util.TimeZone;

public class ScheduleDate 
{
    public static final int ONETIME = 0x01;
    public static final int DAILY = 0x02;
    public static final int WEEKLY = 0x04;
    public static final int MONTHLY = 0x08;
    public static final int YEARLY = 0x10;

    private Calendar calendar;
    private int type;

    public ScheduleDate(int type) { this(type, TimeZone.getDefault()); }

    public ScheduleDate(int type, TimeZone zone) 
    {
        if (type != ONETIME && type != DAILY && type != WEEKLY && type != MONTHLY && type != YEARLY) { throw new IllegalArgumentException("Invalid schedule type"); }

        this.type = type;
        this.calendar = Calendar.getInstance(zone);
        this.calendar.setTimeInMillis(System.currentTimeMillis());
    }

    public int getType() { return type; }

    public int get(int field) { return calendar.get(field); }

    public void set(int field, int value) 
    {
        switch (type) 
        {
            case ONETIME:
                if (field != Calendar.YEAR && field != Calendar.MONTH && field != Calendar.DATE &&
                    field != Calendar.HOUR_OF_DAY && field != Calendar.MINUTE) 
                    {
                    throw new IllegalArgumentException("Invalid field for ONETIME");
                }
                break;
            case DAILY:
                if (field != Calendar.HOUR_OF_DAY && field != Calendar.MINUTE) 
                {
                    throw new IllegalArgumentException("Invalid field for DAILY");
                }
                break;
            case WEEKLY:
                if (field != Calendar.DAY_OF_WEEK && field != Calendar.HOUR_OF_DAY && field != Calendar.MINUTE) 
                {
                    throw new IllegalArgumentException("Invalid field for WEEKLY");
                }
                break;
            case MONTHLY:
                if (field != Calendar.DATE && field != Calendar.HOUR_OF_DAY && field != Calendar.MINUTE) 
                {
                    throw new IllegalArgumentException("Invalid field for MONTHLY");
                }
                break;
            case YEARLY:
                if (field != Calendar.MONTH && field != Calendar.DATE && field != Calendar.HOUR_OF_DAY && field != Calendar.MINUTE) 
                {
                    throw new IllegalArgumentException("Invalid field for YEARLY");
                }
                break;
        }
        calendar.set(field, value);
    }
}
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

public class ConnectionRefusedException extends java.io.IOException 
{
    public static final int APPLICATION_NOT_EXIST = 1;
    public static final int BUSY_RESOURCE = 2;
    public static final int RACE_CONDITION = 6;
    public static final int REFUSED_BY_ADF_SETTING = 4;
    public static final int REFUSED_BY_APPLICATION = 5;
    public static final int REFUSED_BY_USER_SETTING = 3;
    public static final int UNDEFINED = 0;

    private final int status;

    public ConnectionRefusedException() { this.status = UNDEFINED; }

    public ConnectionRefusedException(int status) { this.status = status; }

    public ConnectionRefusedException(int status, String msg) 
    {
        super(msg);
        this.status = status;
    }

    public int getStatus() { return status; }
}
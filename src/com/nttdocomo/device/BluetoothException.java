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

public class BluetoothException extends RuntimeException 
{
    public static final int UNDEFINED = 0;
    public static final int SELF_MODE = 1;
    public static final int TIMEOUT = 2;
    public static final int USER_ABORT = 3;
    public static final int LINKKEY_ERROR = 4;
    public static final int UNKNOWN_PROFILE = 5;

    private int status;

    public BluetoothException() { this.status = UNDEFINED; }

    public BluetoothException(int status) { this.status = status; }

    public BluetoothException(int status, String msg) 
    {
        super(msg);
        this.status = status;
    }

    public int getStatus() { return status; }
}
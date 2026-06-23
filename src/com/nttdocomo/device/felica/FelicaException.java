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

public final class FelicaException extends Exception 
{
    public static final int ID_ACTIVATE_ERROR = 0x06;
    public static final int ID_CHECKPIN_ERROR = 0x0e;
    public static final int ID_CLOSE_ERROR = 0x02;
    public static final int ID_EXECUTEPIN_ERROR = 0x07;
    public static final int ID_GETADHOCSTATE_ERROR = 0x0f;
    public static final int ID_GETISSUEINFO_ERROR = 0x0a;
    public static final int ID_GETKEYVERSION_ERROR = 0x08;
    public static final int ID_INACTIVATE_ERROR = 0x09;
    public static final int ID_LOCKED_NODELIST_ERROR = 0x11;
    public static final int ID_NEGOTIATE_BAUDRATE_ERROR = 0x0d;
    public static final int ID_OPEN_ERROR = 0x01;
    public static final int ID_POLLING_ERROR = 0x03;
    public static final int ID_READ_ERROR = 0x04;
    public static final int ID_RESET_ERROR = 0x10;
    public static final int ID_SETPARAMETER_ERROR = 0x0b;
    public static final int ID_TURNOFF_RFPOWER_ERROR = 0x0c;
    public static final int ID_UNDEFINED_ERROR = 0x00;
    public static final int ID_WRITE_ERROR = 0x05;
    public static final int TYPE_BLOCK_COUNT_OVER_ERROR = 0x0e;
    public static final int TYPE_BLOCK_NO_ERROR = 0x03;
    public static final int TYPE_CASHBACK_ERROR = 0x02;
    public static final int TYPE_CYCLIC_ERROR = 0x04;
    public static final int TYPE_DEVICE_ERROR = 0x11;
    public static final int TYPE_EXTERNAL_CARD_ERROR = 0x13;
    public static final int TYPE_FORMAT_ERROR = 0x09;
    public static final int TYPE_FREEAREA_POLLING_ERROR = 0x14;
    public static final int TYPE_FREEAREA_READ_ERROR = 0x0b;
    public static final int TYPE_FREEAREA_RESET_ERROR = 0x16;
    public static final int TYPE_FREEAREA_WRITE_ERROR = 0x0c;
    public static final int TYPE_IDM_MISMATCH_ERROR = 0x15;
    public static final int TYPE_ILLEGAL_STATE_ERROR = 0x10;
    public static final int TYPE_PIN_COUNT_OVER_ERROR = 0x0d;
    public static final int TYPE_PIN_LOCK_OUT_ERROR = 0x0f;
    public static final int TYPE_PIN_REQUIRED_ERROR = 0x05;
    public static final int TYPE_PURSE_ERROR = 0x01;
    public static final int TYPE_SELF_MODE = 0x17;
    public static final int TYPE_SERVICE_CODE_ERROR = 0x07;
    public static final int TYPE_SETATTRIBUTE_ERROR = 0x06;
    public static final int TYPE_SETPIN_ERROR = 0x08;
    public static final int TYPE_TIMEOUT_ERROR = 0x0a;
    public static final int TYPE_UNDEFINED_ERROR = 0x00;
    public static final int TYPE_UNEXPECTED_ERROR = 0x12;

    private final int id;
    private final int type;

    public FelicaException(int id, int type) 
    {
        this.id = id;
        this.type = type;
    }

    public int getID() { return id; }

    public int getType() { return type; }
}
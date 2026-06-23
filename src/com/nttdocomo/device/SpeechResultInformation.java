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

public class SpeechResultInformation 
{
    public static final int STATUS_CODE_SUCCESS = 0;
    public static final int STATUS_CODE_ERROR = 200;
    public static final int STATUS_CODE_ERROR_NOSOUND = 201;
    public static final int STATUS_CODE_ERROR_NOISE = 202;
    public static final int STATUS_CODE_ERROR_LITTLE_VOICE = 203;
    public static final int STATUS_CODE_ERROR_BIG_VOICE = 204;
    public static final int STATUS_CODE_ERROR_FAST_SPEAKING = 205;
    public static final int STATUS_CODE_ERROR_SLOW_SPEAKING = 206;
    public static final int STATUS_CODE_ERROR_TIMEOUT = 280;
    public static final int STATUS_CODE_ERROR_NORESULT = 281;
    public static final int STATUS_CODE_WARNING = 100;
    public static final int STATUS_CODE_WARNING_NOISE = 101;
    public static final int STATUS_CODE_WARNING_LITTLE_VOICE = 102;
    public static final int STATUS_CODE_WARNING_BIG_VOICE = 103;
    public static final int STATUS_CODE_WARNING_FAST_SPEAKING = 104;
    public static final int STATUS_CODE_WARNING_SLOW_SPEAKING = 105;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_NBEST = 1;

    public int getStatusCode() 
    {
        return STATUS_CODE_SUCCESS;
    }

    public int getType() 
    {
        return TYPE_NONE;
    }

    public SpeechResult[] getResult() 
    {
        return null;
    }
}
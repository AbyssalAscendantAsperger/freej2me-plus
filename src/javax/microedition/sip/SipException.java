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
package javax.microedition.sip;

public class SipException extends java.io.IOException 
{

    public static final byte ALREADY_RESPONDED = 1;
    public static final byte GENERAL_ERROR = 2;
    public static final byte INVALID_MESSAGE = 3;
    public static final byte INVALID_OPERATION = 4;
    public static final byte INVALID_STATE = 5;
    public static final byte TRANSACTION_UNAVAILABLE = 6;
    public static final byte TRANSPORT_NOT_SUPPORTED = 7;
    public static final byte UNKNOWN_LENGTH = 8;
    public static final byte UNKNOWN_TYPE = 9;

    private final byte errorCode;

    public SipException(byte errorCode) 
    {
        super();
        this.errorCode = (isValidErrorCode(errorCode)) ? errorCode : GENERAL_ERROR;
    }

    public SipException(String message, byte errorCode) 
    {
        super(message);
        this.errorCode = (isValidErrorCode(errorCode)) ? errorCode : GENERAL_ERROR;
    }

    public byte getErrorCode() { return errorCode; }

    private boolean isValidErrorCode(byte errorCode) 
    {
        return errorCode >= ALREADY_RESPONDED && errorCode <= UNKNOWN_TYPE;
    }
}
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
package com.nttdocomo.security;

public class SignatureException extends Exception 
{
    public static final int UNDEFINED = 0;
    public static final int PIN2_BLOCKED = 1;
    public static final int PIN2_ALREADY_BLOCKED = 2;
    public static final int PUK2_BLOCKED = 3;
    public static final int PUK2_ALREADY_BLOCKED = 4;
    public static final int PIN2_CANCELED = 5;
    public static final int PUK2_CANCELED = 6;
    public static final int SIGN_ERROR = 7;
    public static final int ILLEGAL_CONTENT = 8;
    public static final int SECURITY_CODE_REJECTED = 9;
    public static final int ENCRYPTED_DIGEST_ERROR = 10;

    private final int status;

    public SignatureException() { this.status = UNDEFINED; }

    public SignatureException(int status) { this.status = status; }

    public SignatureException(int status, String message) 
    {
        super(message);
        this.status = status;
    }

    public int getStatus() { return status; }
}
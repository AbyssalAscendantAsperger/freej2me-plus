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
package com.nttdocomo.io;

public interface ObexConnection extends javax.microedition.io.StreamConnection 
{

    static int ACCEPTED = 0x22;
    static int BAD_GATEWAY = 0x52;
    static int BAD_REQUEST = 0x40;
    static int COMM_MODE_IRDA = 0;
    static int COMM_MODE_IRSIMPLE_INTERACTIVE = 2;
    static int COMM_MODE_IRSIMPLE_UNILATERALLY = 1;
    static int CONFLICT = 0x49;
    static int CONTINUE = 0x10;
    static int CREATED = 0x21;
    static int DATABASE_FULL = 0x60;
    static int DATABASE_LOCKED = 0x61;
    static int DISCONNECT = 0x81;
    static int FORBIDDEN = 0x43;
    static int GATEWAY_TIMEOUT = 0x54;
    static int GET = 0x83;
    static int GONE = 0x4a;
    static int HTTP_VERSION_NOT_SUPPORTED = 0x55;
    static int INTERNAL_SERVER_ERROR = 0x50;
    static int LENGTH_REQUIRED = 0x4b;
    static int METHOD_NOT_ALLOWED = 0x45;
    static int MOVED_PERMANENTLY = 0x31;
    static int MOVED_TEMPORARILY = 0x32;
    static int MULTIPLE_CHOICES = 0x30;
    static int NO_CONTENT = 0x24;
    static int NON_AUTHORITATIVE_INFORMATION = 0x23;
    static int NOT_ACCEPTABLE = 0x46;
    static int NOT_FOUND = 0x44;
    static int NOT_IMPLEMENTED = 0x51;
    static int NOT_MODIFIED = 0x34;
    static int PARTIAL_CONTENT = 0x26;
    static int PAYMENT_REQUIRED = 0x42;
    static int PRECONDITION_FAILED = 0x4c;
    static int PROXY_AUTHENTICATION_REQUIRED = 0x47;
    static int PUT = 0x82;
    static int REQUEST_ENTITY_TOO_LARGE = 0x4d;
    static int REQUEST_TIME_OUT = 0x48;
    static int REQUEST_URL_TOO_LARGE = 0x4e;
    static int RESET_CONTENT = 0x25;
    static int SEE_OTHER = 0x33;
    static int SERVICE_UNAVAILABLE = 0x53;
    static int SUCCESS = 0x20;
    static int UNAUTHORIZED = 0x41;
    static int UNSUPPORTED_MEDIA_TYPE = 0x4f;
    static int USE_PROXY = 0x35;

    int getCommMode();

    int getContentLength();

    String getName();

    long getTime();

    String getType();

    void setName(String name);

    void setTime(long time);

    void setType(String type);
}
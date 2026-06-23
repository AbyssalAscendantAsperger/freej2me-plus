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
package javax.microedition.global;

public final class ResourceException extends RuntimeException 
{

    public static final int DATA_ERROR = 5;
    public static final int METAFILE_NOT_FOUND = 7;
    public static final int NO_RESOURCES_FOR_BASE_NAME = 3;
    public static final int NO_SYSTEM_DEFAULT_LOCALE = 4;
    public static final int RESOURCE_NOT_FOUND = 1;
    public static final int UNKNOWN_ERROR = 0;
    public static final int UNKNOWN_RESOURCE_TYPE = 6;
    public static final int WRONG_RESOURCE_TYPE = 2;

    private final int errorCode;

    public ResourceException(int err, String message) 
    {
        super(message);
        if (err < DATA_ERROR || err > WRONG_RESOURCE_TYPE) { throw new IllegalArgumentException("Undefined error code: " + err); }

        this.errorCode = err;
    }

    public int getErrorCode() { return errorCode; }
}
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

import org.recompile.mobile.Base64Util;

import java.io.UnsupportedEncodingException;

public class Base64 
{

    public static String encode(String str) throws UnsupportedEncodingException
    {
        if (str == null) { throw new NullPointerException("Input string cannot be null"); }

        return encode(str.getBytes("UTF-8"));
    }

    public static String encode(byte[] bytes) throws UnsupportedEncodingException
    {
        if (bytes == null) { throw new NullPointerException("Input byte array cannot be null"); }

        return bytes.length == 0 ? "" : Base64Util.encode(bytes);
    }

    public static String encode(byte[] bytes, int off, int len) throws UnsupportedEncodingException
    {
        if (bytes == null) { throw new NullPointerException("Input byte array cannot be null"); }
        if (off < 0 || len < 0 || off + len > bytes.length) { throw new ArrayIndexOutOfBoundsException("Invalid offset or length"); }

        byte[] newBytes = new byte[len];

        System.arraycopy(bytes, off, newBytes, 0, len);

        return len == 0 ? "" : Base64Util.encode(newBytes);
    }

    public static byte[] decode(String str) throws UnsupportedEncodingException
    {
        if (str == null) { throw new NullPointerException("Input string cannot be null"); }

        return decode(str.getBytes("UTF-8"));
    }

    public static byte[] decode(byte[] bytes) throws UnsupportedEncodingException
    {
        if (bytes == null) { throw new NullPointerException("Input byte array cannot be null"); }

        return bytes.length == 0 ? new byte[0] : Base64Util.decode(bytes);
    }

    public static byte[] decode(byte[] bytes, int off, int len) throws UnsupportedEncodingException
    {
        if (bytes == null) { throw new NullPointerException("Input byte array cannot be null"); }
        if (off < 0 || len < 0 || off + len > bytes.length) { throw new ArrayIndexOutOfBoundsException("Invalid offset or length"); }

        byte[] newBytes = new byte[len];

        System.arraycopy(bytes, off, newBytes, 0, len);

        return len == 0 ? new byte[0] : Base64Util.decode(newBytes);
    }
}
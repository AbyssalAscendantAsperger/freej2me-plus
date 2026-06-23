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
package org.recompile.mobile;

public class Base64Util 
{

    private static final char[] base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String encode(byte[] data) 
    {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < data.length; i += 3) 
        {
            int b1 = data[i] & 0xFF;
            int b2 = (i + 1 < data.length) ? (data[i + 1] & 0xFF) : 0;
            int b3 = (i + 2 < data.length) ? (data[i + 2] & 0xFF) : 0;

            sb.append(base64Chars[b1 >> 2]);
            sb.append(base64Chars[((b1 & 0x03) << 4) | (b2 >> 4)]);
            sb.append((i + 1 < data.length) ? base64Chars[((b2 & 0x0F) << 2) | (b3 >> 6)] : '=');
            sb.append((i + 2 < data.length) ? base64Chars[b3 & 0x3F] : '=');
        }

        return sb.toString();
    }

    public static byte[] decode(String base64) 
    {
        int paddingCount = 0;
        if (base64.endsWith("=")) paddingCount++;
        if (base64.endsWith("==")) paddingCount++;

        int byteCount = (base64.length() * 3) / 4 - paddingCount;
        byte[] data = new byte[byteCount];

        for (int i = 0, j = 0; i < base64.length(); i += 4) {
            int b1 = indexOf(base64.charAt(i));
            int b2 = indexOf(base64.charAt(i + 1));
            int b3 = indexOf(base64.charAt(i + 2));
            int b4 = indexOf(base64.charAt(i + 3));

            data[j++] = (byte) ((b1 << 2) | (b2 >> 4));
            if (j < byteCount) data[j++] = (byte) ((b2 << 4) | (b3 >> 2));
            if (j < byteCount) data[j++] = (byte) ((b3 << 6) | b4);
        }

        return data;
    }

    public static byte[] decode(byte[] base64Data) 
    {
        int paddingCount = 0;
        if (base64Data[base64Data.length - 1] == '=') paddingCount++;
        if (base64Data[base64Data.length - 2] == '=') paddingCount++;

        int byteCount = (base64Data.length * 3) / 4 - paddingCount;
        byte[] decodedData = new byte[byteCount];

        for (int i = 0, j = 0; i < base64Data.length; i += 4) 
        {
            int b1 = indexOf((char) base64Data[i]);
            int b2 = indexOf((char) base64Data[i + 1]);
            int b3 = indexOf((char) base64Data[i + 2]);
            int b4 = indexOf((char) base64Data[i + 3]);

            decodedData[j++] = (byte) ((b1 << 2) | (b2 >> 4));
            if (j < byteCount) decodedData[j++] = (byte) ((b2 << 4) | (b3 >> 2));
            if (j < byteCount) decodedData[j++] = (byte) ((b3 << 6) | b4);
        }

        return decodedData;
    }

    private static int indexOf(char c) 
    {
        if (c >= 'A' && c <= 'Z') { return c - 'A'; }
        if (c >= 'a' && c <= 'z') { return c - 'a' + 26; }
        if (c >= '0' && c <= '9') { return c - '0' + 52; }
        if (c == '+') { return 62; }
        if (c == '/') { return 63; }
        return -1; // Invalid character
    }
}
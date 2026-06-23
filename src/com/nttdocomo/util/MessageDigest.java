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

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MessageDigest 
{
    private java.security.MessageDigest digest;
    private String algorithm;

    private MessageDigest(String algorithm) throws NoSuchAlgorithmException 
    {
        this.algorithm = algorithm;
        this.digest = java.security.MessageDigest.getInstance(algorithm);
    }

    public static MessageDigest getInstance(String algorithm) 
    {
        try { return new MessageDigest(algorithm); } 
        catch (NoSuchAlgorithmException e) { throw new IllegalArgumentException("Unsupported algorithm: " + algorithm); }
    }

    public void update(byte input) { digest.update(input); }

    public void update(byte[] buf) 
    {
        if (buf == null) { throw new NullPointerException("Input byte array cannot be null"); }

        digest.update(buf);
    }

    public void update(byte[] buf, int off, int len) {
        if (buf == null) { throw new NullPointerException("Input byte array cannot be null"); }
        if (off < 0 || len < 0 || off + len > buf.length) { throw new ArrayIndexOutOfBoundsException("Invalid offset or length"); }

        digest.update(buf, off, len);
    }

    public byte[] digest() 
    {
        byte[] result = digest.digest();
        reset();
        return result;
    }

    public byte[] digest(byte[] buf) 
    {
        update(buf);
        return digest();
    }

    public int digest(byte[] buf, int off, int len) 
    {
        if (buf == null) { throw new NullPointerException("Output byte array cannot be null"); }
        if (off < 0 || len < 0 || off + len > buf.length) { throw new ArrayIndexOutOfBoundsException("Invalid offset or length"); }

        byte[] result = digest();
        int length = Math.min(result.length, len);
        System.arraycopy(result, 0, buf, off, length);
        reset();
        return length;
    }

    public void reset() { digest.reset(); }

    public String getAlgorithm() { return algorithm; }

    public int getDigestLength() { return digest.getDigestLength(); }

    public static boolean isEqual(byte[] src, byte[] dst) 
    {
        if (src == null || dst == null) { throw new NullPointerException("Both source and destination arrays must be non-null"); }
        return Arrays.equals(src, dst);
    }

    @Override
    public String toString() { return algorithm + " Message Digest"; }
}
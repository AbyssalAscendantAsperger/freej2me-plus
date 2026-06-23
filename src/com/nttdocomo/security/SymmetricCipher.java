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

public final class SymmetricCipher 
{
    public static final int DES_CBC_PKCS7PADDING = 1;
    public static final int DES_EDE_CBC_PKCS7PADDING = 2;
    public static final int AES_128_CBC_PKCS7PADDING = 3;
    public static final int AES_192_CBC_PKCS7PADDING = 4;
    public static final int AES_256_CBC_PKCS7PADDING = 5;

    private byte[] buffer;

    public SymmetricCipher(int algorithm, byte[] iv, boolean encrypt) 
    {
        try 
        {
            String algorithmName;

            if (algorithm == DES_CBC_PKCS7PADDING) { algorithmName = "DES/CBC/PKCS5Padding"; } 
            else if (algorithm == DES_EDE_CBC_PKCS7PADDING) { algorithmName = "DESede/CBC/PKCS5Padding"; } 
            else if (algorithm == AES_128_CBC_PKCS7PADDING) { algorithmName = "AES/CBC/PKCS5Padding"; } 
            else if (algorithm == AES_192_CBC_PKCS7PADDING) { algorithmName = "AES/CBC/PKCS5Padding"; } 
            else if (algorithm == AES_256_CBC_PKCS7PADDING) { algorithmName = "AES/CBC/PKCS5Padding"; } 
            else { throw new IllegalArgumentException("Invalid algorithm."); }

            buffer = new byte[0];
        } 
        catch (Exception e) { throw new RuntimeException("Cipher initialization failed.", e); }
    }

    public byte[] execute(byte[] data) 
    {
        return null;
    }

    public byte[] executeFinal(byte[] data) throws CipherException 
    {
       return null;
    }

    public void reset() {  buffer = new byte[0]; }
}
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

public final class CipherPBE 
{
    public static final int HASH_SHA1 = 1;

    private byte[] password;
    private byte[] salt;
    private int iterationCount;
    private int hashAlgorithm;
    private int cipher;
    private byte[] iv;

    public CipherPBE(byte[] password, byte[] salt, int iterationCount, int hashAlgorithm, int cipher, byte[] iv) 
    {
        if (password == null || salt == null || iv == null) { throw new NullPointerException(); }
        if (password.length < 1) { throw new IllegalArgumentException("Password must have at least one character."); }
        if (salt.length < 8) { throw new IllegalArgumentException("Salt must be at least 64 bits."); }
        if (iterationCount < 1000) { throw new IllegalArgumentException("Iteration count must be at least 1000."); }
        
        this.password = password;
        this.salt = salt;
        this.iterationCount = iterationCount;
        this.hashAlgorithm = hashAlgorithm;
        this.cipher = cipher;
        this.iv = iv;
    }

    public CipherSessionKey createCipherSessionKey(byte[] encryptedSessionKey) throws CipherException 
    {
        if (encryptedSessionKey == null) { throw new NullPointerException(); }

        return null;
    }

    public CipherSessionKey createCipherSessionKey(int cipher) 
    {
        return null;
    }
}
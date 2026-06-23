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

import com.nttdocomo.system.InterruptedOperationException;
import com.nttdocomo.system.StoreException;

public class PKCS7Signer 
{
    public static final int DATA = 0;
    public static final int SIGNED_DATA = 1;

    private int contentType = DATA;
    private String digestAlgorithm = "SHA-1";
    private byte[] signatureData;

    public PKCS7Signer() { }

    public int getContentType() { return contentType; }

    public String getDigestAlgorithm() { return digestAlgorithm; }

    public void reset() { signatureData = null; }

    public void setContentType(int contentType) 
    {
        if (contentType != DATA && contentType != SIGNED_DATA) { throw new IllegalArgumentException("Invalid content type."); }

        this.contentType = contentType;
    }

    public void setDigestAlgorithm(String hashAlgorithm) 
    {
        if (hashAlgorithm == null) { throw new NullPointerException(); }
        if (!hashAlgorithm.equals("MD5") && !hashAlgorithm.equals("SHA-1")) { throw new IllegalArgumentException("Unsupported hash algorithm."); }
        this.digestAlgorithm = hashAlgorithm;
    }

    public void update(byte input) { }

    public void update(byte[] buf) 
    {
        if (buf == null) { throw new NullPointerException(); }
    }

    public void update(byte[] buf, int off, int len) 
    {
        if (buf == null) { throw new NullPointerException(); }
        if (off < 0 || len < 0 || off + len > buf.length) { throw new ArrayIndexOutOfBoundsException(); }
    }

    public PKCS7SignedData sign() throws SignatureException, InterruptedOperationException 
    {
        return null;
    }

    public PKCS7SignedData sign(int certificateId) throws SignatureException, InterruptedOperationException, StoreException 
    {
        return null;
    }
}
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
package com.nttdocomo.device.felica;

public class OfflineFelica 
{

    public static final int CARD_INTERNAL = 0;
    public static final int CARD_EXTERNAL = 1;
    public static final long PARAM_NODE_CODE_LEN_2 = 0x0000000000000000;
    public static final long PARAM_NODE_CODE_LEN_4 = 0x0000000000010000;

    public PINAttributeData[] checkPIN(CheckPINParameters param) throws FelicaException, java.io.IOException 
    {
        return null;
    }

    public void executePIN(PINParameters param) throws FelicaException, java.io.IOException 
    {

    }

    public int getCard() 
    {
        return CARD_INTERNAL;
    }

    public byte[] getCardVersion() 
    {
        return null;
    }

    public byte[] getContainerIssueInfo() 
    {
        return null;
    }

    public byte[] getIDm() 
    {
        return null;
    }

    public byte[] getKeyVersion(int serviceCode) 
    {
        return null;
    }

    public int getNodeCodeLength() 
    {
        return 2; // Example return value
    }

    public byte[] getResponseTimeInfo() 
    {
        return null;
    }

    public int getSystemCode() 
    {
        return 0;
    }

    public int getTimeout() 
    {
        return 500;
    }

    public FelicaData[] read(InputPINParameters pinParam, ReadParameters readParam) 
    {
        return null;
    }

    public FelicaData[] read(ReadParameters param) 
    {
        return null;
    }

    public void setNodeCodeLength(int nodeCodeLen) 
    {

    }

    public void setParameter(long param) 
    {

    }

    public void setTimeout(int timeout) 
    {

    }

    public void write(InputPINParameters pinParam, WriteParameters writeParam) 
    {

    }

    public void write(WriteParameters param) 
    {

    }
}
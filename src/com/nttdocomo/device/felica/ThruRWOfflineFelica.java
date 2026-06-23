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

public final class ThruRWOfflineFelica extends OfflineFelica 
{
    public static final int BAUDRATE_212_KBPS = 212;
    public static final int BAUDRATE_424_KBPS = 424;

    public ThruRWOfflineFelica() { setTimeout(1000); }

    public PINAttributeData[] checkPIN(CheckPINParameters param) throws FelicaException, java.io.IOException 
    {
        return null;
    }

    public void executePIN(PINParameters param) throws FelicaException, java.io.IOException 
    {
        
    }

    public byte[] getContainerIssueInfo() 
    {
        return null;
    }

    public byte[] getKeyVersion(int serviceCode) 
    {
        return null;
    }

    public int negotiateBaudRate(int baudrate) 
    {
        return baudrate;
    }

    public FelicaData[] read(ReadParameters param) 
    {
        return null;
    }

    public void setParameter(long param)  
    { 

    }

    public void setTimeout(int timeout) 
    { 
        
    }

    public void write(WriteParameters param) 
    {

    }
}
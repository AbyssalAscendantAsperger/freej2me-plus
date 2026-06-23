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

public final class Felica
{

    public static void open() throws FelicaException 
    {

    }

    public static void close() throws FelicaException 
    {

    }

    public static OnlineFelica getOnlineFelica() 
    {
        return null;
    }

    public static OfflineFelica getOfflineFelica(int card, int systemCode) throws FelicaException, java.io.IOException 
    {
        return null;
    }

    public static FreeArea getFreeArea() 
    {
        return null;
    }

    public static AdhocDataTransfer getAdhocDataTransfer() 
    {
        return null;
    }

    public static void turnOffRFPower() 
    {

    }

    public static int[] getLockedNodeList() 
    {
        return null;
    }

    public static void setFelicaPushListener(FelicaPushListener listener) 
    {

    }

    public static void reset() 
    {
    }

    public static void activate() 
    {
        throw new UnsupportedOperationException("activate() is not supported from DoJa-5.0 (903i) onwards.");
    }

    public static void inactivate() 
    {
        throw new UnsupportedOperationException("inactivate() is not supported from DoJa-5.0 (903i) onwards.");
    }
}
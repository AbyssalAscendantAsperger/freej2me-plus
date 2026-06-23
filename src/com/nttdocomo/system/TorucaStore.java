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
package com.nttdocomo.system;

public final class TorucaStore
{

    public static int addEntry(Toruca toruca) throws InterruptedOperationException 
    {
        return -1;
    }

    public static int[] findByHostAndIpid(String host, String ipid) throws InterruptedOperationException 
    {
        return null;
    }

    public static TorucaStore getEntry(int id) throws StoreException 
    {
        return null;
    }

    public int getId() 
    {
        return 0;
    }

    public Toruca getToruca() 
    {
        return new Toruca();
    }

    public static TorucaStore selectEntry() throws InterruptedOperationException 
    {
        return null;
    }

    public static int getRemainingBytes(Toruca toruca) 
    {
        return 0;
    }
}
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

public final class PurseDirectData extends PurseData 
{

    private long purseData;
    private long cashbackData;
    private byte[] userData;

    public PurseDirectData(long purseData, long cashBackData, byte[] userData, int execID) 
    {
        setPurseData(purseData);
        setCashbackData(cashBackData);
        setUserData(userData);
        setExecID(execID);
    }

    public long getPurseData() { return purseData; }

    public void setPurseData(long purseData) 
    {
        if (purseData < 0 || purseData >= (1 << 32)) { throw new IllegalArgumentException("Invalid purse data: " + purseData); }
        this.purseData = purseData;
    }

    public long getCashbackData() { return cashbackData; }

    public void setCashbackData(long cashbackData) 
    {
        if (cashbackData < 0 || cashbackData >= (1 << 32)) { throw new IllegalArgumentException("Invalid cashback data: " + cashbackData); }
        this.cashbackData = cashbackData;
    }

    public byte[] getUserData() { return userData.clone(); }

    public void setUserData(byte[] userData) 
    {
        if (userData == null) { throw new NullPointerException("User data cannot be null"); }

        this.userData = new byte[6];
        System.arraycopy(userData, 0, this.userData, 0, Math.min(userData.length, 6));
    }

    public int getDataType() { return FelicaData.TYPE_PURSE_DIRECT_DATA; }
}
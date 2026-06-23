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

import com.nttdocomo.lang.XString;
import com.nttdocomo.device.location.Location;

public final class PhoneBook implements MailConstants, PhoneBookConstants 
{

    public static int[] addEntry(PhoneBookParam param) 
    {
        return new int[0];
    }

    public static int[] addEntry(String name, String kana, String[] phoneNumbers, String[] mailAddresses, int groupId) 
    {
        return new int[0];
    }

    public static int[] addEntry(String name, String kana, String[] phoneNumbers, String[] mailAddresses, String groupName) 
    {
        return new int[0];
    }

    public static PhoneBook getEntry(int id) 
    {
        return null;
    }

    public int getGroupId() 
    {
        return 0;
    }

    public XString getGroupName() 
    {
        return null;
    }

    public int getId() 
    {
        return 0;
    }

    public XString getKana() 
    {
        return null;
    }

    public XString getKana(int part) 
    {
        return null;
    }

    public Location getLocation() 
    {
        return null;
    }

    public XString getMailAddress(int index, int part) 
    {
        return null;
    }

    public XString[] getMailAddresses(int part) 
    {
        return new XString[0];
    }

    public XString getName() 
    {
        return null;
    }
}
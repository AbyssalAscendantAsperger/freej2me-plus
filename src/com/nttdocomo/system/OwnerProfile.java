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

public final class OwnerProfile 
{

    public static final int ADDRESS = 12;
    public static final int ADDRESS_EXTENDED = 16;
    public static final int ADDRESS_LOCALITY = 14;
    public static final int ADDRESS_REGION = 13;
    public static final int ADDRESS_STREET = 15;
    public static final int BIRTH_DATE = 17;
    public static final int BIRTH_DATE_DAY = 20;
    public static final int BIRTH_DATE_MONTH = 19;
    public static final int BIRTH_DATE_YEAR = 18;
    public static final int EMAIL_ADDRESS_1 = 9;
    public static final int EMAIL_ADDRESS_2 = 10;
    public static final int FAMILY_NAME = 2;
    public static final int FAMILY_NAME_KANA = 5;
    public static final int GIVEN_NAME = 3;
    public static final int GIVEN_NAME_KANA = 6;
    public static final int KANA = 4;
    public static final int NAME = 1;
    public static final int POSTAL_CODE = 11;
    public static final int TELEPHONE_NUMBER_1 = 7;
    public static final int TELEPHONE_NUMBER_2 = 8;

    public static OwnerProfile getProfileData() throws InterruptedOperationException 
    {
        return null; 
    }

    public static OwnerProfile getProfileData(int[] items) throws InterruptedOperationException 
    {
        return null;
    }

    public int[] getSelectedItems() 
    {
        return new int[0];
    }

    public String getData(int item) 
    {
        return "";
    }
}
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
package com.nttdocomo.opt.ui;

public class PhoneResource 
{
    public static final int INFORMATIONDISPLAY_DATA_TITLE = 0;

    protected PhoneResource() { }

    public static byte[] getInformationDisplayData(int targetId, int dataType) 
    {
        return null;
    }

    public static void setInformationDisplay(int targetId, String title, long expire, int repeat, int[][] patterns) 
    {
        if (title == null) { throw new NullPointerException("Title cannot be null"); }
        if (patterns == null) { throw new NullPointerException("Patterns cannot be null"); }
        if (repeat < 0) { throw new IllegalArgumentException("Repeat count must be non-negative"); }
        if (targetId < 1 || targetId > 11) { throw new IllegalArgumentException("Invalid targetId: " + targetId); }
        // TODO
    }
}
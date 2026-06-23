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

import com.nttdocomo.ui.MediaImage;

public final class MovieStore
{

    public static int addEntry(MediaImage movie) throws InterruptedOperationException 
    {
        return -1;
    }

    public static MovieStore getEntry(int id) throws StoreException 
    {
        return null;
    }

    public static int[] getEntryIds(int folderId) throws StoreException 
    {
        return new int[0];
    }

    public int getId() 
    {
        return 0;
    }

    public MediaImage getImage() 
    {
        return null;
    }

    public java.io.InputStream getInputStream() 
    {
        return null;
    }

    public static MovieStore selectEntry() throws InterruptedOperationException 
    {
        return null;
    }
}
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

import org.recompile.mobile.Mobile;

public final class ImageStore 
{

    private ImageStore() { }

    public static int addEntry(MediaImage image) 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "addEntry(MediaImage) not implemented. ");
        return 0; 
    }

    public static int[] addEntry(MediaImage[] images) 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "addEntry(MediaImage[]) not implemented. ");
        return new int[0];
    }

    public static int addEntry(MediaImage image, boolean exclusive) 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "addEntry(MediaImage, boolean) not implemented. ");
        return 0;
    }

    public static ImageStore getEntry(int id) throws StoreException 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "getEntry(int) not implemented. ");
        return null; 
    }

    public static int[] getEntryIds(int folderId) throws StoreException 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "getEntryIds(int) not implemented. ");
        return new int[0]; 
    }

    public int getId() 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "getId() not implemented. ");
        return 0;
    }

    public MediaImage getImage() 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "getImage() not implemented. ");
        return null;
    }

    public java.io.InputStream getInputStream() 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "getInputStream() not implemented. ");
        return null;
    }

    public static ImageStore selectEntry() throws InterruptedOperationException 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "selectEntry() not implemented. ");
        return null;
    }

    public static int selectEntryId() throws InterruptedOperationException 
    {
        Mobile.log(Mobile.LOG_WARNING, ImageStore.class.getPackage().getName() + "." + ImageStore.class.getSimpleName() + ": " + "selectEntryId() not implemented. ");
        return -1;
    }
}
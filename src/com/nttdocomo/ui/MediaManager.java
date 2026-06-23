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
package com.nttdocomo.ui;

import com.nttdocomo.ui.impls.MediaImageImpl;
import com.nttdocomo.ui.impls.MediaSoundImpl;

import org.recompile.mobile.Mobile;

public final class MediaManager 
{
    
    public static MediaImage createMediaImage(int width, int height) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " createMediaImage w,h " + width + " " + height);
        return new MediaImageImpl(width, height);
    }
    
    public static MediaSound createMediaSound(int bytes) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " createMediaSound bytes: " + bytes);
        return new MediaSoundImpl(new byte[bytes]);
    }
    
    public static AvatarData getAvatarData(byte[] data) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getAvatarData bytes: " + data.length);
        return null;
        //return new AvatarData(data);
    }
    
    public static AvatarData getAvatarData(java.io.InputStream in) throws java.io.IOException
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getAvatarData inputstream: " + in.available());
        return null;
        //return new AvatarData(in);
    }
    
    public static AvatarData getAvatarData(String location) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getAvatarData str: " + location);
        return null;
        //return new AvatarData(location);
    }
    
    public static MediaData getData(byte[] data) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getData bytes: " + data.length);
        return null;
        //return new MediaData(data);
    }
    
    public static MediaData getData(java.io.InputStream in) throws java.io.IOException
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getData inputstream: " + in.available());
        return null;
        //return new MediaData(in);
    }
    
    public static MediaData getData(String location) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getData str: " + location);
        return null;
        //return new MediaData(location);
    }
    
    public static MediaImage getImage(byte[] data) 
    {
        Mobile.log(Mobile.LOG_DEBUG, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getImage bytes: " + data.length);
        return new MediaImageImpl(data);
    }
    
    public static MediaImage getImage(java.io.InputStream in) throws java.io.IOException
    {
        Mobile.log(Mobile.LOG_DEBUG, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getImage inputstream: " + in.available());
        return new MediaImageImpl(in);
    }
    
    public static MediaImage getImage(String location) throws java.io.IOException
    {
        Mobile.log(Mobile.LOG_DEBUG, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getImage str: " + location);
        return new MediaImageImpl(location);
    }
    
    public static MediaSound getSound(byte[] data) 
    {
        Mobile.log(Mobile.LOG_DEBUG, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getSound bytes: " + data.length);
        return new MediaSoundImpl(data);
    }
    
    public static MediaSound getSound(java.io.InputStream in) throws java.io.IOException
    {
        Mobile.log(Mobile.LOG_DEBUG, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getSound inputStream: " + in.available());
        return new MediaSoundImpl(in);
    }
    
    public static MediaSound getSound(String location) 
    {
        Mobile.log(Mobile.LOG_DEBUG, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getSound str: " + location);
        return new MediaSoundImpl(location);
    }
    
    public static MediaImage getStreamingImage(String location, String mimetype) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " getstreamingImage loc: " + location + " mime:" + mimetype);
        return null;
        //return new MediaImage(location, mimetype);
    }
    
    public static void use(MediaImage[] images, boolean useOnce) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " use images[] " + useOnce);
        //for (MediaImage image : images) {
        //    image.use(useOnce);
        //}
    }
    
    public static void use(MediaSound[] sounds, boolean useOnce) 
    {
        Mobile.log(Mobile.LOG_WARNING, MediaManager.class.getPackage().getName() + "." + MediaManager.class.getSimpleName() + ": " + " use sounds[] " + useOnce);
        //for (MediaSound sound : sounds) {
        //    sound.use(useOnce);
        //}
    }
}
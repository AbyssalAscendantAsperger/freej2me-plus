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
package com.nttdocomo.device;

public class SpeechRecognizer 
{
    
    public static SpeechRecognizer getInstance() 
    {
        return null;
    }

    public boolean isAvailable() 
    {
        return false;
    }

    public String getName() 
    {
        return null;
    }

    public int getMaxSpeechTime() 
    {
        return 0;
    }

    public String[] getAvailableCodec() 
    {
        return null;
    }

    public int[] getAvailableType() 
    {
        return null;
    }

    public int getReadyTime(String codec) 
    {
        return 0;
    }

    public void start(String codec, SpeechListener listener) 
    {

    }

    public void stop() 
    {

    }

    public void reset() 
    {
        
    }

    public SpeechFeatureData getFeature() 
    {
        return null;
    }

    public SpeechResultInformation getResultInformation(byte[] data, String charSet) 
    {
        return null;
    }
}
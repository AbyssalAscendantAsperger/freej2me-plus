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

public interface SpeechListener extends com.nttdocomo.util.EventListener 
{
    static final int ERROR_BUFFER_OVERFLOW = -2;
    static final int ERROR_SYSTEMERROR = -1;
    static final int EVENT_ERROR = 2;
    static final int EVENT_STOP = 1;
    static final int STOP_RACE_CONDITION = 2;
    static final int STOP_RESET = 3;
    static final int STOP_TIMEOUT = 1;
    static final int STOP_TRIGGER = 0;
    static final int STOP_UNAVAILABLE = 4;

    void notifyEvent(SpeechRecognizer recognizer, int event, int param);
    void notifyFeatureStored(SpeechRecognizer recognizer, SpeechAssistantInformation info);
}
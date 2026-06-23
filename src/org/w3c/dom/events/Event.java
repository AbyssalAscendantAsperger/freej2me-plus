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
package org.w3c.dom.events;

public interface Event 
{

    public static short AT_TARGET = 0;
    public static short BUBBLING_PHASE = 1;
    public static short CAPTURING_PHASE = 2;

    public String getType();

    public EventTarget getTarget();
    
    public EventTarget getCurrentTarget();
    
    public String getNamespaceURI();
    
    public short getEventPhase();
    
    public boolean getBubbles();
    
    public boolean getCancelable();
    
    public boolean getDefaultPrevented();
    
    public long getTimeStamp();
    
    public void preventDefault();
    
    public void stopPropagation();
    
    public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg);
    
    public void initEventNS(String namespaceURIArg, String eventTypeArg, boolean canBubbleArg, boolean cancelableArg);
}
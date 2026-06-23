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

import org.w3c.dom.views.AbstractView;

public interface KeyboardEvent extends UIEvent 
{
    public static int DOM_KEY_LOCATION_LEFT = 1;
    public static int DOM_KEY_LOCATION_RIGHT = 2;
    public static int DOM_KEY_LOCATION_NUMPAD = 3;
    public static int DOM_KEY_LOCATION_STANDARD = 0;

    public boolean getAltKey();
    public boolean getCtrlKey();
    public String getKeyIdentifier();
    public int getKeyLocation();
    public boolean getMetaKey();
    public boolean getShiftKey();
    
    public void initKeyboardEvent(String typeArg,
                           boolean canBubbleArg,
                           boolean cancelableArg,
                           AbstractView viewArg,
                           String keyIdentifierArg,
                           int keyLocationArg,
                           String modifiersList);
    
    public void initKeyboardEventNS(String namespaceURIArg,
                             String typeArg,
                             boolean canBubbleArg,
                             boolean cancelableArg,
                             AbstractView viewArg,
                             String keyIdentifierArg,
                             int keyLocationArg,
                             String modifiersList);
}
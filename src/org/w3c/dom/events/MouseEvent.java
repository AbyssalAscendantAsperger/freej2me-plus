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

public interface MouseEvent extends UIEvent 
{
    public short getButton();
    public int getClientX();
    public int getClientY();
    public int getScreenX();
    public int getScreenY();
    public boolean getAltKey();
    public boolean getCtrlKey();
    public boolean getShiftKey();
    public boolean getMetaKey();
    public EventTarget getRelatedTarget();

    public void initMouseEvent(String typeArg,
                        boolean canBubbleArg,
                        boolean cancelableArg,
                        AbstractView viewArg,
                        int detailArg,
                        int screenXArg,
                        int screenYArg,
                        int clientXArg,
                        int clientYArg,
                        boolean ctrlKeyArg,
                        boolean altKeyArg,
                        boolean shiftKeyArg,
                        boolean metaKeyArg,
                        short buttonArg,
                        EventTarget relatedTargetArg);
    
    public void initMouseEventNS(String namespaceURI,
                          String typeArg,
                          boolean canBubbleArg,
                          boolean cancelableArg,
                          AbstractView viewArg,
                          int detailArg,
                          int screenXArg,
                          int screenYArg,
                          int clientXArg,
                          int clientYArg,
                          short buttonArg,
                          EventTarget relatedTargetArg,
                          String modifiersList);
}
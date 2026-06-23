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
import org.w3c.dom.Node;

public interface MutationEvent extends Event 
{
    public static short ADDITION = 1;
    public static short MODIFICATION = 2;
    public static short REMOVAL = 3;

    public short getAttrChange();
    public String getAttrName();
    public String getNewValue();
    public String getPrevValue();
    public Node getRelatedNode();

    public void initMutationEvent(String typeArg,
                           boolean canBubbleArg,
                           boolean cancelableArg,
                           Node relatedNodeArg,
                           String prevValueArg,
                           String newValueArg,
                           String attrNameArg,
                           short attrChangeArg);
    
    public void initMutationEventNS(String namespaceURIArg,
                              String typeArg,
                              boolean canBubbleArg,
                              boolean cancelableArg,
                              Node relatedNodeArg,
                              String prevValueArg,
                              String newValueArg,
                              String attrNameArg,
                              short attrChangeArg);
}
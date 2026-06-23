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

public interface ProgressEvent extends Event 
{
    public boolean getLengthComputable();
    public int getLoaded();
    public int getTotal();

    public void initProgressEvent(String typeArg,
                           boolean canBubbleArg,
                           boolean cancelableArg,
                           boolean lengthComputableArg,
                           int loadedArg,
                           int totalArg);

    public void initProgressEventNS(String namespaceURIArg,
                              String typeArg,
                              boolean canBubbleArg,
                              boolean cancelableArg,
                              boolean lengthComputableArg,
                              int loadedArg,
                              int totalArg);
}
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

package com.siemens.mp.game;

import org.recompile.mobile.Mobile;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

public class GraphicObject extends com.siemens.mp.misc.NativeMem
{
	private boolean visible;


	protected GraphicObject() { visible = true; }

	public boolean getVisible() { return visible; }

	public void setVisible(boolean value) { visible = value; }

	protected void paint(Graphics g, int x, int y) { }

	protected boolean containsTransparentColor(Image image) 
	{
        for (int x = 0; x < image.getWidth(); x++) 
		{
            for (int y = 0; y < image.getHeight(); y++) 
			{
                if ((image.getARGB(x, y) & 0xFF000000) == 0) { return true; }
            }
        }
        return false;
    }
}
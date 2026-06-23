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
package com.nttdocomo.opt.ui;

import com.nttdocomo.ui.Image;
import com.nttdocomo.ui.impls.ImageImpl;
import org.recompile.mobile.Mobile;

public abstract class Canvas2 extends com.nttdocomo.ui.Canvas
{ 
	public static final int CANVAS_STYLE_VERTICAL = 0;
	public static final int CANVAS_STYLE_HORIZONTAL_RIGHT = 1; // coordinates rotated 90 degrees to the right
	public static final int CANVAS_STYLE_HORIZONTAL_LEFT = 2; // coordinates rotated 90 degrees to the left

	public Canvas2() 
	{
		this(CANVAS_STYLE_VERTICAL);
	}

	// This supposedly rotates the canvas coordinate system and draw area... maybe we can just rotate the screen here?
    public Canvas2(int canvasStyle) 
    { 
        super(); 

		if(canvasStyle < CANVAS_STYLE_VERTICAL || canvasStyle > CANVAS_STYLE_HORIZONTAL_LEFT) { throw new IllegalArgumentException("Invalid canvas Style:" + canvasStyle); }

		// TODO: rotate the screen
		
        Mobile.log(Mobile.LOG_INFO, Canvas2.class.getPackage().getName() + "." + Canvas2.class.getSimpleName() + ": " + "Create I-Appli Canvas2:" + width+", "+height);
    }
}
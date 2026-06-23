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

public class Sprite extends GraphicObject
{
	private int collh;
	private int collw;
	private int collx;
	private int colly;
	private int frame;

	private Image[] mask;
	private Image[] pixels;

	private int x;
	private int y;

	
	public Sprite(byte[] pixels, int pixel_offset, int width, int height, byte[] mask, int mask_offset, int numFrames)
	{
		this(com.siemens.mp.ui.Image.createImageFromBitmap(pixels, width, height * numFrames),
			mask == null ? null : com.siemens.mp.ui.Image.createImageFromBitmap(mask, width, height * numFrames),
			numFrames);
	}

	public Sprite(ExtendedImage pixels, ExtendedImage mask, int numFrames)
	{
		this(pixels.getImage(), mask == null ? null : mask.getImage(), numFrames);
	}

	public Sprite(Image pixels, Image mask, int numFrames)
	{
		if (numFrames < 1) 
		{
            throw new IllegalArgumentException("Number of frames must be at least 1.");
        }
		if (containsTransparentColor(pixels) || (mask != null && containsTransparentColor(mask)))
		{
            throw new IllegalArgumentException("Images must not contain transparent colors.");
		}
		if (pixels.getWidth() % 8 != 0 || (mask != null && mask.getWidth() % 8 != 0))
		{
            throw new IllegalArgumentException("Image width must be a multiple of 8.");
        }
        if (mask != null && (pixels.getWidth() != mask.getWidth() || pixels.getHeight() != mask.getHeight()))
		{
            throw new IllegalArgumentException("Images must have the same size.");
        }
        if (pixels.getHeight() % numFrames != 0) 
		{
            throw new IllegalArgumentException("Image height must be divisible by the number of frames.");
        }        

		this.pixels = new Image[numFrames];

		if (mask != null) 
		{
			pixels = com.siemens.mp.lcdui.Image.createTransparentImageFromMask(pixels, mask);
		}

		for (int i = 0; i < numFrames; i++) 
		{
			Image img = Image.createImage(pixels.getWidth(), pixels.getHeight() / numFrames, 0);

			img.getGraphics().drawImage(pixels, 0, -i * pixels.getHeight() / numFrames, 0);
			this.pixels[i] = img;
		}
		
		collx = 0;
		colly = 0;
		collw = this.pixels[0].getWidth();
		collh = this.pixels[0].getHeight();
	}

	public int getFrame() { return frame; }

	public int getXPosition() { return x; }

	public int getYPosition() { return y; }
	
	public boolean isCollidingWith(Sprite other) 
	{
		return !(other.x + other.collx + other.collw <= x + collx ||
				other.x + other.collx >= x + collx + collw ||
				other.y + other.colly + other.collh <= y + colly ||
				other.y + other.colly >= y + colly + collh);
	}

	public boolean isCollidingWithPos(int xpos, int ypos) 
	{
		return (xpos >= x + collx && xpos < x + collx + collw && ypos >= y + colly && ypos < y + colly + collh);
	}

	public void setCollisionRectangle(int x, int y, int width, int height) 
	{
		collx = x;
		colly = y;
		collw = width;
		collh = height;
	}
	
	public void setFrame(int framenumber) { frame = framenumber; }
	
	public void setPosition(int X, int Y) 
	{ 
		collx += X - x;
		colly += Y - y;
		x = X; 
		y = Y; 
	}

	protected void paint(Graphics g, int x, int y) 
	{
		x += this.x;
		y += this.y;
		g.drawImage(pixels[frame], x, y, 0);
	}
}

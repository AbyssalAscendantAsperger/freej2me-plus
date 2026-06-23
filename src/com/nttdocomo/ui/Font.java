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
package com.nttdocomo.ui;

import com.nttdocomo.lang.XString;

import org.recompile.mobile.Mobile;
import org.recompile.mobile.PlatformFont;

public class Font extends PlatformFont
{

	public static final int FACE_MONOSPACE = 0x72000000;
	public static final int FACE_PROPORTIONAL = 0x73000000;
	public static final int FACE_SYSTEM = 0x71000000;

	public static final int SIZE_LARGE = 0x70000300;
	public static final int SIZE_MEDIUM = 0x70000200;
	public static final int SIZE_SMALL = 0x70000100;
	public static final int SIZE_TINY = 0x70000400;

	public static final int STYLE_BOLD = 0x70110000;
	public static final int STYLE_BOLDITALIC = 0x70130000;
	public static final int STYLE_ITALIC = 0x70120000;
	public static final int STYLE_PLAIN = 0x70100000;

	public static final int TYPE_DEFAULT = 0x00000000;
	public static final int TYPE_HEADING = 0x00000001;

	private static final int[] supportedSizes = { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

	public Font(int face, int style, int size)
	{
		super(face, style, size, false);
	}

	public int getBBoxHeight(String str) { return getHeight(); }

	public int getBBoxWidth(String str) { return stringWidth(str); }

	public int getBBoxHeight(XString xStr) { return getHeight(); }

	public int getBBoxWidth(XString xStr) { return getBBoxWidth(xStr.getString()); }

	public int getBBoxWidth(XString xStr, int off, int len) { return substringWidth(xStr.toString(), off, len); }

	public static Font getFont(int type) { return getDefaultFont(); }

	public static Font getFont(int type, int fontSize) { return getDefaultFont(); }

	public static Font getDefaultFont() { return defaultDoJaFont; }

	public static void setDefaultFont(Font font) 
	{
		if(font != null) { defaultDoJaFont = font; }
	}

	public int getLineBreak(XString xStr, int off, int len, int width) { return getLineBreak(xStr.getString(), off, len, width); }

	public int getLineBreak(String str, int off, int len, int width) 
	{
		int currentWidth = 0;
		for (int i = off; i < off + len; i++) 
		{
			currentWidth += stringWidth(str.substring(i, i + 1)); 
			if (currentWidth > width) { return i; }
		}
		return off + len;
	}

	public int[] getSupportedFontSizes() { return supportedSizes; }

	public int stringWidth(XString xStr) { return stringWidth(xStr.getString()); }
	
	public int stringWidth(XString xStr, int off, int len) { return substringWidth(xStr.getString(), off, len); }
}
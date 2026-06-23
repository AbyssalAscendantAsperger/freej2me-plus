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

public class PhoneSystem2 extends com.nttdocomo.ui.PhoneSystem 
{
    // Attributes for display brightness
    public static final int ATTR_BRIGHTNESS_MAX = 255;
    public static final int ATTR_BRIGHTNESS_MIN = 0;
    
    // Attributes for display contrast
    public static final int ATTR_CONTRAST_MAX = 255;
    public static final int ATTR_CONTRAST_MIN = 0;
    
    // Display style attributes
    public static final int ATTR_DISPLAY_STYLE_VERTICAL = 0;
    public static final int ATTR_DISPLAY_STYLE_HORIZONTAL_RIGHT = 1;
    public static final int ATTR_DISPLAY_STYLE_HORIZONTAL_LEFT = 2;
    public static final int ATTR_DISPLAY_STYLE_REVERSE = 3;
    
    // Illumination attributes
    public static final int ATTR_ILLUMINATION_OFF = 0;
    public static final int ATTR_ILLUMINATION_WHITE = 1;
    public static final int ATTR_ILLUMINATION_ORANGE = 2;
    public static final int ATTR_ILLUMINATION_YELLOW = 3;
    public static final int ATTR_ILLUMINATION_GREEN = 4;
    public static final int ATTR_ILLUMINATION_SKYBLUE = 5;
    public static final int ATTR_ILLUMINATION_BLUE = 6;
    public static final int ATTR_ILLUMINATION_VIOLET = 7;
    public static final int ATTR_ILLUMINATION_RAINBOW = 8;
    
    // Key slant attributes
    public static final int ATTR_KEY_SLANT_OFF = 0;
    public static final int ATTR_KEY_SLANT_ON = 1;
    
    // LED attributes
    public static final int ATTR_MEMO_LED_OFF = 0;
    public static final int ATTR_MEMO_LED_ON = -1;
    
    // Volume attributes
    public static final int ATTR_VOLUME_MAX = 100;
    public static final int ATTR_VOLUME_MIN = 0;
    
    // Device specific constants
    public static final int DEV_ALLOCATABLE_JAVA_MEMORY = 140;
    public static final int DEV_DISPLAY_BRIGHTNESS = 136;
    public static final int DEV_DISPLAY_CONTRAST = 138;
    public static final int DEV_DISPLAY_STYLE = 142;
    public static final int DEV_ILLUMINATION = 133;
    public static final int DEV_MEMO_LED = 134;
    public static final int DEV_KEY_SLANT = 135;
    
}
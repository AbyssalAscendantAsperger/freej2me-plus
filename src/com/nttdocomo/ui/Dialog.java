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

import java.util.List;

import org.recompile.mobile.Mobile;

public final class Dialog extends Frame 
{
    public static final int BUTTON_OK = 0x0001;
    public static final int BUTTON_CANCEL = 0x0002;
    public static final int BUTTON_YES = 0x0004;
    public static final int BUTTON_NO = 0x0008;
    
    public static final int DIALOG_INFO = 0;
    public static final int DIALOG_WARNING = 1;
    public static final int DIALOG_ERROR = 2;
    public static final int DIALOG_YESNO = 3;
    public static final int DIALOG_YESNOCANCEL = 4;

    private String title;
    private String message;
    private int dialogType;
    private Font font = Font.getDefaultFont();

    private List<String> lines;
	private int lineSpacing;
	private int margin;
	private int scrollbarWidth;
	private int scrollY = 0;
	private int scrollHeight = 0;
	private int clientHeight;
	private boolean needsLayout = true;

    public Dialog(int type, String title) 
    {
        super();
        if (type < DIALOG_INFO || type > DIALOG_YESNOCANCEL) { throw new IllegalArgumentException("Illegal dialog type"); }
        
        this.dialogType = type;
        this.title = (title != null) ? title : " ";
    }

    public void setBackground(int color) { super.setBackground(color); }

    public void setFont(Font font) { this.font = font; }

    public void setSoftLabel(int key, String label) { }

    public void setText(String msg) 
    {
        this.message = (msg != null) ? msg : " ";
    }

    public int show() throws UIException 
    { 
        Mobile.log(Mobile.LOG_WARNING, Dialog.class.getPackage().getName() + "." + Dialog.class.getSimpleName() + ": " + title + " " + message + " Dialog type " + dialogType);
        
        if(dialogType == DIALOG_ERROR) 
        {
            setSoftLabel(SOFT_KEY_1, "OK");
            setSoftLabel(SOFT_KEY_2, "");
        }

        renderScreen(0, 0, Display.getWidth(), Display.getHeight());
        try { Thread.sleep(2500);}
        catch(Exception e) {}
        return BUTTON_OK; 
    }

    public String renderScreen(int x, int y, int width, int height) 
    {
		clientHeight = height;

		if (message == null) {
			return null;
		}
		if (needsLayout) 
        {
			lines = javax.microedition.lcdui.StringItem.wrapText(message, width - 2*margin - scrollbarWidth, javax.microedition.lcdui.Font.getDefaultFont());
			needsLayout = false;
			if (lines.isEmpty()) {
				return "";
			}

			scrollHeight = (lines.size()*Font.getDefaultFont().getHeight() + (lines.size()-1)*lineSpacing) + 2*margin;
			scrollY = 0;
		}

		if (lines.isEmpty()) {
			return "";
		}

		graphics.setColor(Mobile.lcduiTextColor);

		for(int l=0;l<lines.size();l++) {
			int ystart = margin + l*Font.getDefaultFont().getHeight() + (l > 0 ? (l-1)*lineSpacing : 0);
			int yend = ystart + Font.getDefaultFont().getHeight();

			if (yend < scrollY || ystart >= scrollY+height) {
				continue;
			}

			graphics.drawString(
				lines.get(l),
				x + margin,
				y + ystart - scrollY,
				Graphics.LEFT);
		}
		
		double fact = (double)height/scrollHeight;
		int yscrollStart = (int)Math.round(scrollY * fact);
		int yscrollHeight = (int)Math.min(height, Math.round(height * fact));
	
		if (height < scrollHeight)
		{
			graphics.setColor(Mobile.lcduiBGColor);
			graphics.fillRect(x + width - scrollbarWidth, y+yscrollStart, scrollbarWidth, yscrollHeight);
		}
		
		return null;
	}

    
}
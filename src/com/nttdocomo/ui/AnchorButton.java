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

public final class AnchorButton extends Component implements Interactable 
{
    public AnchorButton() { super(); }

    public AnchorButton(Image image) 
    {
        super();
        setImage(image);
    }

    public AnchorButton(Image image, String text) 
    {
        super();
        setImage(image);
        setText(text);
    }

    public AnchorButton(Image image, com.nttdocomo.lang.XString xText) 
    {
        super();
        setImage(image);
        setText(xText);
    }

    public AnchorButton(String text) 
    {
        super();
        setText(text);
    }

    public AnchorButton(com.nttdocomo.lang.XString xText) 
    {
        super();
        setText(xText);
    }

    public void requestFocus() 
    {

    }

    public void setEnabled(boolean b) 
    {

    }

    public void setImage(Image image) 
    {

    }

    public void setText(String text) 
    {

    }

    public void setText(com.nttdocomo.lang.XString xText) 
    {

    }
}
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
package com.nttdocomo.system;

import com.nttdocomo.lang.XString;

public final class MailAgent
{

    public static Mail getLastIncoming() throws SecurityException, IllegalStateException 
    {
        return null;
    }

    public static int getRemainingBytes(DecomailDraft mail) 
    {
        return 0;
    }

    public static int getRemainingBytes(MailDraft mail) 
    {
        return 0;
    }

    public static boolean send(DecomailDraft mail) 
    {
        return false;
    }

    public static boolean send(MailDraft mail) 
    {
        return false;
    }

    public static boolean send(String subject, String[] addresses, String body) 
    {
        return false;
    }

    public static boolean send(String subject, XString address, String body) 
    {
        return false;
    }
}
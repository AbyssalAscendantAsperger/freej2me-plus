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

public final class MessageAgent implements MailConstants 
{

    public static void delete(int type, int id) 
    {

    }

    public static int[] getIds(int type, boolean unseen) 
    {
        return new int[0];
    }

    public static Message getMessage(int type, int id) 
    {
        return null;
    }

    public static int getRemainingBytes(MessageDraft message) 
    {
        return 0;
    }

    public static boolean isSeen(int id) 
    {
        return false;
    }

    public static boolean send(MessageDraft message) 
    {
        return false;
    }

    public static boolean send(MessageSent message) 
    {
        return false;
    }

    public static boolean send(String subject, String[] addresses, String body, byte[] data) 
    {
        return false;
    }

    public static boolean send(String subject, XString address, String body, byte[] data) 
    {
        return false;
    }

    public static void setMessageFolderListener(MessageFolderListener listener) 
    {

    }

    public static void setSeen(int id, boolean seen) 
    {

    }

    public static int size(int type, boolean unseen) 
    {
        return 0;
    }
}
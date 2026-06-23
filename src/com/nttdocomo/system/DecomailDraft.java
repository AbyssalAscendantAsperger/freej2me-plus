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

public final class DecomailDraft extends MailDraft 
{

    public DecomailDraft() { }

    public DecomailDraft(String subject, String[] addresses, String body) 
    {
        setSubject(subject);
        setRecipients(addresses);
        setBody(body);
    }

    public DecomailDraft(String subject, XString address, String body) 
    {
        setSubject(subject);
        setRecipient(address);
        setBody(body);
    }

    public void setBody(String body) 
    {
        super.setBody(body);
    }
}
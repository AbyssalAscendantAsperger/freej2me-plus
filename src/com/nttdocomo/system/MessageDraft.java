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

public final class MessageDraft implements MailConstants 
{

    private String subject;
    private String[] recipients;
    private String body;
    private byte[] data;
    private XString xRecipient;
    private XString[] xRecipients;

    public MessageDraft() { }

    public MessageDraft(String subject, String[] addresses, String body, byte[] data) 
    {
        setSubject(subject);
        setRecipients(addresses);
        setBody(body);
        setData(data);
    }

    public MessageDraft(String subject, XString address, String body, byte[] data) 
    {
        setSubject(subject);
        setRecipient(address);
        setBody(body);
        setData(data);
    }

    public MessageDraft(Message message, boolean all) 
    {
    
    }

    public void addRecipient(String address) 
    {

    }

    public String getBody() { return body; }

    public byte[] getData() { return data; }

    public String[] getRecipients() 
    {
        return recipients != null ? recipients.clone() : null;
    }

    public String getSubject() { return subject; }

    public XString getXRecipient() { return xRecipient; }

    public XString[] getXRecipients() 
    {
        return xRecipients != null ? xRecipients.clone() : null;
    }

    public void removeXRecipient(XString address) 
    {

    }

    public void setBody(String body) { this.body = body; }

    public void setData(byte[] data) { this.data = data; }

    public void setRecipient(XString address) 
    {
        this.xRecipient = address;
        this.recipients = null;
    }

    public void setRecipients(String[] addresses) 
    {
        this.recipients = addresses != null ? addresses.clone() : null;
        this.xRecipient = null;
    }

    public void setSubject(String subject) { this.subject = subject; }
}
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

public final class PhoneBookParam implements PhoneBookConstants 
{

    private String name;
    private String kana;
    private String[] phoneNumbers;
    private String[] mailAddresses;
    private int groupId;
    private String groupName;

    public PhoneBookParam() { this(null, null, null, null, -1); }

    public PhoneBookParam(String name, String kana, String[] phoneNumbers, String[] mailAddresses, String groupName) 
    {
        setName(name);
        setKana(kana);
        setPhoneNumbers(phoneNumbers);
        setMailAddresses(mailAddresses);
        setGroupName(groupName);
    }

    public PhoneBookParam(String name, String kana, String[] phoneNumbers, String[] mailAddresses, int groupId) 
    {
        setName(name);
        setKana(kana);
        setPhoneNumbers(phoneNumbers);
        setMailAddresses(mailAddresses);
        setGroupId(groupId);
    }

    public void addMailAddress(String mailAddress) 
    {

    }

    public void addPhoneNumber(String phoneNumber) 
    {

    }

    public int getGroupId() { return groupId; }

    public String getGroupName() { return groupName; }

    public String getKana() { return kana; }

    public String getKana(int part) 
    {
        return null; 
    }

    public void setGroupId(int id) { this.groupId = id; }

    public void setGroupName(String name) { this.groupName = name; }

    public void setKana(int part, String name) 
    {

    }

    public void setKana(String kana) { this.kana = kana; }

    public void setMailAddresses(String[] mailAddresses) 
    {
        this.mailAddresses = mailAddresses;
    }

    public void setName(int part, String name) 
    {

    }

    public void setName(String name) { this.name = name; }

    public void setPhoneNumbers(String[] phoneNumbers) 
    {
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() { return name; }

    public String getName(int part) 
    {
        return null;
    }

    public String getPhoneNumber(int index) 
    {
        return null;
    }

    public String[] getPhoneNumbers() 
    {
        return phoneNumbers != null ? phoneNumbers.clone() : null;
    }

    public String getMailAddress(int index) 
    {
        return null;
    }

    public String[] getMailAddresses() 
    {
        return mailAddresses != null ? mailAddresses.clone() : null;
    }
}
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

public final class Toruca
{

    public static final String TYPE_CARD = "card";
    public static final String TYPE_SNIP = "snip";

    private byte[] body;
    private byte[] category;
    private String url;
    private String data1;
    private String data2;
    private String data3;
    private int colorID;
    private String ipID;
    private int redistributionID;
    private String sortID;
    private String type;
    private byte[] version;
    private java.util.Date expirationDate;

    public Toruca() 
    {
        this.version = new byte[]{0x01, 0x00};
        this.type = TYPE_SNIP;
        this.url = null;
        this.data1 = null;
        this.data2 = null;
        this.data3 = null;
        this.category = null;
        this.colorID = -1;
        this.redistributionID = 4;
        this.expirationDate = null;
        this.ipID = null;
        this.sortID = null;
        this.body = null;
    }

    public Toruca(byte[] data) 
    {
    }

    public byte[] getBody() { return body; }

    public void setBody(byte[] body) { this.body = body; }

    public byte[] getCategory() { return category; }

    public void setCategory(byte[] category) { this.category = category; }

    public String getData1() { return data1; }

    public void setData1(String data1) { this.data1 = data1; }

    public String getData2() { return data2; }

    public void setData2(String data2) { this.data2 = data2; }

    public String getData3() { return data3; }

    public void setData3(String data3) { this.data3 = data3; }

    public java.util.Date getExpirationDate() { return expirationDate; }

    public String getIPID() { return ipID; }

    public int getRedistributionID() { return redistributionID; }

    public String getSortID() { return sortID; }

    public String getType() { return type; }

    public String getURL() { return url; }

    public byte[] getVersion() { return version; }

    public void setURL(String url) { this.url = url; }

    public void setVersion(byte[] version) { this.version = version; }

    public void setType(String type) { this.type = type; }

    public void setProperty(String key, String value) 
    {

    }
}
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
package com.nttdocomo.util;

import com.nttdocomo.lang.IllegalStateException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarInflater 
{
    private Hashtable<String, JarEntry> entries;
    private Hashtable<String, byte[]> data;

    public JarInflater(InputStream inputStream) throws JarFormatException, IOException 
    {
        if (inputStream == null) { throw new NullPointerException("InputStream is null"); }

        inflate(new JarInputStream(inputStream));
    }

    public JarInflater(byte[] dataArray) throws JarFormatException, IOException
    {
        if (dataArray == null) { throw new NullPointerException("Data array is null"); }

        inflate(new JarInputStream(new ByteArrayInputStream(dataArray)));
    }

    private void inflate(JarInputStream jarInputStream) throws IOException 
    {
        entries = new Hashtable<String, JarEntry>();
        data = new Hashtable<String, byte[]>();
        JarEntry jarEntry = jarInputStream.getNextJarEntry();
        byte[] buffer = new byte[1024];

        while (jarEntry != null) 
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = jarInputStream.read(buffer)) != -1) { byteArrayOutputStream.write(buffer, 0, bytesRead); }

            entries.put(jarEntry.getName(), jarEntry);
            data.put(jarEntry.getName(), byteArrayOutputStream.toByteArray());
            jarEntry = jarInputStream.getNextJarEntry();
        }
    }

    public void close() 
    {
        entries = null;
        data = null;
    }

    public long getSize(String name) throws JarFormatException, IllegalStateException
    {
        if (entries == null) { throw new IllegalStateException("JarInflater is closed"); }
        if (name == null) { throw new NullPointerException("name is null"); }

        JarEntry jarEntry = entries.get(name);
        if (jarEntry == null) { return -1; }
        return jarEntry.getSize();
    }

    public InputStream getInputStream(String name) throws JarFormatException, IllegalStateException
    {
        if (entries == null) { throw new IllegalStateException("JarInflater is closed"); }
        if (name == null) { throw new NullPointerException("name is null"); }

        byte[] entryData = data.get(name);
        return (entryData != null) ? new ByteArrayInputStream(entryData) : null;
    }
}
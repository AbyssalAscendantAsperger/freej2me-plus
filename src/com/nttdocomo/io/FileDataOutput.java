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
package com.nttdocomo.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.DataOutput;

public class FileDataOutput implements DataOutput, RandomAccessible 
{
    private final RandomAccessFile file;

    public FileDataOutput(String filePath) throws IOException 
    {
        this.file = new RandomAccessFile(filePath, "rw");
    }

    public void close() throws IOException { file.close(); }

    public void flush() throws IOException { }

    public long getPosition() throws IOException { return file.getFilePointer(); }

    public long getSize() throws IOException { return file.length(); }

    public void setPosition(long position) throws IOException 
    {
        if (position < 0) { throw new IllegalArgumentException("Position cannot be negative"); }

        file.seek(position);
    }

    public void setPositionRelative(long position) throws IOException 
    {
        long newPos = getPosition() + position;
        setPosition(newPos);
    }

    public void truncate(long fileSize) throws IOException { file.setLength(fileSize); }

    public void write(byte[] b) throws IOException { file.write(b); }

    public void write(byte[] b, int off, int len) throws IOException { file.write(b, off, len); }

    public void write(int b) throws IOException { file.write(b); }

    public void writeBoolean(boolean v) throws IOException { file.writeBoolean(v); }

    public void writeByte(int v) throws IOException { file.writeByte(v); }

    public void writeBytes(String s) throws IOException { file.writeBytes(s); }

    public void writeChar(int v) throws IOException { file.writeChar(v); }

    public void writeChars(String s) throws IOException { file.writeChars(s); }

    public void writeDouble(double v) throws IOException { file.writeDouble(v); }

    public void writeFloat(float v) throws IOException { file.writeFloat(v); }

    public void writeInt(int v) throws IOException { file.writeInt(v); }

    public void writeLong(long v) throws IOException { file.writeLong(v); }

    public void writeShort(int v) throws IOException { file.writeShort(v); }

    public void writeString(String s) throws IOException 
    {
        byte[] bytes = s.getBytes("Shift_JIS");
        writeShort(bytes.length);
        write(bytes);
    }

    public void writeString(String s, int bytes) throws IOException 
    {
        byte[] bytesArray = s.getBytes("Shift_JIS");
        if (bytesArray.length > bytes) { throw new IllegalArgumentException("String too long for specified byte length"); }
        writeShort(bytes); 
        write(bytesArray);

        // Assuming we have space remaining, zero-fill it
        for (int i = bytesArray.length; i < bytes; i++) { write(0); }
    }

    public void writeUTF(String str) throws IOException 
    {
        byte[] bytes = str.getBytes("Shift_JIS");
        writeShort(bytes.length);
        write(bytes);
    }
}
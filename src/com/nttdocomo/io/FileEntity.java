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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class FileEntity 
{
    private final RandomAccessFile file;
    private int bufferSize;
    private boolean isClosed = false;
    private String filePath;

    public FileEntity(String filePath) throws IOException 
    {
        this.filePath = filePath;
        this.file = new RandomAccessFile(filePath, "rw");
        this.bufferSize = 1024;
    }

    public void close() throws IOException 
    {
        if (!isClosed) 
        {
            file.close();
            isClosed = true;
        }
    }

    public int getBufferSize() throws IOException 
    {
        if (isClosed) { throw new RuntimeException("This FileEntity is already closed"); }
        return bufferSize; 
    }

    public void setBufferSize(int bufferSize) throws IOException 
    {
        if (isClosed) { throw new RuntimeException("This FileEntity is already closed"); }
        if (bufferSize < 0) { throw new IllegalArgumentException("Buffer size cannot be negative"); }

        this.bufferSize = bufferSize;
    }

    public FileDataInput openDataInput() throws IOException 
    {
        if (isClosed) { throw new RuntimeException("This FileEntity is already closed"); }
        return new FileDataInput(file.getFD().toString());
    }

    public FileDataOutput openDataOutput() throws IOException 
    {
        if (isClosed) { throw new RuntimeException("This FileEntity is already closed"); }
        return new FileDataOutput(file.getFD().toString());
    }

    public InputStream openInputStream() throws IOException 
    {
        if (isClosed) { throw new RuntimeException("This FileEntity is already closed"); }
        return new InputStream() 
        {
            @Override
            public int read() throws IOException { return file.read(); }
        };
    }

    public OutputStream openOutputStream() throws IOException 
    {
        if (isClosed) { throw new RuntimeException("This FileEntity is already closed"); }
        return new OutputStream() 
        {
            @Override
            public void write(int b) throws IOException { file.write(b); }
        };
    }

    public void deleteFile() throws IOException
    { 
        file.close();
        File fileDat = new File(filePath); 
        fileDat.delete();
    }
}
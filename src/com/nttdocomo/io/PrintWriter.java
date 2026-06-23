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

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class PrintWriter extends java.io.Writer 
{
    protected Writer out;
    private boolean autoFlush;
    private boolean errorFlag;

    public PrintWriter(OutputStream out) throws UnsupportedEncodingException { this(out, false); }

    public PrintWriter(OutputStream out, boolean autoFlush) throws UnsupportedEncodingException
    {
        this(new java.io.OutputStreamWriter(out, "Shift_JIS"), autoFlush);
    }

    public PrintWriter(Writer out) throws UnsupportedEncodingException { this(out, false); } 

    public PrintWriter(Writer out, boolean autoFlush) throws UnsupportedEncodingException
    {
        if (out == null) { throw new NullPointerException("Output stream or writer cannot be null"); }

        this.out = out;
        this.autoFlush = autoFlush;
        this.errorFlag = false;
    }

    @Override
    public void flush() 
    {
        try { out.flush(); } 
        catch (IOException e) { setError(); }
    }

    @Override
    public void close() 
    {
        try { out.close(); } 
        catch (IOException e) { setError(); }
    }

    @Override
    public void write(int c) 
    {
        try 
        {
            out.write(c);
            if (autoFlush) { flush(); }
        } 
        catch (IOException e) { setError(); }
    }

    @Override
    public void write(char[] buf) 
    {
        try 
        {
            out.write(buf);
            if (autoFlush) { flush(); }
        } 
        catch (IOException e) { setError(); }
    }

    @Override
    public void write(char[] buf, int off, int len) 
    {
        try 
        {
            out.write(buf, off, len);
            if (autoFlush) { flush(); }
        } 
        catch (IOException e) { setError(); }
    }

    @Override
    public void write(String s) 
    {
        if (s == null) { write("null"); } 
        else 
        {
            try 
            {
                out.write(s);
                if (autoFlush) { flush(); }
            } 
            catch (IOException e) { setError(); }
        }
    }

    @Override
    public void write(String s, int off, int len) 
    {
        if (s == null) { write("null", off, len); } 
        else 
        {
            try 
            {
                out.write(s, off, len);
                if (autoFlush) { flush(); }
            } 
            catch (IOException e) { setError(); }
        }
    }

    public void print(boolean b) { write(String.valueOf(b)); }

    public void print(char c) { write(c); }

    public void print(int i) { write(String.valueOf(i)); }

    public void print(long l) { write(String.valueOf(l)); }

    public void print(Object obj) { write(String.valueOf(obj)); }

    public void print(String s) { write(s); }

    public void println() 
    {
        write("\r\n"); // CRLF for new line
        if (autoFlush) { flush(); }
    }

    public void println(boolean b) 
    {
        print(b);
        println();
    }

    public void println(char c) 
    {
        print(c);
        println();
    }

    public void println(int i) 
    {
        print(i);
        println();
    }

    public void println(long l) 
    {
        print(l);
        println();
    }

    public void println(Object obj) 
    {
        print(obj);
        println();
    }

    public void println(String s) 
    {
        print(s);
        println();
    }

    public boolean checkError() { return errorFlag; }

    protected void setError() { errorFlag = true; }
}
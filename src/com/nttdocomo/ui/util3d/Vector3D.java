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
package com.nttdocomo.ui.util3d;

public class Vector3D 
{
    private float x;
    private float y;
    private float z;

    public Vector3D() 
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Vector3D(float x, float y, float z) { set(x, y, z); }

    public Vector3D(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("null vector received"); }

        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void set(float x, float y, float z) 
    {
        if (Float.isNaN(x) || Float.isNaN(y) || Float.isNaN(z) ||
            Float.isInfinite(x) || Float.isInfinite(y) || Float.isInfinite(z)) 
        {
            throw new IllegalArgumentException("invalid value received");
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("null vector received"); }

        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void setX(float x) 
    {
        if (Float.isNaN(x) || Float.isInfinite(x)) { throw new IllegalArgumentException("Invalid x value"); }

        this.x = x;
    }

    public void setY(float y) 
    {
        if (Float.isNaN(y) || Float.isInfinite(y)) { throw new IllegalArgumentException("Invalid y value"); }

        this.y = y;
    }

    public void setZ(float z) 
    {
        if (Float.isNaN(z) || Float.isInfinite(z)) { throw new IllegalArgumentException("Invalid z value"); }

        this.z = z;
    }

    public float getX() { return x; }

    public float getY() { return y; }

    public float getZ() { return z; }

    public void add(float x, float y, float z) { set(this.x + x, this.y + y, this.z + z); }

    public void add(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("null vector received"); }

        set(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public void cross(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("null vector received"); }

        float crossX = this.y * v.z - this.z * v.y;
        float crossY = this.z * v.x - this.x * v.z;
        float crossZ = this.x * v.y - this.y * v.x;
        set(crossX, crossY, crossZ);
    }

    public void cross(Vector3D u, Vector3D v) 
    {
        if (u == null || v == null) { throw new NullPointerException("null vector received"); }

        float crossX = u.y * v.z - u.z * v.y;
        float crossY = u.z * v.x - u.x * v.z;
        float crossZ = u.x * v.y - u.y * v.x;
        set(crossX, crossY, crossZ);
    }

    public float dot(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("null vector received"); }

        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public static float dot(Vector3D v1, Vector3D v2) 
    {
        if (v1 == null || v2 == null) { throw new NullPointerException("null vector received"); }

        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public void normalize() 
    {
        float length = (float) Math.sqrt(x * x + y * y + z * z);
        if (length == 0) { throw new ArithmeticException("Zero vector cannot be normalized."); }
        set(x / length, y / length, z / length);
    }
}
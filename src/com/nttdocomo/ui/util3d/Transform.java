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

public class Transform 
{
    private float[] matrix;

    public Transform() 
    {
        matrix = new float[16];
        setIdentity();
    }

    public Transform(Transform transform) 
    {
        if (transform == null) { throw new NullPointerException("Null transform received"); }

        matrix = new float[16];
        System.arraycopy(transform.matrix, 0, this.matrix, 0, 16);
    }

    public void set(float[] matrix) 
    {
        if (matrix == null) { throw new NullPointerException("Null matrix received"); }
        if (matrix.length < 16) { throw new IllegalArgumentException("Matrix must have at least 16 elements."); }

        for (int i = 0; i < 16; i++) 
        {
            if (Float.isNaN(matrix[i]) || Float.isInfinite(matrix[i])) { throw new IllegalArgumentException("Matrix elements must be finite."); }
            this.matrix[i] = matrix[i];
        }
    }

    public void get(float[] matrix) 
    {
        if (matrix == null) { throw new NullPointerException("Null matrix received"); }

        if (matrix.length < 16) { throw new IllegalArgumentException("Matrix must have at least 16 elements."); }

        System.arraycopy(this.matrix, 0, matrix, 0, 16);
    }

    public float get(int index) 
    {
        if (index < 0 || index >= 16) { throw new IllegalArgumentException("Index out of bounds."); }
        return matrix[index];
    }

    public void set(int index, float value) 
    {
        if (index < 0 || index >= 16) { throw new IllegalArgumentException("Index out of bounds."); }
        if (Float.isNaN(value) || Float.isInfinite(value)) { throw new IllegalArgumentException("Value must be finite."); }

        matrix[index] = value;
    }

    public void setIdentity() 
    {
        matrix[0] = 1; matrix[1] = 0; matrix[2] = 0; matrix[3] = 0;
        matrix[4] = 0; matrix[5] = 1; matrix[6] = 0; matrix[7] = 0;
        matrix[8] = 0; matrix[9] = 0; matrix[10] = 1; matrix[11] = 0;
        matrix[12] = 0; matrix[13] = 0; matrix[14] = 0; matrix[15] = 1;
    }

    // Copied directly from M3G's Transform
    public void invert()
	{
		/* The inverse matrix is calculated by using an adapted version of the Laplace Expansion Theorem. */

		/*
		 * Since the matrix is a linear array, the logic is akin to C's pointer arithmethic 
		 * on matrices, where accesses to mat[row][col] becomes mat[4*row + col].
		 */
		float s0 = this.matrix[4*0 + 0] * this.matrix[4*1 + 1] - this.matrix[4*1 + 0] * this.matrix[4*0 + 1];
    	float s1 = this.matrix[4*0 + 0] * this.matrix[4*1 + 2] - this.matrix[4*1 + 0] * this.matrix[4*0 + 2];
    	float s2 = this.matrix[4*0 + 0] * this.matrix[4*1 + 3] - this.matrix[4*1 + 0] * this.matrix[4*0 + 3];
		float s3 = this.matrix[4*0 + 1] * this.matrix[4*1 + 2] - this.matrix[4*1 + 1] * this.matrix[4*0 + 2];
		float s4 = this.matrix[4*0 + 1] * this.matrix[4*1 + 3] - this.matrix[4*1 + 1] * this.matrix[4*0 + 3];
		float s5 = this.matrix[4*0 + 2] * this.matrix[4*1 + 3] - this.matrix[4*1 + 2] * this.matrix[4*0 + 3];

		float c0 = this.matrix[4*2 + 0] * this.matrix[4*3 + 1] - this.matrix[4*3 + 0] * this.matrix[4*2 + 1];
		float c1 = this.matrix[4*2 + 0] * this.matrix[4*3 + 2] - this.matrix[4*3 + 0] * this.matrix[4*2 + 2];
		float c2 = this.matrix[4*2 + 0] * this.matrix[4*3 + 3] - this.matrix[4*3 + 0] * this.matrix[4*2 + 3];
		float c3 = this.matrix[4*2 + 1] * this.matrix[4*3 + 2] - this.matrix[4*3 + 1] * this.matrix[4*2 + 2];
		float c4 = this.matrix[4*2 + 1] * this.matrix[4*3 + 3] - this.matrix[4*3 + 1] * this.matrix[4*2 + 3];
		float c5 = this.matrix[4*2 + 2] * this.matrix[4*3 + 3] - this.matrix[4*3 + 2] * this.matrix[4*2 + 3];
		
		/* 
		 * Check if the transform matrix can be inverted by calculating its determinant.
		 */
		float determinant = (float) 1.0 / (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);

		/* If it can't, throw ArithmeticException as per JSR-184. */
		if(determinant == 0) { throw new ArithmeticException("This transform matrix cannot be inverted."); }

		/* Calculate the inverse. */
		float[] inverseMatrix = new float[]
		{
			( this.matrix[4*1 + 1] * c5 - this.matrix[4*1 + 2] * c4 + this.matrix[4*1 + 3] * c3) * determinant,
			(-this.matrix[4*0 + 1] * c5 + this.matrix[4*0 + 2] * c4 - this.matrix[4*0 + 3] * c3) * determinant,
			( this.matrix[4*3 + 1] * s5 - this.matrix[4*3 + 2] * s4 + this.matrix[4*3 + 3] * s3) * determinant,
			(-this.matrix[4*2 + 1] * s5 + this.matrix[4*2 + 2] * s4 - this.matrix[4*2 + 3] * s3) * determinant,

			(-this.matrix[4*1 + 0] * c5 + this.matrix[4*1 + 2] * c2 - this.matrix[4*1 + 3] * c1) * determinant,
			( this.matrix[4*0 + 0] * c5 - this.matrix[4*0 + 2] * c2 + this.matrix[4*0 + 3] * c1) * determinant,
			(-this.matrix[4*3 + 0] * s5 + this.matrix[4*3 + 2] * s2 - this.matrix[4*3 + 3] * s1) * determinant,
			( this.matrix[4*2 + 0] * s5 - this.matrix[4*2 + 2] * s2 + this.matrix[4*2 + 3] * s1) * determinant,

			( this.matrix[4*1 + 0] * c4 - this.matrix[4*1 + 1] * c2 + this.matrix[4*1 + 3] * c0) * determinant,
			(-this.matrix[4*0 + 0] * c4 + this.matrix[4*0 + 1] * c2 - this.matrix[4*0 + 3] * c0) * determinant,
			( this.matrix[4*3 + 0] * s4 - this.matrix[4*3 + 1] * s2 + this.matrix[4*3 + 3] * s0) * determinant,
			(-this.matrix[4*2 + 0] * s4 + this.matrix[4*2 + 1] * s2 - this.matrix[4*2 + 3] * s0) * determinant,

			(-this.matrix[4*1 + 0] * c3 + this.matrix[4*1 + 1] * c1 - this.matrix[4*1 + 2] * c0) * determinant,
			( this.matrix[4*0 + 0] * c3 - this.matrix[4*0 + 1] * c1 + this.matrix[4*0 + 2] * c0) * determinant,
			(-this.matrix[4*3 + 0] * s3 + this.matrix[4*3 + 1] * s1 - this.matrix[4*3 + 2] * s0) * determinant,
			( this.matrix[4*2 + 0] * s3 - this.matrix[4*2 + 1] * s1 + this.matrix[4*2 + 2] * s0) * determinant
		};

		/* Make the inverse matrix be the transform's matrix. */
		this.matrix = inverseMatrix;
	}

    public void transpose() 
    {
        float[] m = matrix;
        float[] t = new float[16];

        t[0] = m[0]; t[1] = m[4]; t[2] = m[8]; t[3] = m[12];
        t[4] = m[1]; t[5] = m[5]; t[6] = m[9]; t[7] = m[13];
        t[8] = m[2]; t[9] = m[6]; t[10] = m[10]; t[11] = m[14];
        t[12] = m[3]; t[13] = m[7]; t[14] = m[11]; t[15] = m[15];

        System.arraycopy(t, 0, matrix, 0, 16);
    }

    public void multiply(Transform transform) 
    {
        if (transform == null) { throw new NullPointerException("Null transform received"); }

        float[] m1 = this.matrix;
        float[] m2 = transform.matrix;
        float[] result = new float[16];

        for (int i = 0; i < 4; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                result[i * 4 + j] = m1[i * 4 + 0] * m2[0 * 4 + j] +
                                    m1[i * 4 + 1] * m2[1 * 4 + j] +
                                    m1[i * 4 + 2] * m2[2 * 4 + j] +
                                    m1[i * 4 + 3] * m2[3 * 4 + j];
            }
        }
        System.arraycopy(result, 0, this.matrix, 0, 16);
    }

    public void scale(float x, float y, float z) {
        if (Float.isNaN(x) || Float.isInfinite(x) ||
            Float.isNaN(y) || Float.isInfinite(y) ||
            Float.isNaN(z) || Float.isInfinite(z)) {
            throw new IllegalArgumentException("Scale values must be finite.");
        }
        
        float[] scaleMatrix = 
        {
            x, 0, 0, 0,
            0, y, 0, 0,
            0, 0, z, 0,
            0, 0, 0, 1
        };
        Transform scaleTransform = new Transform();
        scaleTransform.set(scaleMatrix);
        multiply(scaleTransform);
    }

    public void scale(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("Null vector received"); }

        scale(v.getX(), v.getY(), v.getZ());
    }

    public void rotate(float x, float y, float z, float angle) 
    {
        if (Float.isNaN(x) || Float.isInfinite(x) ||
            Float.isNaN(y) || Float.isInfinite(y) ||
            Float.isNaN(z) || Float.isInfinite(z) ||
            Float.isNaN(angle) || Float.isInfinite(angle)) 
        {
            throw new IllegalArgumentException("Rotation parameters must be finite.");
        }
        
        float rad = (float) Math.toRadians(angle);
        float c = (float) Math.cos(rad);
        float s = (float) Math.sin(rad);
        float[] rotationMatrix = {
            c + (1 - c) * x * x, (1 - c) * x * y - s * z, (1 - c) * x * z + s * y, 0,
            (1 - c) * y * x + s * z, c + (1 - c) * y * y, (1 - c) * y * z - s * x, 0,
            (1 - c) * z * x - s * y, (1 - c) * z * y + s * x, c + (1 - c) * z * z, 0,
            0, 0, 0, 1
        };
        Transform rotationTransform = new Transform();
        rotationTransform.set(rotationMatrix);
        multiply(rotationTransform);
    }

    public void rotate(Vector3D v, float angle) 
    {
        if (v == null) { throw new NullPointerException("Null vector received"); }
        rotate(v.getX(), v.getY(), v.getZ(), angle);
    }

    public void translate(float x, float y, float z) 
    {
        if (Float.isNaN(x) || Float.isInfinite(x) ||
            Float.isNaN(y) || Float.isInfinite(y) ||
            Float.isNaN(z) || Float.isInfinite(z)) 
        {
            throw new IllegalArgumentException("Translation parameters must be finite.");
        }
        
        float[] translationMatrix = 
        {
            1, 0, 0, x,
            0, 1, 0, y,
            0, 0, 1, z,
            0, 0, 0, 1
        };

        Transform translationTransform = new Transform();
        translationTransform.set(translationMatrix);
        multiply(translationTransform);
    }

    public void translate(Vector3D v) 
    {
        if (v == null) { throw new NullPointerException("Null vector received"); }

        translate(v.getX(), v.getY(), v.getZ());
    }

    public void lookAt(Vector3D position, Vector3D look, Vector3D up) 
    {
        if (position == null || look == null || up == null) { throw new NullPointerException("Null argument received"); }
        
        Vector3D zaxis = new Vector3D();
        zaxis.set(look);
        zaxis.add(-position.getX(), -position.getY(), -position.getZ());
        zaxis.normalize();

        Vector3D xaxis = new Vector3D();
        xaxis.cross(up, zaxis);
        xaxis.normalize();

        Vector3D yaxis = new Vector3D();
        yaxis.cross(zaxis, xaxis);

        float[] lookAtMatrix = 
        {
            xaxis.getX(), yaxis.getX(), zaxis.getX(), 0,
            xaxis.getY(), yaxis.getY(), zaxis.getY(), 0,
            xaxis.getZ(), yaxis.getZ(), zaxis.getZ(), 0,
            -xaxis.dot(position), -yaxis.dot(position), -zaxis.dot(position), 1
        };

        set(lookAtMatrix);
    }

    public void transVector(Vector3D v, Vector3D result) 
    {
        if (v == null || result == null) { throw new NullPointerException("Null argument received"); }
        
        float[] coords = new float[4];
        coords[0] = v.getX();
        coords[1] = v.getY();
        coords[2] = v.getZ();
        coords[3] = 1.0f; // Homogeneous coordinate

        float[] transformed = new float[4];
        for (int i = 0; i < 4; i++) 
        {
            transformed[i] = matrix[i * 4] * coords[0] +
                            matrix[i * 4 + 1] * coords[1] +
                            matrix[i * 4 + 2] * coords[2] +
                            matrix[i * 4 + 3] * coords[3];
        }

        result.set(transformed[0], transformed[1], transformed[2]);
    }
}
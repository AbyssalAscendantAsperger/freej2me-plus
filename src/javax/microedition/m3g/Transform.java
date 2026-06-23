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
package javax.microedition.m3g;

import java.util.Arrays;

public class Transform
{

	// This is a 4x4 matrix represented as a 16 item long array.
	// The items are in row major order:
	//   [  0,  1,  2,  3 ]
	//   [  4,  5,  6,  7 ]   Addressing in 2D vs in 1D:
	//   [  8,  9, 10, 11 ]     mat_2D[row][col] == mat_1D[4*row + col]
	//   [ 12, 13, 14, 15 ]
	private float[] matrix = new float[] 
	{
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		0, 0, 0, 1
	};

	// Matrix for operations such as scale, translation, etc
	private static final float[] manipulationMatrix = new float[] 
	{
		1, 0, 0, 0,
		0, 1, 0, 0,
		0, 0, 1, 0,
		0, 0, 0, 1
	};

	/* ------------------------- public methods ------------------------- */
	public Transform() { }

	public Transform(Transform transform)
	{
		/* As per JSR-184, throw NullPointerException if the given transform is null. */
		if(transform == null) { throw new NullPointerException("Cannot initialize with a null transform."); }

		this.matrix = transform.matrix.clone();
	}

	public void get(float[] matrix)
	{
		/* As per JSR-184, throw NullPointerException if the given matrix is null.*/
		if(matrix == null) { throw new NullPointerException("Cannot copy the matrix contents to a null object."); }

		/* Also per JSR-184, throw IllegalArgumentException if the matrix length is less than 16.*/
		if(matrix.length < 16) { throw new IllegalArgumentException("The received matrix is not a valid 4x4 transform matrix."); }

		System.arraycopy(this.matrix, 0, matrix, 0, 16);
	}

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

	public void postMultiply(Transform transform)
	{
		/* As per JSR-184, throw NullPointerException if the given transform is null. */
		if(transform == null) { throw new NullPointerException("Cannot multiply by receiving a null transform."); }
	
		multiply(this.matrix.clone(), transform.matrix);
	}

	public void postRotate(float angle, float ax, float ay, float az)
	{
		Transform.rotate(angle, ax, ay, az);
		multiply(this.matrix.clone(), Transform.manipulationMatrix);
	}

	public void postRotateQuat(float qx, float qy, float qz, float qw)
	{
		Transform.rotateQuat(qx, qy, qz, qw);
		multiply(this.matrix.clone(), Transform.manipulationMatrix);
	}

	public void postScale(float sx, float sy, float sz)
	{
		resetManipulationMatrix();
		manipulationMatrix[0]  = sx;
		manipulationMatrix[5]  = sy;
		manipulationMatrix[10] = sz;
		multiply(this.matrix.clone(), Transform.manipulationMatrix);
	}

	public void postTranslate(float tx, float ty, float tz)
	{
		resetManipulationMatrix();
		manipulationMatrix[3]  = tx;
		manipulationMatrix[7]  = ty;
		manipulationMatrix[11] = tz;
		multiply(this.matrix.clone(), Transform.manipulationMatrix);
	}

	public void set(float[] matrix)
	{
		/* As per JSR-184, throw NullPointerException if the given matrix is null. */
		if(matrix == null) { throw new NullPointerException("Tried setting the transform with a null matrix."); }

		/* Also per JSR-184, IllegalArgumentException if matrix.length < 16 (not a 4x4 matrix). */
		if(matrix.length < 16) { throw new IllegalArgumentException("Cannot copy data from a matrix with less than 16 elements."); }

		System.arraycopy(matrix, 0, this.matrix, 0, 16);
	}

	public void set(Transform transform)
	{
		/* As per JSR-184, throw NullPointerException if the given transform is null. */
		if(transform == null) { throw new NullPointerException("Tried to set a null transform."); }

		this.matrix = transform.matrix.clone();
	}

	public void setIdentity()
	{
		this.matrix[0] = 1;
		this.matrix[1] = 0;
		this.matrix[2] = 0;
		this.matrix[3] = 0;

		this.matrix[4] = 0;
		this.matrix[5] = 1;
		this.matrix[6] = 0;
		this.matrix[7] = 0;

		this.matrix[8]  = 0;
		this.matrix[9]  = 0;
		this.matrix[10] = 1;
		this.matrix[11] = 0;

		this.matrix[12] = 0;
		this.matrix[13] = 0;
		this.matrix[14] = 0;
		this.matrix[15] = 1;
	}

	public static void resetManipulationMatrix() 
	{
		manipulationMatrix[0] = 1;
		manipulationMatrix[1] = 0;
		manipulationMatrix[2] = 0;
		manipulationMatrix[3] = 0;

		manipulationMatrix[4] = 0;
		manipulationMatrix[5] = 1;
		manipulationMatrix[6] = 0;
		manipulationMatrix[7] = 0;

		manipulationMatrix[8]  = 0;
		manipulationMatrix[9]  = 0;
		manipulationMatrix[10] = 1;
		manipulationMatrix[11] = 0;

		manipulationMatrix[12] = 0;
		manipulationMatrix[13] = 0;
		manipulationMatrix[14] = 0;
		manipulationMatrix[15] = 1;
	}

	public void transform(float[] vectors)
	{
		/* As per JSR-184, throw NullPointerException if the given vector is null. */
		if(vectors == null) { throw new NullPointerException("Cannot transform a null vector."); }
		
		/* Also per JSR-184, throw IllegalArgumentException if the given vector is not a flat array of quadruplets. */
		if(vectors.length % 4 != 0) { throw new IllegalArgumentException("Cannot transform a vector array that's not multiple of 4."); }

		/* Multiply each 4D vector with this transform's matrix by quadruplets, hence the vector offset of 4. */
		float x, y, z, w;
		for (int offset = 0; offset < vectors.length; offset += 4) 
		{
			x = vectors[offset];
			y = vectors[offset + 1];
			z = vectors[offset + 2];
			w = vectors[offset + 3];

			for (int row = 0; row < 4; row++) 
			{
				vectors[offset + row] =
					this.matrix[4 * row + 0] * x +
					this.matrix[4 * row + 1] * y +
					this.matrix[4 * row + 2] * z +
					this.matrix[4 * row + 3] * w;
			}
		}
	}

	public void transform(VertexArray in, float[] out, boolean W)
	{
		/* As per JSR-184, throw NullPointerException if either 'in' or 'out' are null. */
		if(in == null || out == null) { throw new NullPointerException("Cannot transform since input vertex array or output array are null."); }

		int vertexCount = in.getVertexCount();
		int vertexDims = in.getComponentCount();

		/* Also per JSR-184, throw IllegalArgumentException if numComponents == 4 or out.length < (4 * vertexCount). */
		if (vertexDims == 4 || out.length < 4 * vertexCount) { throw new IllegalArgumentException("Tried to transform an invalid vertex array."); }

		// Fill the `out` array with raw data
		if (in.getComponentType() == 1) 
		{
			byte[] vertices = new byte[vertexCount * vertexDims];
			in.get(0, vertexCount, vertices);

			for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++)
			{
				int  in_offset = vertexIndex * vertexDims;
				int out_offset = vertexIndex * 4;

				out[out_offset] = vertices[in_offset]; // x
				out[out_offset + 1] = vertexDims > 1 ? vertices[in_offset + 1] : 0.0f; // y
				out[out_offset + 2] = vertexDims > 2 ? vertices[in_offset + 2] : 0.0f; // z
				out[out_offset + 3] = vertexDims > 3 ? vertices[in_offset + 3] : (W ? 1f : 0f); // w
			}
		}
		else 
		{
			short[] vertices = new short[vertexCount * vertexDims];
			in.get(0, vertexCount, vertices);

			for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++)
			{
				int  in_offset = vertexIndex * vertexDims;
				int out_offset = vertexIndex * 4;

				out[out_offset] = vertices[in_offset]; // x
				out[out_offset + 1] = vertexDims > 1 ? vertices[in_offset + 1] : 0.0f; // y
				out[out_offset + 2] = vertexDims > 2 ? vertices[in_offset + 2] : 0.0f; // z
				out[out_offset + 3] = vertexDims > 3 ? vertices[in_offset + 3] : (W ? 1f : 0f); // w
			}
		}

		// Do the transformation on the raw data that is currently in `out`
		this.transform(out);
	}

	public void transpose()
	{
		float[] old = this.matrix.clone();

		/* Transposes the matrix column by column. */
		for (int i = 0; i < 16; i++) { this.matrix[4*(i/4) + (i%4)] = old[4*(i%4) + (i/4)]; }
	}

	/* ------------------------- package methods ------------------------- */

	// The pre* methods exist to facilitate chaining transformations.
	// They are mainly used in rendering.

	// package-private
	void preMultiply(Transform transform)
	{
		if (transform == null) { throw new java.lang.NullPointerException("preMultiply() called with null transform."); }
		multiply(transform.matrix, this.matrix.clone());
	}

	// package-private
	void preRotate(float angle, float ax, float ay, float az)
	{
		Transform.rotate(angle, ax, ay, az);
		multiply(Transform.manipulationMatrix, this.matrix.clone());
	}

	// package-private
	void preRotateQuat(float qx, float qy, float qz, float qw)
	{
		Transform.rotateQuat(qx, qy, qz, qw);
		multiply(Transform.manipulationMatrix, this.matrix.clone());
	}

	// package-private
	static void rotate(float angle, float ax, float ay, float az)
	{
		/* As per JSR-184, throw IllegalArgumentException if the rotation axis is zero but the angle is not. */
		if(ax == 0 && ay == 0 && az == 0 && angle != 0) { throw new IllegalArgumentException("The rotation axis is zero while angle is nonZero."); }

		resetManipulationMatrix();

		// If angle is 0, return right away;
		if (angle == 0) { return; }
		
		// Compute sine and cosine of the angle
		float rad = M3GMath.toRadians(angle);
		float s = M3GMath.sin(rad);
		float c = M3GMath.cos(rad);
		float d = 1f - c;

		// Normalize the axis
		float l = M3GMath.sqrt((ax * ax) + (ay * ay) + (az * az));
		float x = ax / l;
		float y = ay / l;
		float z = az / l;

		manipulationMatrix[0] = x*x*d +  c;
		manipulationMatrix[1] = y*x*d - z*s;
		manipulationMatrix[2] = z*x*d + y*s;
		manipulationMatrix[3] = 0;

		manipulationMatrix[4] = x*y*d + z*s;
		manipulationMatrix[5] = y*y*d +  c;
		manipulationMatrix[6] = z*y*d - x*s;
		manipulationMatrix[7] = 0;

		manipulationMatrix[8]  = x*z*d - y*s;
		manipulationMatrix[9]  = y*z*d + x*s;
		manipulationMatrix[10] = z*z*d +  c;
		manipulationMatrix[11] = 0;

		manipulationMatrix[12] = 0;
		manipulationMatrix[13] = 0;
		manipulationMatrix[14] = 0;
		manipulationMatrix[15] = 1;
	}

	// package-private
	static void rotateQuat(float qx, float qy, float qz, float qw)
	{
		/* As per JSR-184, throw IllegalArgumentException if all quaternion components are zero. */
		if(qx == 0 && qy == 0 && qz == 0 && qw == 0) { throw new IllegalArgumentException("Cannot rotate when all quaternion components are zero."); }

		// Normalize the quaternion
		float l = M3GMath.sqrt((qx * qx) + (qy * qy) + (qz * qz) + (qw * qw));
		float x = qx / l;
		float y = qy / l;
		float z = qz / l;
		float w = qw / l;

		resetManipulationMatrix();
		manipulationMatrix[0] = 1-2*y*y-2*z*z;
		manipulationMatrix[1] = 2*x*y-2*z*w;
		manipulationMatrix[2] = 2*x*z+2*y*w;
		manipulationMatrix[3] = 0;

		manipulationMatrix[4] = 2*x*y+2*z*w;
		manipulationMatrix[5] = 1-2*x*x-2*z*z;
		manipulationMatrix[6] = 2*y*z-2*x*w;
		manipulationMatrix[7] = 0;

		manipulationMatrix[8]  = 2*x*z-2*y*w;
		manipulationMatrix[9]  = 2*y*z+2*x*w;
		manipulationMatrix[10] = 1-2*x*x-2*y*y;
		manipulationMatrix[11] = 0;

		manipulationMatrix[12] = 0;
		manipulationMatrix[13] = 0;
		manipulationMatrix[14] = 0;
		manipulationMatrix[15] = 1;
	}

	/* ------------------------- private methods ------------------------- */

	private void multiply(float[] left, float[] right)
	{
		for (int row = 0; row < 4; row++)
		{
			this.matrix[4 * row] = left[4 * row] * right[0] + left[4 * row + 1] * right[4] + 
				left[4 * row + 2] * right[8] + left[4 * row + 3] * right[12];

			this.matrix[4 * row + 1] = left[4 * row] * right[1] + left[4 * row + 1] * right[5] + 
				left[4 * row + 2] * right[9] + left[4 * row + 3] * right[13];

			this.matrix[4 * row + 2] = left[4 * row] * right[2] + left[4 * row + 1] * right[6] + 
				left[4 * row + 2] * right[10] + left[4 * row + 3] * right[14];

			this.matrix[4 * row + 3] = left[4 * row] * right[3] + left[4 * row + 1] * right[7] + 
				left[4 * row + 2] * right[11] + left[4 * row + 3] * right[15];
		}
	}

}

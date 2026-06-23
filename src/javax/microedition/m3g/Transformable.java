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

import org.recompile.mobile.Mobile;

public abstract class Transformable extends Object3D
{
	private Transform matrix = new Transform();
	private Transform scale = new Transform();
	private Transform rotate = new Transform();
	private Transform translate = new Transform();

	public void getCompositeTransform(Transform transform)
	{
		if (transform == null) { throw new NullPointerException("Cannot copy composite transform data into a null transform."); }

		// Composite Transform is given as: C=T*R*S*M
		transform.setIdentity();
		transform.postMultiply(this.translate);
		transform.postMultiply(this.rotate);
		transform.postMultiply(this.scale);
		transform.postMultiply(this.matrix);
	}

	protected Object3D duplicateImpl() 
	{
		Transformable copy = (Transformable) super.duplicateImpl();
		copy.matrix = new Transform(matrix);
		copy.scale = new Transform(scale);
		copy.rotate = new Transform(rotate);
		copy.translate = new Transform(translate);

		return copy;
	}

	public void getOrientation(float[] angleAxis)
	{
		if (angleAxis == null) { throw new NullPointerException("Cannot copy orientation data into a null array."); }
		if (angleAxis.length < 4) { throw new IllegalArgumentException("Illegal length of angle axis array"); }

		final float ax, ay, az;
		final float[] m = new float[16];
		this.rotate.get(m);
		
		final float angle = M3GMath.acos(((m[0] + m[5] + m[10]) - 1) / 2);
		
		ax = angle == 0 ? 0 : (m[9] - m[6]) / (2 * M3GMath.sin(angle));
		ay = angle == 0 ? 0 : (m[2] - m[8]) / (2 * M3GMath.sin(angle));
		az = angle == 0 ? 0 : (m[4] - m[1]) / (2 * M3GMath.sin(angle));

		angleAxis[0] = angle;
		angleAxis[1] = ax;
		angleAxis[2] = ay;
		angleAxis[3] = az;
	}

	public void getScale(float[] xyz)
	{
		if (xyz == null) { throw new NullPointerException("Cannot copy scale data into a null array."); }
		if (xyz.length < 3) { throw new IllegalArgumentException("Illegal size of scale array"); }

		float[] m = new float[16];
		this.scale.get(m);
		xyz[0] = m[4*0 + 0];
		xyz[1] = m[4*1 + 1];
		xyz[2] = m[4*2 + 2];
	}

	public void getTransform(Transform transform)
	{
		if (transform == null) { throw new NullPointerException("Cannot copy transform data into a null transform."); }

		transform.set(this.matrix);
	}

	public void getMatrix(float[] matrix)
	{
		if (matrix == null) { throw new NullPointerException("Cannot copy matrix data into a null matrix."); }

		System.arraycopy(this.matrix, 0, matrix, 0, 16);
	}

	public void getTranslation(float[] xyz)
	{
		if (xyz == null) { throw new NullPointerException("Cannot copy translation data into a null array."); }
		if (xyz.length < 3) { throw new IllegalArgumentException("Illegal size of translation array"); }

		float[] m = new float[16];
		this.translate.get(m);
		xyz[0] = m[4*0 + 3];
		xyz[1] = m[4*1 + 3];
		xyz[2] = m[4*2 + 3];
	}

	public void postRotate(float angle, float ax, float ay, float az)
	{
		this.rotate.postRotate(angle, ax, ay, az);
	}

	public void preRotate(float angle, float ax, float ay, float az)
	{
		this.rotate.preRotate(angle, ax, ay, az);
	}

	public void scale(float sx, float sy, float sz)
	{
		float[] xyz = new float[3];
		getScale(xyz);
		sx *= xyz[0];
		sy *= xyz[1];
		sz *= xyz[2];
		this.scale.postScale(sx, sy, sz);
	}

	public void setOrientation(float angle, float ax, float ay, float az)
	{
		this.rotate.setIdentity();
		this.rotate.preRotate(angle, ax, ay, az);
	}

	public void setScale(float sx, float sy, float sz)
	{
		this.scale.setIdentity();
		this.scale.postScale(sx, sy, sz);
	}

	public void setTransform(Transform transform)
	{
		if (transform == null)
		{
			Mobile.log(Mobile.LOG_WARNING, Graphics3D.class.getPackage().getName() + "." + Graphics3D.class.getSimpleName() + ": " + "Received null transform! Creating identity transform...");
			this.matrix.setIdentity();
			return;
		}

		if (this instanceof Node) 
		{
			final float[] m = new float[16];
			transform.get(m);

			if (m[12] != 0 || m[13] != 0 || m[14] != 0 || m[15] != 1) { throw new IllegalArgumentException("The bottom row of the transform must be (0, 0, 0, 1) for Node objects."); }
		}

		this.matrix = new Transform(transform);
	}

	public void setTranslation(float tx, float ty, float tz)
	{
		this.translate.setIdentity();
		this.translate.postTranslate(tx, ty, tz);
	}

	public void translate(float tx, float ty, float tz)
	{
		float[] xyz = new float[3];
		getTranslation(xyz);
		tx += xyz[0];
		ty += xyz[1];
		tz += xyz[2];
		this.translate.postTranslate(tx, ty, tz);
	}

	@Override
	void updateProperty(int property, float[] value) 
	{
		Mobile.log(Mobile.LOG_WARNING, Graphics3D.class.getPackage().getName() + "." + Graphics3D.class.getSimpleName() + ": " + "AnimTrack updating Transformable property");
		switch (property) 
		{
			case AnimationTrack.ORIENTATION:
				setOrientation(value[0], value[1], value[2], value[3]);
				break;
			case AnimationTrack.TRANSLATION:
				translate(value[0], value[1], value[2]);
				break;
			case AnimationTrack.SCALE:
				scale(value[0], value[1], value[2]);
				break;
			default:
				super.updateProperty(property, value);
		}
		boolean invalidate = true;
	}

	void invalidateTransformable() 
	{
		if (!(this instanceof Texture2D)) 
		{
			if (((Node) this).parent != null && (((Node) this).hasRenderables || ((Node) this).hasBones)) 
			{
				((Node) this).parent.invalidateNode(new boolean[]{false, false});
			}
		}
	}

	boolean animTrackCompatible(AnimationTrack track) 
	{
		switch (track.getTargetProperty()) 
		{
			case AnimationTrack.ORIENTATION:
			case AnimationTrack.SCALE:
			case AnimationTrack.TRANSLATION:
				return true;
			default:
				return super.animTrackCompatible(track);
		}
	}
}

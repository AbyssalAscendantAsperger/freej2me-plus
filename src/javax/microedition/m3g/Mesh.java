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

import java.util.Vector;

public class Mesh extends Node
{

	private VertexBuffer vertices;
	private IndexBuffer[] submeshes;
	private Appearance[] appearances;

	protected Mesh() { }

	protected Object3D duplicateImpl() 
	{
		Mesh copy = (Mesh) super.duplicateImpl();
		copy.submeshes = (IndexBuffer[]) submeshes.clone();
		copy.appearances = (Appearance[]) appearances.clone();
		return copy;
	}

	public Mesh(VertexBuffer vertices, IndexBuffer submesh, Appearance appearance) 
	{
		if ((vertices == null) || (submesh == null)) { throw new NullPointerException("Cannot create mesh due to a null element"); }

		this.vertices = vertices;
		this.submeshes = new IndexBuffer[]{submesh};
		appearances = new Appearance[]{appearance};
		if(appearance != null) // Appearance can be null here, so only add the reference if it isn't
		{ 
			addReference(appearances[0]);
		} 
		addReference(this.vertices);
		addReference(this.submeshes[0]);
	}

	public Mesh(VertexBuffer vertices, IndexBuffer[] submeshes, Appearance[] appearances) 
	{
		if ((vertices == null) || (submeshes == null) || hasArrayNullElement(submeshes)) 
		{
			throw new NullPointerException("Cannot create mesh due to a null element");
		}
		if ((submeshes.length == 0) || ((appearances != null) && (appearances.length < submeshes.length))) 
		{
			throw new IllegalArgumentException("Cannot create mesh, one of the provided arguments is invalid");
		}

		this.vertices = vertices;
		this.submeshes = new IndexBuffer[submeshes.length];
		this.appearances = new Appearance[submeshes.length];

		for (int i = 0; i < submeshes.length; i++) 
		{
			if (submeshes[i] == null) { throw new NullPointerException("Cannot add a null submesh to this mesh object"); }

			this.submeshes[i] = submeshes[i];
			addReference(this.submeshes[i]);

			if (appearances != null && appearances[i] != null) 
			{
				this.appearances[i] = appearances[i];
				addReference(this.appearances[i]);
			}
		}

		addReference(this.vertices);
	}

	public Appearance getAppearance(int index) 
	{ 
		if (index < 0 || index >= submeshes.length) { throw new IndexOutOfBoundsException("Cannot get invalid appearance index"); }
		return appearances[index]; 
	}

	public IndexBuffer getIndexBuffer(int index) 
	{ 
		if (index < 0 || index >= submeshes.length) { throw new IndexOutOfBoundsException("Cannot get invalid index buffer index"); }
		return submeshes[index]; 
	}

	public int getSubmeshCount() { return submeshes.length; }

	public VertexBuffer getVertexBuffer() { return vertices; }

	public void setAppearance(int index, Appearance appearance) 
	{ 
		if (index < 0 || index >= submeshes.length) { throw new IndexOutOfBoundsException("Cannot set to invalid appearance index"); }
		removeReference(appearances[index]);
		appearances[index] = appearance;
		addReference(appearances[index]);
	}

	private boolean hasArrayNullElement(IndexBuffer[] buffer) 
	{
		for (int i = 0; i < buffer.length; i++) 
		{
			if (buffer[i] == null) { return true; }
		}
		return false;
	}

}

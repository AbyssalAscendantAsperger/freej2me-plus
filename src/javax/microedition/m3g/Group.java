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

public class Group extends Node
{

	public Node firstChild;
	public int numNonCullables = 0, numRenderables = 0;

	protected Object3D duplicateImpl() 
	{
		Group copy = (Group) super.duplicateImpl();
		copy.firstChild = (Node) firstChild.duplicateImpl();
		
		copy.removeReference(firstChild);
		copy.addReference(copy.firstChild);

		copy.firstChild.parent = copy;

		return copy;
	}

	public void addChild(Node child) 
	{
		if (child == null) { throw new NullPointerException("child can not be null"); }
		if (child == this) { throw new IllegalArgumentException("can not add self as child"); }

		if (child.parent == null) 
		{
			if (firstChild == null) 
			{
				firstChild = child;
				child.left = child;
				child.right = child;
			} 
			else 
			{
				Node linkChild = firstChild;
				child.left = linkChild.left;
				linkChild.left.right = child;

				child.right = linkChild;
				linkChild.left = child;
			}
			child.setParent(this);
			addReference(child);
		}
	}

	public Node getChild(int idx) 
	{
		if (idx < 0) { throw new IllegalArgumentException(); }

		Node n = firstChild;
		while (idx-- > 0) 
		{
			n = n.right;
			if (n == firstChild) { throw new IllegalArgumentException(); }
		}
		return n;
	}

	public int getChildCount() 
	{
		int count = 0;
		Node child = firstChild;
		if (child != null) 
		{
			do 
			{
				++count;
				child = child.right;
			} while (child != firstChild);
		}
		return count;
	}

	@Override
	boolean doAlign(Node ref) 
	{
		if (!super.doAlign(ref)) { return false; }

		Node child = firstChild;
		if (child != null) 
		{
			do 
			{
				if (!child.doAlign(ref)) { return false; } 
				child = child.right;
			} while (child != firstChild);
		}
		return true;
	}

	public boolean pick(int scope, float x, float y, Camera camera, RayIntersection ri) 
	{
		// TODO
		return false;
	}

	public boolean pick(int scope, float ox, float oy, float oz, float dx, float dy, float dz, RayIntersection ri) 
	{
		// TODO
		return false;
	}

	public void removeChild(Node child) 
	{
		if (child != null && firstChild != null) 
		{
			Node n = firstChild;
			do 
			{
				if (n == child) 
				{
					n.right.left = n.left;
					n.left.right = n.right;

					if (firstChild == n) { firstChild = (n.right != n) ? n.right : null; }

					n.left = null;
					n.right = null;
					n.setParent(null);
					removeReference(child);
					return;
				}
				n = n.right;
			} while (n != firstChild);
		}
	}

}

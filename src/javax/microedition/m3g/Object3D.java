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

public abstract class Object3D implements Cloneable
{

	protected int userID = 0;
	protected Object userObject = null;
	Vector<AnimationTrack> animationTracks = new Vector<AnimationTrack>();
	Vector<Object3D> curReferences = new Vector<Object3D>();

	void updateProperty(int property, float[] value) { }

	public final Object3D duplicate() { return this.duplicateImpl(); }

	@SuppressWarnings("unchecked") // Those two vectors will always house AnimationTracks and Object3Ds
	protected Object3D duplicateImpl() 
	{
		try 
		{
			Object3D copy = (Object3D) this.clone();
			copy.animationTracks = (Vector<AnimationTrack>) animationTracks.clone();
			copy.curReferences = (Vector<Object3D>) curReferences.clone();

			return copy;
		} 
		catch (CloneNotSupportedException e) { } // Shouldn't ever happen
		
		return null;
	}

	public Object3D find(int userID) 
	{
		if (this.userID == userID) { return this; }

		Object3D found = null;

		for (int obj = 0; obj < this.curReferences.size(); obj++) 
		{ 
			found = ((Object3D) this.curReferences.get(obj)).find(userID);
			if(found != null) { return found; }
		}

		return null;
	}

	public int getReferences(Object3D[] references) 
	{ 
		if(references != null && references.length < curReferences.size()) { throw new IllegalArgumentException("references array is not large enough to hold all of this object's references"); }
		
		if (references != null) 
		{
			for (int reference = 0; reference < curReferences.size(); ++reference) 
			{
				references[reference] = (Object3D) curReferences.get(reference);
			}
		}

		return curReferences.size();
	}

	public int getUserID() { return userID; }

	public void setUserID(int userID) { this.userID = userID; }

	public Object getUserObject() { return userObject; }

	public void setUserObject(Object userObject) { this.userObject = userObject; }

	public void addAnimationTrack(AnimationTrack animationTrack) 
	{
		if (animationTrack == null) { throw new NullPointerException("AnimationTrack cannot be null"); }

		if (animationTracks.contains(animationTrack))
		{
			throw new IllegalArgumentException("AnimationTrack already exists");
		}

		int newTrackTarget = animationTrack.getTargetProperty();
		int components = animationTrack.getKeyframeSequence().getComponentCount();
		int i;
		for (i = 0; i < animationTracks.size(); i++) 
		{
			AnimationTrack track = (AnimationTrack) animationTracks.elementAt(i);

			if (track.getTargetProperty() > newTrackTarget) { break; }

			if (track.getTargetProperty() == newTrackTarget && (track.getKeyframeSequence().getComponentCount() != components)) 
			{
				throw new IllegalArgumentException();
			}
		}

		animationTracks.add(i, animationTrack);
		addReference(animationTrack);
	}

	public AnimationTrack getAnimationTrack(int index) { return (AnimationTrack) animationTracks.elementAt(index); }

	public void removeAnimationTrack(AnimationTrack animationTrack) { animationTracks.removeElement(animationTrack); }

	public int getAnimationTrackCount() { return animationTracks.size(); }

	public final int animate(int time) 
	{ 
		int validity = 0x7FFFFFFF;

		if (animationTracks.isEmpty()) { return validity; }

		int numTracks = animationTracks.size();

		for (int trackIndex = 0; trackIndex < numTracks; ) 
		{
			AnimationTrack track = (AnimationTrack) animationTracks.elementAt(trackIndex);
			KeyframeSequence sequence = track.sequence;

			int components = sequence.componentCount;
			int property = track.property;
			int nextProperty;

			float sumWeights = 0;
			float[] sumValues = new float[components];

			for (int i = 0; i < components; i++) sumValues[i] = 0;

			do 
			{
				float[] weight = new float[1];
				int[] Validity = new int[1];

				track.getContribution(time, sumValues, weight, Validity);
				if (Validity[0] <= 0)
					return 0;

				sumWeights += weight[0];
				validity = M3GMath.min(validity, Validity[0]);

				if (++trackIndex == numTracks) { break; }
				track = (AnimationTrack) animationTracks.elementAt(trackIndex);
				nextProperty = track.property;
			} while (nextProperty == property);

			if (sumWeights > 0) { updateProperty(property, sumValues); }
		}
		return validity;
	}

	boolean animTrackCompatible(AnimationTrack animationtrack) { return false; }

	protected void addReference(Object3D obj) 
	{
		if(obj == null) { return; } 
		curReferences.add(obj); 
	}

	protected void removeReference(Object3D obj) 
	{
		if(obj == null) { return; }
		curReferences.remove(obj);
	}
}

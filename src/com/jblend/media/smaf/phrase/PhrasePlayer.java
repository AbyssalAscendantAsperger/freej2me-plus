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
package com.jblend.media.smaf.phrase;

public class PhrasePlayer extends PhrasePlayerBase
{
	protected static PhrasePlayer phrasePlayer;

	public static PhrasePlayer getPlayer() 
	{ 
		PhrasePlayerBase.setup();
		phrasePlayer = new PhrasePlayer();
		return phrasePlayer; 
	}

	public void disposePlayer() 
	{ 
		super.dispose();
		phrasePlayer = null;
	}

	public PhraseTrack getTrack() { return super.getPhraseTrack(); }

	public AudioPhraseTrack getAudioTrack() { return super.getAudioTrack(); }

	public PhraseTrack getTrack(int track) { return super.getPhraseTrack(); }

	public void disposeTrack(PhraseTrack t) { super.disposePhraseTrack(t); }
}
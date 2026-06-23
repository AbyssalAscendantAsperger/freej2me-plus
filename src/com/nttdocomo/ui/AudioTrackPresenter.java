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
package com.nttdocomo.ui;

public class AudioTrackPresenter extends AudioPresenter 
{

    protected AudioTrackPresenter() { }

    public Audio3D getAudio3D() { throw new UnsupportedOperationException("getAudio3D() cannot be called on AudioTrackPresenter."); }

    public int getCurrentTime() { return super.getCurrentTime(); }

    public int getTotalTime() { return super.getTotalTime(); }

    public void pause() { super.pause(); }

    public void play() { play(0); }

    public void play(int time) { super.play(time); }

    public void restart() { super.restart(); }

    public void setAttribute(int attr, int value) { super.setAttribute(attr, value); }

    public void setSound(MediaImage sound) { /* TODO */ }

    public void setSound(MediaSound sound) { throw new UnsupportedOperationException("setSound(MediaSound) cannot be called on AudioTrackPresenter."); }

    public void setSyncEvent(int channel, int key) { throw new UnsupportedOperationException("setSyncEvent() cannot be called on AudioTrackPresenter."); }
}
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
package javax.microedition.media.decoders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MIDIPatcher 
{
    
    /*
     * This class has the purpose of patching up MIDI files with Running Status bytes,
     * as Java doesn't support these up until Java 24. This might expand to 
     * solve other issues later, if any are found.
     */

    public static final byte[] patchMidi(byte[] midiData) throws IOException 
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        if (midiData.length < 14 || midiData[0] != 'M' || midiData[1] != 'T' ||
            midiData[2] != 'h' || midiData[3] != 'd') 
        {
            throw new IOException("Invalid MIDI header");
        }
        
        int headerLength = readInt(midiData, 4);
        int format = readShort(midiData, 8);        
        int numberOfTracks = readShort(midiData, 10);
        int tickdiv = readShort(midiData, 12);

        outputStream.write(midiData, 0,  headerLength + 8);

        int currentPosition = headerLength + 8;

        // Process each track chunk
        while (currentPosition < midiData.length) 
        {
            // Check for "MTrk" identifier
            if (midiData[currentPosition] == 'M' && midiData[currentPosition + 1] == 'T' &&
                midiData[currentPosition + 2] == 'r' && midiData[currentPosition + 3] == 'k') 
            {
                
                // Copy the "MTrk" string to the outputStream, length will be updated with the patched track's size later
                outputStream.write(midiData, currentPosition, 4);
                currentPosition += 4;

                // Read the length of the track
                int trackLength = readInt(midiData, currentPosition);
                currentPosition += 4;

                byte[] trackData = new byte[trackLength];
                
                System.arraycopy(midiData, currentPosition, trackData, 0, trackLength);
                
                // Patch the track data
                byte[] patchedTrack = patchTrack(trackData);

                byte[] patchedTrackLength = new byte[] 
                {
                    (byte) (patchedTrack.length >> 24),
                    (byte) (patchedTrack.length >> 16),
                    (byte) (patchedTrack.length >> 8),
                    (byte) patchedTrack.length
                };
                outputStream.write(patchedTrackLength);
                outputStream.write(patchedTrack);
                
                // Move to the next track chunk
                currentPosition += trackLength;
            } 
            else { throw new IOException("Invalid MIDI file structure: missing 'MTrk' chunk"); }
        }
        
        return outputStream.toByteArray();
    }

    private static final byte[] patchTrack(byte[] trackData) throws IOException 
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int runningStatus = 0;

        int i = 0;
        while (i < trackData.length) 
        {
            int deltaTimeLength = getVarIntLength(trackData, i);
            outputStream.write(trackData, i, deltaTimeLength); // Keep delta time
            
            i+=deltaTimeLength;
            
            // Read the MIDI event
            int byteValue = trackData[i++] & 0xFF;
            
            // 3-byte events (Note On/Off, Poly Pressure, Controller, Pitch Bend)
            if ((byteValue >= 0x80 && byteValue < 0xC0) || (byteValue >= 0xE0 && byteValue < 0xF0)) 
            {
                // New status byte
                runningStatus = byteValue; // Update running status
                outputStream.write(runningStatus);
                outputStream.write(trackData[i++] & 0xFF); // Status data (Note, program, pressure, etc)
                outputStream.write(trackData[i++] & 0xFF); // Additional data byte
            } 
            else if (byteValue >= 0xC0 && byteValue < 0xE0) // 2-byte events (Program Change, Channel Pressure)
            {
                // New status byte
                runningStatus = byteValue; // Update running status
                outputStream.write(runningStatus);
                outputStream.write(trackData[i++] & 0xFF); // Status data (Note, program, pressure, etc)
            } 
            else if (byteValue >= 0xF0 && byteValue <= 0xFF)
            {
                // System common messages reset running status
                if (byteValue >= 0xF0 && byteValue <= 0xF7) { runningStatus = 0; }

                outputStream.write(byteValue);
                if(byteValue == 0xFF) { outputStream.write(trackData[i++] & 0xFF); } // Type 
                
                int lengthBytes = getVarIntLength(trackData, i);
                int length = readVarInt(trackData, i);

                for(int msgByte = 0; msgByte < length + lengthBytes; msgByte++) 
                {
                    outputStream.write(trackData[i++] & 0xFF);
                }
            }
            else // Data byte without status (Note event)
            {
                if (runningStatus != 0) { outputStream.write(runningStatus); }
                outputStream.write(byteValue); // Status data (Note)
                outputStream.write(trackData[i++] & 0xFF); // Note velocity
            }
        }
        
        return outputStream.toByteArray();
    }

    private static final int readInt(byte[] data, int offset) 
    {
        return ((data[offset] & 0xFF) << 24) |
            ((data[offset + 1] & 0xFF) << 16) |
            ((data[offset + 2] & 0xFF) << 8) |
            (data[offset + 3] & 0xFF);
    }

    private static final int readShort(byte[] data, int offset) 
    {
        return ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
    }

    private static final int readVarInt(byte[] data, int offset) 
    {
        int value = 0;
        int byteRead;
        
        do 
        {
            byteRead = data[offset++] & 0xFF;
            value = (value << 7) | (byteRead & 0x7F);
        } 
        while ((byteRead & 0x80) != 0);

        return value;
    }

    private static final int getVarIntLength(byte[] data, int offset) 
    {
        int length = 0, byteRead = 0;
        do 
        {
            byteRead = data[offset++] & 0xFF;
            length++;
        } 
        while ((byteRead & 0x80) != 0);

        return length;
    }

    public static final ByteArrayInputStream patchMIDIFile(byte[] midiData) 
    {
        try { return new ByteArrayInputStream(patchMidi(midiData)); } 
        catch (IOException e) { return null; }
    }
}
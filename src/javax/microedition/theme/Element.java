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
package javax.microedition.theme;

import java.util.Hashtable;

public interface Element 
{

    static final String KIND_COLOR = "color";
    static final String KIND_FLOAT = "float";
    static final String KIND_FONT = "font";
    static final String KIND_GRAPHIC = "graphic";
    static final String KIND_GROUP = "group";
    static final String KIND_INTEGER = "integer";
    static final String KIND_SOUND = "sound";
    static final String KIND_STRING = "string";
    static final String KIND_VIDEO = "video";

    boolean equals(Object otherElement);

    Object getContent();

    String getFeature();

    String getKind();

    Hashtable<String, String> getPresentation();

    String getRole();

    int hashCode();

    String toString();
}
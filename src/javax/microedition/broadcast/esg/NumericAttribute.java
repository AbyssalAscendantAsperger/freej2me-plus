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
package javax.microedition.broadcast.esg;

public class NumericAttribute extends Attribute 
{

    public static final int FIXED_POINT_TYPE = 1;
    public static final int FLOATING_POINT_TYPE = 2;
    public static final int INTEGER_TYPE = 0;

    private final String attribute;
    private final int type;
    private final boolean signed;

    public NumericAttribute(String attribute, int type, boolean signed) 
    {
        if (attribute == null) { throw new NullPointerException("Attribute cannot be null"); }
        if (type < 1 || type > 3) { throw new IllegalArgumentException("Unrecognized type"); }

        this.attribute = attribute;
        this.type = type;
        this.signed = signed;
    }

    public boolean equals(Object obj) 
    {
        if (this == obj) { return true; }

        if (obj instanceof NumericAttribute) 
        {
            NumericAttribute other = (NumericAttribute) obj;
            return getType() == other.getType() &&
                   getName().equalsIgnoreCase(other.getName()) &&
                   isSigned() == other.isSigned();
        }
        return false;
    }

    public int getType() { return type; }

    public boolean isSigned() { return signed; }

    public String getName() { return attribute; }
}
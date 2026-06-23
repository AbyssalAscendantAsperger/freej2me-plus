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

import java.util.Date;

public class ServiceGuide 
{

    public void addListener(ServiceGuideListener listener, Query filter) throws QueryException { return; }

    public ProgramEvent[] findPrograms(Query query) throws QueryException { return null; }

    public ProgramEvent[] findPrograms(Query query, Attribute[] sortBy, int startOffset, int length) throws QueryException { return null; }

    public Service[] findServices(Query query) throws QueryException { return null; }

    public Service[] findServices(Query query, Attribute[] sortBy, int startOffset, int length) throws QueryException { return null; }

    public boolean forceUpdate() { return false; }

    public static ServiceGuide[] getAllServiceGuides() { return null; }

    public Object[] getAllUniqueValues(Attribute attribute) throws QueryException { return null; }

    public static ServiceGuide getDefaultServiceGuide() { return null; }

    public Date getLastUpdatedTime() { return null; }

    public String getServiceProvider() { return "Default Provider"; }

    public MetadataSet[] getSupportedMetadataSets() { return null; }

    public boolean isValid() { return true; }

    public void removeListener(ServiceGuideListener listener) { return; }
}
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
package org.w3c.dom;

public interface DOMImplementation 
{
    
    public Document createDocument(String namespaceURI, String qualifiedName, DocumentType doctype) throws DOMException;
    
    public DocumentType createDocumentType(String qualifiedName, String publicId, String systemId) throws DOMException;
    
    public Object getFeature(String feature, String version);
    
    public boolean hasFeature(String feature, String version);
}
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

public interface Element extends Node 
{
    
    public String getAttribute(String name);
    
    public Attr getAttributeNode(String name);
    
    public Attr getAttributeNodeNS(String namespaceURI, String localName);
    
    public String getAttributeNS(String namespaceURI, String localName);
    
    public NodeList getElementsByTagName(String name);
    
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName);
    
    public String getTagName();
    
    public boolean hasAttribute(String name);
    
    public boolean hasAttributeNS(String namespaceURI, String localName);
    
    public void removeAttribute(String name);
    
    public Attr removeAttributeNode(Attr oldAttr);
    
    public void removeAttributeNS(String namespaceURI, String localName);
    
    public void setAttribute(String name, String value);
    
    public Attr setAttributeNode(Attr newAttr);
    
    public Attr setAttributeNodeNS(Attr newAttr);
    
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value);
    
    public void setIdAttribute(String name, boolean isId);
    
    public void setIdAttributeNode(Attr idAttr, boolean isId);
    
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId);
}
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

public interface Document extends Node 
{
    
    public Node adoptNode(Node source) throws DOMException;
    
    public Attr createAttribute(String name) throws DOMException;
    
    public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException;
    
    public CDATASection createCDATASection(String data) throws DOMException;
    
    public Comment createComment(String data);
    
    public DocumentFragment createDocumentFragment();
    
    public Element createElement(String tagName) throws DOMException;
    
    public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException;
    
    public EntityReference createEntityReference(String name) throws DOMException;
    
    public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException;
    
    public Text createTextNode(String data);
    
    public DocumentType getDoctype();
    
    public Element getDocumentElement();
    
    public Element getElementById(String elementId);
    
    public NodeList getElementsByTagName(String tagname);
    
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName);
    
    public DOMImplementation getImplementation();
    
    public Node importNode(Node importedNode, boolean deep) throws DOMException;
}
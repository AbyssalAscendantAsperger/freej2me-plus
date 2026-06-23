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

public interface Node 
{
    public static final short ATTRIBUTE_NODE = 1;
    public static final short CDATA_SECTION_NODE = 2;
    public static final short COMMENT_NODE = 3;
    public static final short DOCUMENT_FRAGMENT_NODE = 4;
    public static final short DOCUMENT_NODE = 5;
    public static final short DOCUMENT_TYPE_NODE = 6;
    public static final short ELEMENT_NODE = 7;
    public static final short ENTITY_NODE = 8;
    public static final short ENTITY_REFERENCE_NODE = 9;
    public static final short NOTATION_NODE = 10;
    public static final short PROCESSING_INSTRUCTION_NODE = 11;
    public static final short TEXT_NODE = 12;

    public String getNodeName();
    
    public String getNodeValue() throws DOMException;
    
    public void setNodeValue(String nodeValue) throws DOMException;
    
    public short getNodeType();
    
    public Node getParentNode();
    
    public NodeList getChildNodes();
    
    public Node getFirstChild();
    
    public Node getLastChild();
    
    public Node getPreviousSibling();
    
    public Node getNextSibling();
    
    public NamedNodeMap getAttributes();
    
    public Document getOwnerDocument();
    
    public Node insertBefore(Node newChild, Node refChild) throws DOMException;
    
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException;
    
    public Node removeChild(Node oldChild) throws DOMException;
    
    public Node appendChild(Node newChild) throws DOMException;
    
    public boolean hasChildNodes();
    
    public Node cloneNode(boolean deep);
    
    public void normalize();
    
    public boolean isSupported(String feature, String version);
    
    public String getNamespaceURI();
    
    public String getPrefix();
    
    public void setPrefix(String prefix) throws DOMException;
    
    public String getLocalName();
    
    public boolean hasAttributes();
    
    public String getTextContent() throws DOMException;
    
    public void setTextContent(String textContent) throws DOMException;
    
    public Object getFeature(String feature, String version);
    
    public Object setUserData(String key, Object data, UserDataHandler handler);
    
    public Object getUserData(String key);
}
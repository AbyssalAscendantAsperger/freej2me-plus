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
package javax.xml.stream;

import java.io.InputStream;
import java.io.Reader;

public abstract class XMLInputFactory 
{
    
    public static final String IS_COALESCING = "javax.xml.stream.isCoalescing";
    public static final String IS_NAMESPACE_AWARE = "javax.xml.stream.isNamespaceAware";
    public static final String IS_REPLACING_ENTITY_REFERENCES = "javax.xml.stream.isReplacingEntityReferences";
    public static final String IS_SUPPORTING_EXTERNAL_ENTITIES = "javax.xml.stream.isSupportingExternalEntities";
    public static final String IS_VALIDATING = "javax.xml.stream.isValidating";
    public static final String RESOLVER = "javax.xml.stream.resolver";
    public static final String SUPPORT_DTD = "javax.xml.stream.supportDTD";

    protected XMLInputFactory() {}

    public static XMLInputFactory newInstance() throws FactoryConfigurationError { return null; }

    public abstract XMLStreamReader createXMLStreamReader(InputStream stream) throws XMLStreamException;
    
    public abstract XMLStreamReader createXMLStreamReader(InputStream stream, String encoding) throws XMLStreamException;
    
    public abstract XMLStreamReader createXMLStreamReader(Reader reader) throws XMLStreamException;
    
    public abstract Object getProperty(String name) throws IllegalArgumentException;
    
    public abstract XMLResolver getXMLResolver();
    
    public abstract boolean isPropertySupported(String name);
    
    public abstract void setProperty(String name, Object value) throws IllegalArgumentException;
    
    public abstract void setXMLResolver(XMLResolver resolver);
}
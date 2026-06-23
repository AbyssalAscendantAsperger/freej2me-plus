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
package javax.microedition.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.recompile.mobile.Mobile;

public class ContentHandlerImpl implements ContentHandler 
{

    private String id;
    private String authority;
    private String appName;
    private String version;
    private List<String> types;
    private List<String> suffixes;
    private List<String> actions;
    private List<ActionNameMap> actionNameMaps;

    public ContentHandlerImpl(String id, String authority, String appName, String version,
                                    List<String> types, List<String> suffixes, 
                                    List<String> actions, List<ActionNameMap> actionNameMaps) 
    {
        Mobile.log(Mobile.LOG_ERROR, ContentHandlerImpl.class.getPackage().getName() + "." + ContentHandlerImpl.class.getSimpleName() + ": " + "New ContentHandler");
        this.id = id;
        this.authority = authority;
        this.types = types != null ? new ArrayList<String>(types) : new ArrayList<String>();
        this.suffixes = suffixes != null ? new ArrayList<String>(suffixes) : new ArrayList<String>();
        this.actions = actions != null ? new ArrayList<String>(actions) : new ArrayList<String>();
        this.actionNameMaps = actionNameMaps != null ? new ArrayList<ActionNameMap>(actionNameMaps) : new ArrayList<ActionNameMap>();
    }

    public String getID() { return id; }

    public String getAuthority() { return authority; }

    public String getAppName() { return appName; }

    public String getVersion() { return version; }

    public int getTypeCount() { return types.size(); }

    public String getType(int index) { return types.get(index); }

    public boolean hasType(String type) 
    {
        if (type == null) { throw new NullPointerException("type cannot be null"); }
        for (String t : types) 
        {
            if (t.equalsIgnoreCase(type)) { return true; }
        }
        return false;
    }

    public int getSuffixCount() { return suffixes.size(); }

    public String getSuffix(int index) { return suffixes.get(index); }

    public boolean hasSuffix(String suffix) 
    {
        if (suffix == null) { throw new NullPointerException("suffix cannot be null"); }
        for (String s : suffixes) 
        {
            if (s.equalsIgnoreCase(suffix)) { return true; }
        }
        return false;
    }

    public int getActionCount() { return actions.size(); }

    public String getAction(int index) { return actions.get(index); }

    public boolean hasAction(String action) { return actions.contains(action); }

    public ActionNameMap getActionNameMap() 
    {
        return getActionNameMap(Locale.getDefault().toString());
    }

    public ActionNameMap getActionNameMap(String locale) 
    {
        for (ActionNameMap map : actionNameMaps) 
        {
            if (map.getLocale().equalsIgnoreCase(locale)) { return map; }
        }
        return null; // No match found
    }

    public int getActionNameMapCount() { return actionNameMaps.size(); }

    public ActionNameMap getActionNameMap(int index) { return actionNameMaps.get(index); }
}
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

import java.util.ArrayList;
import java.util.List;

public class ThemeManager 
{

    public static final String SYSTEM_DEFAULT_THEME = "SYSTEM_DEFAULT_THEME";
    private static final ThemeManager INSTANCE = new ThemeManager();
    private Theme activeTheme;
    private final List<ThemeListener> listeners = new ArrayList<ThemeListener>();

    // Private constructor to enforce singleton pattern
    private ThemeManager() {}

    public static ThemeManager getInstance() { return INSTANCE; }

    public void activateTheme(String name) throws ThemeException 
    {
        for (ThemeListener listener : listeners) { listener.themeActivated(activeTheme, null);  }
    }

    public void addThemeListener(ThemeListener listener) 
    {
        if (listener != null && !listeners.contains(listener)) { listeners.add(listener); }
    }

    public void createTheme(String themeLocator, String name) throws ThemeException, ModifyNotSupportedException 
    {
        for (ThemeListener listener : listeners) { listener.themeCreated(null); }
    }

    public Theme getActiveTheme() { return activeTheme; }

    public Theme getTheme(String name) throws ThemeNotFoundException { return null; }

    public String[] getThemes() { return null; }

    public void removeTheme(String name) throws ThemeException 
    {
        for (ThemeListener listener : listeners) { listener.themeRemoved(name, null); }
    }

    public void removeThemeListener(ThemeListener listener) { listeners.remove(listener); }
}
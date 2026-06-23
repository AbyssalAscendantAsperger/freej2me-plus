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
package javax.microedition.global;

import java.text.Collator;
import java.util.Locale;

public final class StringComparator 
{
    public static final int IDENTICAL = 15;
    public static final int LEVEL1 = 1;
    public static final int LEVEL2 = 2;
    public static final int LEVEL3 = 3;

    private String locale;
    private int level;

    public StringComparator() 
    {
        this.locale = System.getProperty("microedition.locale");
        this.level = LEVEL1;
        validateLocale();
    }

    public StringComparator(String locale) 
    {
        this.locale = locale;
        this.level = LEVEL1;
        validateLocale();
    }

    public StringComparator(String locale, int level) 
    {
        this.locale = locale;
        this.level = level;
        validateLocale();
        validateLevel();
    }

    public int compare(String s1, String s2) 
    {
        if (s1 == null || s2 == null) { throw new NullPointerException("Strings cannot be null"); }

        Collator collator = Collator.getInstance(locale != null ? Locale.forLanguageTag(locale) : Locale.getDefault());
        
        switch (level) 
        {
            case LEVEL1:
                collator.setStrength(Collator.PRIMARY);
                break;
            case LEVEL2:
                collator.setStrength(Collator.SECONDARY);
                break;
            case LEVEL3:
                collator.setStrength(Collator.TERTIARY);
                break;
            case IDENTICAL:
                collator.setStrength(Collator.IDENTICAL);
                break;
            default:
                throw new IllegalArgumentException("Invalid comparison level: " + level);
        }

        return collator.compare(s1, s2);
    }

    public boolean equals(String s1, String s2) { return compare(s1, s2) == 0; }

    public int getLevel() { return level; }

    public String getLocale() { return locale; }

    public static String[] getSupportedLocales() { return Formatter.getSupportedLocales(); }

    private void validateLocale() 
    {
        if (locale != null && !locale.isEmpty() && !isLocaleSupported(locale)) { throw new UnsupportedLocaleException("Unsupported locale: " + locale); }
    }

    private void validateLevel() 
    {
        if (level < LEVEL1 || level > IDENTICAL) { throw new IllegalArgumentException("Invalid comparison level: " + level); }
    }

    private boolean isLocaleSupported(String locale) { return Formatter.isLocaleSupported(locale); }
}
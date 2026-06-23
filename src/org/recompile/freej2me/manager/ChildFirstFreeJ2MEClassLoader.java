package org.recompile.freej2me.manager;

import java.net.URL;
import java.net.URLClassLoader;

/*
	Child-first ClassLoader that isolates FreeJ2ME core classes per session.
	Packages matching PARENT_FIRST are delegated to the parent loader so that
	shared API classes (session interfaces, manager types) stay compatible
	across classloader boundaries.
*/
public class ChildFirstFreeJ2MEClassLoader extends URLClassLoader
{
	private static final String[] PARENT_FIRST_PACKAGES = {
		"java.",
		"javax.sound.",
		"org.recompile.freej2me.session.",
		"org.recompile.freej2me.manager.api.",
		"org.recompile.freej2me.manager."
	};

	public ChildFirstFreeJ2MEClassLoader(URL[] urls, ClassLoader parent)
	{
		super(urls, parent);
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		Class<?> c = findLoadedClass(name);
		if(c != null)
		{
			if(resolve) { resolveClass(c); }
			return c;
		}

		boolean parentFirst = false;
		for(String pkg : PARENT_FIRST_PACKAGES)
		{
			if(name.startsWith(pkg)) { parentFirst = true; break; }
		}

		if(parentFirst)
		{
			try { c = getParent().loadClass(name); }
			catch(ClassNotFoundException e) { c = findClass(name); }
		}
		else
		{
			try { c = findClass(name); }
			catch(ClassNotFoundException e) { c = getParent().loadClass(name); }
		}

		if(resolve) { resolveClass(c); }
		return c;
	}
}

package org.recompile.freej2me.transport;

import java.util.HashMap;
import java.util.Map;

/*
	Ultra-simple JSON parser for flat objects only.
	Supports: strings, numbers, booleans.
	Example: {"width":240,"height":320,"phone":0,"jar":"game.jar"}
*/
public class SimpleJSON
{
	public static Map<String, String> parse(String json)
	{
		Map<String, String> map = new HashMap<String, String>();
		if(json == null || json.isEmpty()) { return map; }
		json = json.trim();
		if(json.startsWith("{")) { json = json.substring(1); }
		if(json.endsWith("}")) { json = json.substring(0, json.length() - 1); }
		json = json.trim();
		if(json.isEmpty()) { return map; }

		int i = 0;
		while(i < json.length())
		{
			// Skip whitespace and commas
			while(i < json.length() && (json.charAt(i) == ' ' || json.charAt(i) == '\t' || json.charAt(i) == '\n' || json.charAt(i) == '\r' || json.charAt(i) == ',')) { i++; }
			if(i >= json.length()) { break; }

			// Parse key
			if(json.charAt(i) != '"') { break; }
			i++;
			StringBuilder key = new StringBuilder();
			while(i < json.length() && json.charAt(i) != '"')
			{
				if(json.charAt(i) == '\\' && i + 1 < json.length()) { i++; }
				key.append(json.charAt(i));
				i++;
			}
			if(i < json.length() && json.charAt(i) == '"') { i++; }

			// Skip whitespace and colon
			while(i < json.length() && (json.charAt(i) == ' ' || json.charAt(i) == '\t' || json.charAt(i) == '\n' || json.charAt(i) == '\r')) { i++; }
			if(i >= json.length() || json.charAt(i) != ':') { break; }
			i++;
			while(i < json.length() && (json.charAt(i) == ' ' || json.charAt(i) == '\t' || json.charAt(i) == '\n' || json.charAt(i) == '\r')) { i++; }

			// Parse value
			StringBuilder value = new StringBuilder();
			if(i < json.length() && json.charAt(i) == '"')
			{
				i++;
				while(i < json.length() && json.charAt(i) != '"')
				{
					if(json.charAt(i) == '\\' && i + 1 < json.length()) { i++; }
					value.append(json.charAt(i));
					i++;
				}
				if(i < json.length() && json.charAt(i) == '"') { i++; }
			}
			else
			{
				while(i < json.length() && json.charAt(i) != ',' && json.charAt(i) != '}')
				{
					value.append(json.charAt(i));
					i++;
				}
			}
			map.put(key.toString().trim(), value.toString().trim());
		}
		return map;
	}
}

package input;

import java.util.ArrayList;

import util.Dfl;

public class HardTypedArg extends Arg
{
	String typeName;
	ArgTag[] tags;
	
	public HardTypedArg(String raw)
	{
		super(raw);	
		
		int firstBracket = raw.indexOf("[");
		int lastBracket = raw.lastIndexOf("]");
		
		typeName = raw.substring(0, firstBracket);
		String theRest = raw.substring(firstBracket + 1, lastBracket).trim();

		ArrayList<ArgTag> tags = new ArrayList<ArgTag>();
		boolean onName = true;
		String curName = "";
		String curValue = "";
		int brackets = 0;
		boolean inQuotes = false;
		
		for (int i = 0; i < theRest.length(); i++)
		{	
			char curChar = theRest.charAt(i);
			char charAfter = ';';
			
			if (i + 1 < theRest.length())
				charAfter = theRest.charAt(i + 1);
			
			else
				charAfter = '\0';
			
			if (onName)
			{
				if (curChar == ';')
				{
					curName = "";
				}
					
				else if (curChar == '=')
				{
					curName = curName.trim();
					onName = false;
				}
				
				else
				{
					curName += curChar;
				}
			}
			
			else if (inQuotes)
			{
				if (curChar == '\'' && charAfter == '\'')
				{
					curValue += "'";
					i++;
				}
				
				else
				{
					curValue += curChar;
					
					if (curChar == '\'')
					{	
						inQuotes = false;
					}
				}
			}
			
			else
			{
				if (curChar == ';' && brackets == 0)
				{
					curValue = curValue.trim();
					
					if (curName.equals(""))
						Input.setError("TAG PARSE ERROR: Empty tag name.", "Put a tag name before the equals sign.", this);
					
					if (curValue.equals(""))
						Input.setError("TAG PARSE ERROR: Empty tag value.", "Put a tag value after the equals sign.", this);
					
					tags.add(new ArgTag(curName, getArg(curValue)));
					curName = "";
					curValue = "";
					onName = true;
				}
				
				else
				{
					curValue += curChar;
					
					if (curChar == '\'')
					{
						inQuotes = true;
					}
					
					else if (curChar == '[')
					{
						brackets++;
					}
					
					else if (curChar == ']')
					{
						brackets--;
					}
				}
			}
		}
		
		curValue = curValue.trim();
		
		if (curValue.equals("") && !onName)
			Input.setError("TAG PARSE ERROR: Empty tag value.", "Put a tag value after the equals sign.", this);
		
		if (!onName)
			tags.add(new ArgTag(curName, getArg(curValue)));
		
		if (brackets > 0)
		{
			Input.setError("TAG PARSE ERROR: Too many opening brackets within tag value.", "Note that, because of how input is parsed, the error may not be in the highlighted area.", this);
		}
		
		else if (brackets < 0)
		{
			Input.setError("TAG PARSE ERROR: Too many closing brackets within tag value.", "Note that, because of how input is parsed, the error may not be in the highlighted area.", this);
		}
		
		else if (inQuotes)
		{
			Input.setError("TAG PARSE ERROR: Missing closing quote mark within tag value.", "Note that, because of how input is parsed, the error may not be in the highlighted area.", this);
		}
		
		this.tags = tags.toArray(new ArgTag[0]);
	}

	@Override
	public String getFocus()
	{
		return typeName;
	}

	@Override
	public String toString()
	{
		String out = typeName + "[";
		
		for (int i = 0; i < tags.length; i++)
		{
			out += tags[i].getName() + " = " + tags[i].getValue().toString() + "; ";
			
			if (i == tags.length - 1)
				out = Dfl.cutString(out, 1);
		}
		
		return out + "]";
	}

	public boolean hasTag(String tag)
	{
		for (ArgTag t : tags)
			if (t.getName().equals(tag))
				return true;
		
		return false;
	}

	public ArgTag getTag(String tag)
	{
		for (ArgTag t : tags)
			if (t.getName().equals(tag))
				return t;
		
		return null;
	}
	
	public ArgTag[] getTags()
	{
		return tags;
	}
}

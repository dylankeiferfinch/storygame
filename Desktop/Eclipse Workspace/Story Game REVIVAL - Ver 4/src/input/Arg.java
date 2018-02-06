package input;

import java.util.ArrayList;

public abstract class Arg
{
	protected String raw;
	
	public Arg(String raw)
	{
		this.raw = raw;
	}
	
	public String getRaw()
	{
		return raw;
	}
	
	public static Arg getArg(String raw)
	{
		raw = raw.trim();
		
		if (raw.length() < 1)
			return new VariableArg(raw);
		
		char lastChar = raw.charAt(raw.length() - 1);
		char firstChar = raw.charAt(0);
		
		if (lastChar == ']' && raw.length() > 2)
			return new HardTypedArg(raw);
		
		else if (firstChar == '@' && raw.length() > 1)
			return new StaticArg(raw);
		
		else if (lastChar == '\'' && firstChar == '\'' && raw.length() > 2)
			return new SoftTypedArg(raw);
		
		else if (firstChar == '$' && raw.length() > 1)
			return new SeperatorArg(raw);
		
		return new VariableArg(raw);
	}
	
	public static Arg[] inputToArgs(String input)
	{
		if (input == null || input.equals(""))
			return new Arg[0];
		
		input = input.trim();
		
		if (input == null || input.length() == 0)
		{
			Input.setError("EMPTY INPUT ERROR: No input to process.", "Type some input in before hitting ENTER.");
			return null;
		}
		
		ArrayList<Arg> args = new ArrayList<Arg>();
		String curArgRaw = "";
		boolean inQuotes = false;
		int brackets = 0;
		
		for (int i = 0; i < input.length(); i++)
		{
			char curChar = input.charAt(i);
			char charAfter = ' ';
			
			if (i + 1 < input.length())
				charAfter = input.charAt(i + 1);
			
			if (inQuotes && brackets == 0)
			{
				if (curChar == '\'' && charAfter == '\'')
				{
					curArgRaw += "'";
					i++;
				}
				
				else
				{
					curArgRaw += curChar;
					
					if (curChar == '\'')
					{	
						inQuotes = false;
					}
				}
			}
			
			else if (inQuotes)
			{
				curArgRaw += curChar;
				
				if (curChar == '\'')
				{	
					if (charAfter == '\'')
					{
						curArgRaw += '\'';
						i++;
					}
					
					else
						inQuotes = false;
				}
				
				
			}
			
			else
			{
				if (curChar == ' ' && brackets == 0)
				{
					args.add(getArg(curArgRaw));
					curArgRaw = "";
				}
				
				curArgRaw += curChar;
				
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
		
		if (!curArgRaw.equals(""))
			args.add(getArg(curArgRaw));
		
		if (brackets > 0)
		{
			Input.setError("INPUT PARSE ERROR: Too many opening brackets.", "Note that, because of how input is parsed, the error may not be in the highlighted area.", args.get(args.size() - 1));
		}
		
		else if (brackets < 0)
		{
			Input.setError("INPUT PARSE ERROR: Too many closing brackets.", "Note that, because of how input is parsed, the error may not be in the highlighted area.", args.get(args.size() - 1));
		}
		
		else if (inQuotes)
		{
			Input.setError("INPUT PARSE ERROR: Missing closing quote mark.", "Note that, because of how input is parsed, the error may not be in the highlighted area.", args.get(args.size() - 1));
		}
		
		return args.toArray(new Arg[0]);
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Arg && ((Arg) obj).getFocus().equals(this.getFocus()))
			return true;
		
		return false;
	}
	
	public abstract String toString();
	public abstract String getFocus();
}

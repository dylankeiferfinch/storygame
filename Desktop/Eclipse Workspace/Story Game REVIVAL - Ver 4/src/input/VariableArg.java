package input;

public class VariableArg extends Arg
{
	public VariableArg(String raw)
	{
		super(raw);
	}

	@Override
	public String getFocus()
	{
		return raw;
	}

	@Override
	public String toString()
	{
		if (raw.contains(" "))
			return "'" + raw + "'";
		
		else
			return raw;
	}
}

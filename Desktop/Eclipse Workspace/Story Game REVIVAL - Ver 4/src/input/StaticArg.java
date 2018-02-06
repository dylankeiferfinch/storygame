package input;

public class StaticArg extends Arg
{
	private String staticName;
	
	public StaticArg(String raw)
	{
		super(raw);
		
		staticName = raw.substring(1);
	}

	@Override
	public String getFocus()
	{
		return staticName;
	}

	@Override
	public String toString()
	{
		return raw;
	}
}

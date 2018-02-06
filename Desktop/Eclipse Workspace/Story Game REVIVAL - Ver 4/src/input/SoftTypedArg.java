package input;

public class SoftTypedArg extends Arg
{
	private String focus;
	
	public SoftTypedArg(String raw)
	{
		super(raw);
		
		focus = raw.substring(1, raw.length() - 1);
	}

	@Override
	public String getFocus()
	{
		return focus;
	}

	@Override
	public String toString()
	{
		return raw;
	}
}

package input;

public class SeperatorArg extends Arg
{
	private String focus;
	
	/**
	 * SeperatorArgs should have the form: $arg
	 * 
	 * @param raw
	 */
	public SeperatorArg(String raw)
	{
		super(raw);
		
		focus = raw.substring(1);
	}

	@Override
	public String toString()
	{
		return raw;
	}

	@Override
	public String getFocus()
	{
		return focus;
	}

}

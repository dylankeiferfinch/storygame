package processes;

import base.Output;
import process_stuff.Function;

public class SaveFunc extends Function
{
	public SaveFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		return Output.genGenericError("FUNCTION DEFINITION ERROR: This functionality is not yet implemented.");
	}
}

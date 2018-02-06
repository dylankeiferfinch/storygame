package processes;

import base.GameBase;
import base.Output;
import process_stuff.Function;

public class LookFunc extends Function
{
	public LookFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		GameBase.look();
		return Output.genGenericOutput("You look around.");
	}
}

package processes;

import base.GameBase;
import base.Location;
import base.Output;
import process_stuff.Function;

public class SkipFunc extends Function
{
	public SkipFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute()
	{
		GameBase.setLocation(Location.ROOM);
		return Output.genGenericOutput("Let the journey begin...!");
	}
}

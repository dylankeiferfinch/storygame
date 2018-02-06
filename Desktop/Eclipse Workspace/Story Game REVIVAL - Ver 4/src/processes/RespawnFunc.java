package processes;

import base.GameBase;
import base.Location;
import base.Output;
import process_stuff.Function;

public class RespawnFunc extends Function
{
	public RespawnFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		GameBase.setLocation(Location.ROOM);
		return Output.genGenericOutput("You respanwed.");
	}
}

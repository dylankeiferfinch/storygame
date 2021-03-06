package processes;

import base.GameBase;
import base.Location;
import base.Output;
import process_stuff.Function;

public class OnwardFunc extends Function
{
	public OnwardFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		GameBase.setLocation(Location.ROOM);
		return Output.genGenericOutput("You return to your adventure.");
	}
}

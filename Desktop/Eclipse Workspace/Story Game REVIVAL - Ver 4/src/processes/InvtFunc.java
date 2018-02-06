package processes;

import base.GameBase;
import base.Location;
import base.Output;
import process_stuff.Function;

public class InvtFunc extends Function
{
	public InvtFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public static Output execute()
	{
		GameBase.setLocation(Location.INVENTORY);
		
		return Output.genGenericOutput("You look into your big pockets at all the stuff you've collected.");
	}
}

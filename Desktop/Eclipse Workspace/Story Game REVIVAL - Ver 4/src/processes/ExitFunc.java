package processes;

import base.GameBase;
import base.Location;
import base.Output;
import process_stuff.Function;

public class ExitFunc extends Function
{
	public ExitFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public static Output execute()
	{
		Location oldLoc = GameBase.getLocation();
		
		GameBase.setLocation(Location.ROOM);
		return Output.genGenericOutput(oldLoc == Location.INVENTORY ? "You exit your inventory." : "Closing the manual....");
	}
}

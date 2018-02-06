package processes;

import base.GameBase;
import base.Location;
import base.Output;
import process_stuff.Function;

public class StartFunc extends Function
{
	public StartFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		GameBase.setLocation(Location.TUTORIAL);
		return Output.genGenericOutput("Let the journey begin... right after this fun and educational tutorial!");
	}
}

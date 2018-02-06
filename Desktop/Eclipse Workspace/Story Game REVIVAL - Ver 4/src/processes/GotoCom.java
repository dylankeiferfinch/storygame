package processes;

import base.GameBase;
import base.Output;
import params.LocObj;
import process_stuff.Command;

public class GotoCom extends Command
{
	public GotoCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(LocObj.Type loc)
	{
		GameBase.setLocation(loc.getValue());
		return Output.genGenericOutput("Location set to " + loc.getValue() +".");
	}
}

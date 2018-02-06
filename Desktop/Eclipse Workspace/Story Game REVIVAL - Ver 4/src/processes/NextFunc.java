package processes;

import base.GameBase;
import base.Location;
import base.Output;
import base.Tutorial;
import process_stuff.Function;

public class NextFunc extends Function
{
	public NextFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute()
	{
		Tutorial.nextPage();
		
		if (GameBase.getLocation() == Location.ROOM)
			return Output.genGenericOutput("Congrats! You've finished the tutorial.\n\nLet the journey begin!");
		
		else
			return Output.genGenericOutput("Moving on....");
	}
}

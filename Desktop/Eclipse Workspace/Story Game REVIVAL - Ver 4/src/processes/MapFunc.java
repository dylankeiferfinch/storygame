package processes;

import base.Output;
import map.GameMap;
import process_stuff.Function;

public class MapFunc extends Function
{
	public MapFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		return Output.genGenericOutput("The current map:\n\n" + GameMap.getMapAsString() + "\nKey:\n@ - Your current location."
				+ "\nO - A room.\n(lines) - Two-way passages.\n(arrows points) - One-way passages.");
	}
}

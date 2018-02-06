package processes;

import base.GameBase;
import base.Location;
import base.Output;
import manual.Manual;
import process_stuff.Function;

public class ManualFunc extends Function
{
	public ManualFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		Manual.startManual();
		GameBase.setLocation(Location.MANUAL);
		return Output.genGenericOutput("Openning the manual....\n\nUse the \"open <folder name>\" and \"open <page name>\" functions to navigate the manual. For examaple, to access the \"Manual Info\" page, type \"open Manual Info\".");
	}
}

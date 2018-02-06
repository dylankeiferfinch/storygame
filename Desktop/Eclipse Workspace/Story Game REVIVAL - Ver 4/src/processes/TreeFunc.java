package processes;

import base.Output;
import manual.Manual;
import process_stuff.Function;

public class TreeFunc extends Function
{
	public TreeFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute()
	{
		return Output.genGenericOutput("Tree represntation of pages and folders connected to here:\n\n" + Manual.tree());
	}
}

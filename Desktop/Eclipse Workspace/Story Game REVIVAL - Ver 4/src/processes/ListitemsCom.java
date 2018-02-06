package processes;

import base.Output;
import hero.Item;
import process_stuff.Command;

public class ListitemsCom extends Command
{
	public ListitemsCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		return Output.genCommandOutput(Item.getItemsAsString());
	}
}

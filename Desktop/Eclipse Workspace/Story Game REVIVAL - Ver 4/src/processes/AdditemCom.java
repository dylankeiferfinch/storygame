package processes;

import base.Output;
import hero.Item;
import params.InvtItemObj;
import params.NewItemObj;
import process_stuff.Command;

public class AdditemCom extends Command
{
	public AdditemCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(NewItemObj.Type arg0)
	{
		Item.addUserItem(arg0.getValue());
		
		return Output.genCommandOutput("Added new item.\n\nName: " + arg0.getValue().getName() + "\nId: " + arg0.getValue().getId() + "\nDescription: " + arg0.getValue().getDesc());
	}
}

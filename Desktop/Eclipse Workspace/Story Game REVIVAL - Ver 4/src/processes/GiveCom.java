package processes;

import base.Output;
import hero.Invt;
import hero.Item;
import params.AmtObj;
import params.ItemIdObj;
import params.ItemObj;
import params.NewItemObj;
import params.InvtItemObj;
import process_stuff.Command;

public class GiveCom extends Command
{
	public GiveCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public static Output execute(ItemObj.Type arg0)
	{
		Invt.addItem(arg0.getValue());
		
		return getOutput(arg0.getValue(), 1);
	}
	
	public static Output execute(ItemObj.Type arg0, AmtObj.Type amt)
	{
		Invt.addItem(arg0.getValue(), amt.getValue());
		
		return getOutput(arg0.getValue(), amt.getValue());
	}
	
	public static Output execute(NewItemObj.Type arg0)
	{
		Invt.addItem(arg0.getValue());
		
		return getOutput(arg0.getValue(), 1);
	}
	
	public static Output execute(NewItemObj.Type arg0, AmtObj.Type amt)
	{
		Invt.addItem(arg0.getValue(), amt.getValue());
		
		return getOutput(arg0.getValue(), amt.getValue());
	}
	
	private static Output getOutput(Item i, int amt)
	{
		return Output.genGenericOutput("Added " + amt + " of item \"" + i.getName() + "\" to the inventory.");
	}
}

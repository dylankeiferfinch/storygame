package processes;

import base.Output;
import hero.Item;
import params.AmtObj;
import params.ForStatic;
import params.InvtItemObj;
import params.ItemsStatic;
import params.StrObj;
import params.ThenStatic;
import process_stuff.Command;

public class EchoCom extends Command
{
	public EchoCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(StrObj.Type arg0)
	{
		return Output.genGenericOutput(arg0.getValue());
	}
	
	public Output execute(StrObj.Type arg0, AmtObj.Type arg1)
	{
		String out = "";
		
		for (int i = 0; i < arg1.getValue(); i++)
			out += arg0.getValue() + "\n";
		
		return Output.genGenericOutput(out);
	}
	
	public Output execute(InvtItemObj.Type arg0)
	{
		return Output.genGenericOutput(arg0.getValue().toString());
	}
	
	public Output execute(ItemsStatic.Type arg0)
	{
		return Output.genGenericOutput(Item.getItemsAsString());
	}
	
	public Output execute(StrObj.Type str, ForStatic.Type x, AmtObj.Type amt)
	{
		return this.execute(str, amt);
	}
	
	//<str> <+for> <amt> <str> <+then> <str>
	public Output execute(StrObj.Type str1, ForStatic.Type x1, AmtObj.Type amt1, ThenStatic.Type x2, StrObj.Type str2, ForStatic.Type x3, AmtObj.Type amt2)
	{
		String out = "";
		
		for (int i = 0; i < amt1.getValue(); i++)
			out += str1.getValue() + "\n";
		
		out += "\n";
		
		for (int i = 0; i < amt2.getValue(); i++)
			out += str2.getValue() + "\n";
		
		return Output.genGenericOutput(out);
	}
}

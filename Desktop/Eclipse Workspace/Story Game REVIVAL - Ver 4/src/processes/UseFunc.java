package processes;

import base.Output;
import params.InteractableObj;
import params.InvtItemObj;
import params.OnStatic;
import process_stuff.Function;

public class UseFunc extends Function
{
	public UseFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(InvtItemObj.Type item, OnStatic.Type on, InteractableObj.Type thing)
	{
		return Output.genGenericOutput(thing.getValue().use(item.getValue()));
	}
}

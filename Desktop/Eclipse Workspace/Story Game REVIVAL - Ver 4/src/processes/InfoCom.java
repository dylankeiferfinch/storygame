package processes;

import base.Output;
import param_stuff.ParamTag;
import params.TypeObj;
import process_stuff.Command;
import util.Dfl;

public class InfoCom extends Command
{
	public InfoCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(TypeObj.Type type)
	{
		return Output.genGenericOutput("Information on type: " + type.getValue().getId() + "\nThis type is " + type.getValue().getDesc() + ".\nLong name: " + type.getValue().getName() + "\nTags: " + toString(type.getValue().getTags()));
	}
	
	private String toString(ParamTag[] tags)
	{
		String out = "";
		
		for (ParamTag t : tags)
			out += t.getName() + " (Type: " + t.getType().getId() + "), ";
		
		return Dfl.cutString(out, 2);
	}
}

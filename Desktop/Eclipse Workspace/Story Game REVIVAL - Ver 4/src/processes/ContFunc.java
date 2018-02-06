package processes;

import base.Output;
import params.StrObj;
import process_stuff.Function;

public class ContFunc extends Function
{
	public ContFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		return Output.genGenericOutput("");
	}
}

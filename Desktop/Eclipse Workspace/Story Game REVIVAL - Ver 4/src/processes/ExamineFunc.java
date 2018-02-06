package processes;

import base.Output;
import params.ExaminableObj;
import process_stuff.Function;

public class ExamineFunc extends Function
{
	public ExamineFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(ExaminableObj.Type arg)
	{
		return Output.genGenericOutput(arg.getValue().examine());
	}
	
}

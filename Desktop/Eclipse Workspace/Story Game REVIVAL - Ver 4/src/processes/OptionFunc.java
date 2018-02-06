package processes;

import base.Output;
import map.Conversation;
import params.DialogOptionObj;
import process_stuff.Function;

public class OptionFunc extends Function
{
	public OptionFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(DialogOptionObj.Type arg0)
	{
		Conversation.converse(arg0.getValue());
		return Output.genGenericOutput(Conversation.getResponse());
	}
}

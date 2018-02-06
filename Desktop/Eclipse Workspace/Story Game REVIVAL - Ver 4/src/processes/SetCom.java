package processes;

import base.GameRule;
import base.Output;
import params.BoolObj;
import params.BooleanGameRuleObj;
import process_stuff.Command;

public class SetCom extends Command
{
	public SetCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(BooleanGameRuleObj.Type rule, BoolObj.Type value)
	{
		rule.getValue().setValue(value.getValue());
		return Output.genGenericOutput("GameRule \"" + rule.getValue().getName() + "\" has been set to " + (value.getValue() ? "TRUE" : "FALSE") + ".\n\n" + rule.getValue().getDesc());
	}
	
	public Output execute(BooleanGameRuleObj.Type rule)
	{
		return Output.genGenericOutput("GameRule \"" + rule.getValue().getName() + "\" is currently set to " + (rule.getValue().getValue() ? "TRUE" : "FALSE") + ".\n\n" + rule.getValue().getDesc());
	}
	
	public Output execute()
	{
		return Output.genGenericOutput("Current settings:\n\n" + GameRule.getGameRulesAsString());
	}
}

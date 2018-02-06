package processes;

import base.GameBase;
import base.Output;
import param_stuff.ParamSet;
import params.FuncObj;
import process_stuff.Function;

public class HelpFunc extends Function
{
	public HelpFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute()
	{
		String out = "GENERAL HELP";
		
		out += "\n\nYou are currently in the location: " + GameBase.getLocation().toString();
		out += "\n\nLIST OF FUNCTIONS";
		
		for (Function f : Function.getFunctions())
		{
			if (f.canUseInLoc(GameBase.getLocation()))
			{
				out += "\n\nThe \"" + f.getName() + "\" function:";
				
				for (ParamSet s : f.getParamSetsForLoc(GameBase.getLocation()))
					out += "\n > " + f.getName() + " " + s.getCorrectUsage() + " - " + s.getDesc();
			}
		}
		
		out += "\n\nFURTHER HELP\n\nFor further help with any function, use the function: \"help <function name>\"" +
			   "\n\nFor further help with a specific game mechanic or concept, use this game's manual, accessible by typing \"manual\".";
		
		return Output.genGenericOutput(out);
	}
	
	public Output execute(FuncObj.Type func)
	{
		String out = "HELP FOR FUNCTION: " + func.getValue().getName();
		out += "\n\nValid ways to use this function:";
		
		for (ParamSet s : func.getValue().getArgSets())
			out += "\n > " + func.getValue().getName() + " " + s.getCorrectUsage() + " - " + s.getDesc();
		
		out += "\n\n" + (func.getValue().canUseInLoc(GameBase.getLocation()) ? "You can use this function in this location." : "You CANNOT use this function in this location.");
		
		return Output.genGenericOutput(out);
	}
}

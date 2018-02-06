package processes;

import base.GameBase;
import base.Output;
import param_stuff.ParamSet;
import params.ComObj;
import process_stuff.Command;

public class HelpCom extends Command
{
	public HelpCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute()
	{
		String out = "LIST OF COMMANDS";
		
		for (Command c : Command.getCommands())
		{
			if (c.canUseInLoc(GameBase.getLocation()))
			{
				out += "\n\nThe \"" + c.getName() + "\" command:";
				
				for (ParamSet s : c.getArgSets())
					out += "\n > " + c.getName() + " " + s.getCorrectUsage() + " - " + s.getDesc();
			}
		}
		
		out += "\n\nFor further help with any commands, use the command: \"/help <command name>\"";
		
		return Output.genGenericOutput(out);
	}
	
	public Output execute(ComObj.Type com)
	{
		String out = "HELP FOR COMMAND: " + com.getValue().getName();
		out += "\n\nValid ways to use this command:";
		
		for (ParamSet s : com.getValue().getArgSets())
			out += "\n > " + com.getValue().getName() + " " + s.getCorrectUsage() + " - " + s.getDesc();
		
		out += "\n\n" + (com.getValue().canUseInLoc(GameBase.getLocation()) ? "You can use this command in this location." : "You CANNOT use this command in this location.");
		
		return Output.genGenericOutput(out);
	}
}

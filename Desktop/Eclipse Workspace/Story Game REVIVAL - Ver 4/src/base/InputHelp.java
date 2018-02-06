package base;

import java.util.ArrayList;
import input.Arg;
import input.Input;
import input.InputError;
import input.Log;
import param_stuff.ObjParam;
import param_stuff.Param;
import param_stuff.ParamSetEval;
import param_stuff.ParamTag;
import param_stuff.StaticParam;
import process_stuff.Command;
import process_stuff.Function;
import process_stuff.Process;

public class InputHelp
{	
	private static int rotation;
	private static String lastStr;
	private static HelpSet[] optionsList;
	private static String extra;
	private static ParamSetEval[] sets;
	private static Param curArg;
	
	public static void initInputHelp()
	{
		rotation = -1;
		lastStr = null;
		optionsList = new HelpSet[0];
		extra = null;
		curArg = null;
		sets = null;
	}
	
	public static void resetRotation()
	{
		rotation = -1;
		lastStr = null;
	}
	
	public static boolean isInputFinalized()
	{
		return rotation == -1;
	}
	
	private static String get(String str, int changeBy)
	{
		Log.begin("completing: " + (str.equals("") ? "(No text.)" : "\"" + str + "\""));
		
		if (!str.equals(lastStr))
		{
			Log.log("Text has changed. Finding new options list.");
			
			lastStr = str;
			optionsList = getOptionsList(str);
			
			if (changeBy == 1)
				rotation = -1;
			
			else
			{
				if (optionsList.length > 1 && extra != null)
					rotation = optionsList.length - 1;
					
				else
					rotation = optionsList.length;
			}
			
		}
		
		if (optionsList.length == 0)
		{
			Log.log("Could not complete the text.");
			Log.end("completing: " + (str.equals("")? "(No text.)" : "\"" + str + "\""));
			return "";
		}
		
		Log.log("Found options list: " + Log.toString(optionsList));
		
		int oldRotation = rotation;
		rotation = (rotation + changeBy) % optionsList.length;
		
		if (rotation < 0)
			rotation = optionsList.length - 1;
		
		Log.log("Chnaged rotation number from " + oldRotation + " to " + rotation + ".");
		
		Log.log("Found completion text: " + optionsList[rotation]);
		Log.end("completing: " + (str.equals("") ? "(No text.)" : "\"" + str + "\""));
		
		return optionsList[rotation].getStirng();
	}
	
	public static String getNext(String str, String alreadyThere)
	{
		extra = alreadyThere;
		return get(str, 1);
	}
	
	public static String getPrev(String str, String alreadyThere)
	{
		extra = alreadyThere;
		return get(str, -1);
	}
	
	private static HelpSet[] getOptionsList(String str)
	{
		ArrayList<HelpSet> strs = new ArrayList<HelpSet>();
		curArg = null;
		sets = null;
		
		if (str.equals(""))
		{
			for (Function f : Function.getFunctions())
				if (f.canUseInLoc(GameBase.getLocation()))
					strs.add(new HelpSet(f.getName() + (f.hasNoArgs() ? "" : " "), null));
			
			strs.add(new HelpSet("/", null));
		}
		
		else if (str.equals("/"))
		{
			for (Command c : Command.getCommands())
				if (c.canUseInLoc(GameBase.getLocation()))
					strs.add(new HelpSet(c.getName().substring(1) + (c.hasNoArgs() ? "" : " "), null));
		}
		
		else
		{
			Input.doInput = false;
			Input item = new Input(str);
			Input.doInput = true;
			sets = item.getEvals();
			
			Arg[] parts = item.getParts();
			
			for (int i = 0; Process.getProcess(item.getFunctionName()) != null && i < item.getEvals().length; i++)
			{
				if (errorBefore(parts, item.getEvals()[i].getFirstError()))
					continue;

				Param[] set = item.getEvals()[i].getArgSet().getArgs();
				String[] reted = new String[0];
				
				if (parts.length < set.length)
					reted = set[parts.length].defaultValues();
				
				for (String s : reted)
					strs.add(new HelpSet((str.charAt(str.length() - 1) == ' ' ? "" : " ") + s + (set.length > parts.length + 1 ? " " : ""), set[parts.length]));
			}
			
			for (int i = 0; i < strs.size() - 1; i++)
			{
				HelpSet helper = strs.get(i);
				String s = strs.get(i).getStirng();
				
				for (int j = i + 1; j < strs.size(); j++)
				{
					String other = strs.get(j).getStirng();
					
					if (s.trim().equals(other.trim()))
					{
						if (s.charAt(s.length() - 1) != ' ' || (other.charAt(other.length() - 1) == ' ' && s.charAt(s.length() - 1) == ' '))
						{
							strs.remove(helper);
							i--;
						}
					}
				}
			}
		}

		if (extra != null)
			strs.add(new HelpSet(extra, null));
		
		if (strs.size() == 0)
			strs.add(new HelpSet("", null));
		
		return strs.toArray(new HelpSet[0]);
	}
	
	private static boolean errorBefore(Arg[] parts, InputError err)
	{
		for (Arg p : parts)
			if (err != null && err.getFailedPart() == p)
				return true;
		
		return false;
	}
	
	public static String extend()
	{
		if (lastStr == null || optionsList[rotation].getType() == null)
			return null;
		
		if (optionsList[rotation].getType() instanceof StaticParam)
			return "@" + optionsList[rotation].getType().getId();
		
		ObjParam type = (ObjParam) optionsList[rotation].getType();
		ParamTag[] tags = type.getTags();

		String out = type.getId() + "[";
		
		if (tags.length == 1)
			return out + "?]" + (optionsList[rotation].getStirng().charAt(optionsList[rotation].getStirng().length() - 1) == ' ' ? " " : "");
					
		for (int i = 0; i < tags.length; i++)
		{
			if (!tags[i].getName().equals("this"))
				out += tags[i].getName() + " = ?; ";
		}
		
		out = out.substring(0, out.length() - 1);

		return out + "]" + (optionsList[rotation].getStirng().charAt(optionsList[rotation].getStirng().length() - 1) == ' ' ? " " : "");
	}
}

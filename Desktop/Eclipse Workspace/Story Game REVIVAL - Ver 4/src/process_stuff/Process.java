package process_stuff;

import java.lang.reflect.Method;
import java.util.ArrayList;

import base.Location;
import base.Output;
import param_stuff.ObjParam;
import param_stuff.Param;
import param_stuff.ParamSet;
import param_stuff.ParamType;
import param_stuff.StaticParam;
import util.Dfl;

public class Process
{
	private String name;
	private String desc;
	private boolean isCommand;
	private ParamSet[] argSets;
	private Location[] validLocs;
	
	public Process(String name, String desc, String... followWith)
	{
		this.name = name;
		this.desc = desc;
		this.validLocs = Location.values();
		
		if (followWith == null)
			followWith = new String[] {"<null>"};

		argSets = new ParamSet[0];
		
		if (followWith != null && followWith.length != 0)
			addDescAndArgs(desc, followWith);
	}
	
	public ParamSet[] getParamSetsForLoc(Location loc)
	{
		ArrayList<ParamSet> noLocs = new ArrayList<ParamSet>();
		ArrayList<ParamSet> forLocs = new ArrayList<ParamSet>();
		
		for (ParamSet s : argSets)
			if (s.getLocation() == null)
				noLocs.add(s);
			else if (s.getLocation() == loc)
				forLocs.add(s);
		
		if (forLocs.isEmpty())
			return noLocs.toArray(new ParamSet[0]);
		
		return forLocs.toArray(new ParamSet[0]);
	}
	
	public void setInvalidLocs(Location... locs)
	{
		Location[] temp = new Location[validLocs.length - locs.length];
		
		int j = 0;
		
		for (int i = 0; i < validLocs.length; i ++)
		{
			if (!isLocIn(validLocs[i], locs))
				temp[j] = validLocs[i];
			
			else
				j--;
			
			j++;
		}
		
		validLocs = temp;
	}
	
	public void setValidLocs(Location... locs)
	{
		validLocs = locs;
	}
	
	private boolean isLocIn(Location loc, Location[] locs)
	{
		for (Location l : locs)
			if (l.equals(loc))
				return true;
		
		return false;
	}
	
	public boolean canUseInLoc(Location loc)
	{
		for (Location l : validLocs)
			if (l.equals(loc))
				return true;
		
		return false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ParamSet[] getArgSets()
	{
		return argSets;
	}
	
	public boolean hasNoArgs()
	{
		return argSets.length == 1 && argSets[0].getArgs().length == 0;
	}
	
	public static Process startsWithProcess(String input)
	{
		for (Function f : Function.getFunctions())
		{
			if (input.startsWith(f.getName()))
				return f;
		}
		
		for (Command c : Command.getCommands())
		{
			if (input.startsWith(c.getName()))
				return c;
		}
		
		return null;
	}
	
	public static Process getProcess(String name)
	{
		Function f = Function.getFunction(name);
		Command c = Command.getCommand(name);
		
		if (f != null)
			return f;
		
		return c;
	}
	
	public Output beginExecution(ParamType[] args)
	{
		Class<?>[] classes = new Class<?>[args.length];
		String classNames = "";
		
		for (int i = 0; i < classes.length; i++)
		{
			if (!classNames.equals(""))
				classNames += ", ";
				
			classes[i] = args[i].getClass();
			classNames += args[i].getClass().getName();
		}
		
		Method method;
		
		try 
		{
			method = (this.getClass().getMethod("execute", classes));
			return (Output) method.invoke(this, (Object[]) args);
		}
		
		catch (NoSuchMethodException e)
		{
			System.err.println("CODE ERROR: Could not find execution method in process \"" + this.getName() + "\" with arguments: " + classNames);
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addDescAndArgs(String desc, String... args) 
	{
		String[] newStrArgs = convert(args);
		ParamSet[] newArgSets = convertToArgSets(desc, newStrArgs);
		ParamSet[] temp = new ParamSet[argSets.length + newArgSets.length];
		
		int i = 0;
		
		for (; i < argSets.length; i++)
			temp[i] = argSets[i];
		
		for (; i - argSets.length < newArgSets.length; i++)
			temp[i] = newArgSets[i - argSets.length];
		
		argSets = temp;
	}
	
	public void addDescAndArgs(Location loc, String desc, String... args) 
	{
		if (loc != null)
		{
			Location[] temp = new Location[validLocs.length + 1];
			
			for (int i = 0; i < validLocs.length; i++)
				temp[i] = validLocs[i];
			
			temp[validLocs.length] = loc;
			validLocs = temp;
		}
		
		String[] newStrArgs = convert(args);
		ParamSet[] newArgSets = convertToArgSets(loc, desc, newStrArgs);
		ParamSet[] temp = new ParamSet[argSets.length + newArgSets.length];
		
		int i = 0;
		
		for (; i < argSets.length; i++)
			temp[i] = argSets[i];
		
		for (; i - argSets.length < newArgSets.length; i++)
			temp[i] = newArgSets[i - argSets.length];
		
		argSets = temp;
	}
	
	public String getCorrectUsage(ParamSet set)
	{
		String out = "";
		
		if (this.argSets.length > 1)
			out += "Best guess for correct usage";
		
		else
			out += "Correct usage";
		
		out += " of \"" + this.name + "\" " + (isCommand ? "command" : "function") + ":";
		out += "\n> " + this.name + " " + set.getCorrectUsage();
		
		if (this.argSets.length > 1)
		{
			out += "\n\nOther usages:";
			
			for(int i = 0; i < this.argSets.length; i++)
				if (this.argSets[i] != set)
					out += "\n> " + this.name + " " + this.argSets[i].getCorrectUsage();
		}
		
		out += "\n\n" + "Description of \"" + this.name + "\" " + (isCommand ? "command" : "function") + ":\n";
		out += desc;
		
		return out;
	}
	
	private static ParamSet[] convertToArgSets(String desc, String[] validArgSetsAsStrs)
	{
		return convertToArgSets(null, desc, validArgSetsAsStrs);
	}
	
	private static ParamSet[] convertToArgSets(Location loc, String desc, String[] validArgSetsAsStrs)
	{
		ParamSet[] out = new ParamSet[validArgSetsAsStrs.length];
		
		for (int i = 0; i < validArgSetsAsStrs.length; i++)
		{
			ArrayList<Param> list = new ArrayList<Param>();
			
			String[] parts = validArgSetsAsStrs[i].split(" ");
			
			if (parts.length == 1 && parts[0].equals("null"))
			{
				out[i] = new ParamSet(loc, desc, new Param[0]);
				continue;
			}
			
			for(int j = 0; j < parts.length; j++)
			{
				if (parts[j].charAt(0) == '+')
				{
					list.add(testForValidStaticArg(parts[j].substring(1, parts[j].length()).trim()));
				}
				
				else
				{
					list.add(testForValidArgType(parts[j]));
				}
			}
			
			out[i] = new ParamSet(loc, desc, list.toArray(new Param[] {}));
		}
		
		return out;
	}
	
	private static ObjParam testForValidArgType(String test)
	{
		ObjParam t = ObjParam.getObjParam(test);
		
		if (t != null)
			return t;
		
		Dfl.err("CODE ERROR: ArgType \"" + test + "\" not found.\nIt may not be added to the list of valid ArgTypes.\n");
		return null;
	}
	
	private static StaticParam testForValidStaticArg(String test)
	{
		StaticParam t = StaticParam.getStaticArg(test);
		
		if (t != null)
			return t;
		
		Dfl.err("CODE ERROR: ArgType \"" + test + "\" not found.\nIt may not be added to the list of valid ArgTypes.\n");
		return null;
	}
	
	/**
	 * Converts an array of Strings in the form {"<a|b>", "<a|b> <c>"} into one of the form {"a", "b", "a c", "b c"}.
	 * Used to convert function followings into something more easily dealt with.
	 * 
	 * @param array An array to convert
	 * @return A new String[] of the new form.
	 */
	private static String[] convert(String[] array)
	{
		ArrayList<String> finalList = new ArrayList<String>();
		ArrayList<String> partialList = new ArrayList<String>();
		ArrayList<String> tempList = new ArrayList<String>();
		
		int stop = array.length;
		
		for (int thruArray = 0; thruArray < stop; thruArray++)
		{
			partialList = new ArrayList<String>();
			
			String currentItem = new String(array[thruArray]);
			String[] parts = currentItem.split(" ");
			
			for (int thruParts = 0; thruParts < parts.length; thruParts++)
			{
				tempList = new ArrayList<String>();
				
				String currentPart = parts[thruParts].substring(1, parts[thruParts].length() - 1);
				String[] ors = currentPart.split("\\|");
				
				for (int thruOrs = 0; thruOrs < ors.length; thruOrs++)
				{
					if (partialList.size() == 0)
						tempList.add(ors[thruOrs]);
					
					else
						for(int i = 0; i < partialList.size(); i++)
						{
							tempList.add(partialList.get(i) + " " + ors[thruOrs]);
						}
				}
				
				partialList = tempList;
			}
			
			finalList.addAll(partialList);
		}
		
		return finalList.toArray(new String[] {});
	}
	
	public String toString()
	{
		String sets = "";
		
		for (int i = 0; i < argSets.length; i++)
		{
			sets += argSets[i].toString() + "; ";
		}
		
		if (!sets.equals(""))
			sets = sets.substring(0, sets.length() - 2);
		
		return "Process: " + this.name + " - " + desc + " (Has ArgSets: " + sets + ")";
	}
}

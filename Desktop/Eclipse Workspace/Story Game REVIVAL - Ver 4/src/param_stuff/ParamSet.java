package param_stuff;

import base.Location;

public class ParamSet
{
	private Param[] args;
	private String desc;
	private Location loc;
	
	public ParamSet(Location loc, String desc, Param... args)
	{
		this.loc = loc;
		this.args = args;
		this.desc = desc;
	}

	public ParamSet(String desc, Param... args)
	{
		this(null, desc, args);
	}
	
	public ParamSet(Param... args)
	{
		this(null, args);
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public Location getLocation()
	{
		return loc;
	}
	
	public String getCorrectUsage()
	{
		String out = "";
		
		if (args.length == 0)
			return "";
		
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] instanceof ObjParam)
			{	
				out += "<" + args[i].getName() + "> ";
			}
			
			else
			{
				StaticParam argStr = (StaticParam) args[i];
				
				out += argStr.getName() + " ";
			}
		}
		
		return out;
	}
	
	public Param[] getArgs()
	{
		return args;
	}
	
	public String toString()
	{
		String out = "";
		
		for (Param a : this.args)
		{
			out += (a instanceof ObjParam ? "<" + a.toString() + "> " : a.toString() + " ");
		}
		
		if (!out.equals(""))
			out = out.substring(0, out.length() - 1);
		
		else
			out = "(No arguments.)";
		
		return out;
	}
	
	public boolean equals(ParamSet other)
	{
		if (other.args.length != this.args.length)
			return false;
		
		for (int i = 0; i < this.args.length; i++)
		{
			if (!args[i].toString().equals(other.args[i].toString()))
				return false;
		}
		
		return true;
	}
}

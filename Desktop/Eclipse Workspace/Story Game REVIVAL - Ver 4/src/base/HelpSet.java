package base;

import param_stuff.ObjParam;
import param_stuff.Param;

public class HelpSet
{
	private String complete;
	private Param type;
	
	public HelpSet(String complete, Param type)
	{
		this.complete = complete;
		this.type = type;
	}
	
	public String getStirng()
	{
		return complete;
	}
	
	public Param getType()
	{
		return type;
	}
	
	public String toString()
	{
		return (type == null ? "" : type.toString() + "/") + "\"" + complete + "\"";
	}
}

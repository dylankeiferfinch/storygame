package params;

import base.Location;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class LocObj extends ObjParam
{
	public LocObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Location[] locs = Location.values();
		
		for (Location l : locs)
			if (l.name().equals(part.getFocus().toUpperCase()))
				return new Type(id, l);
		
		Input.setError("SOFT TYPE ERROR: The given location was not a valid one.", part);
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFUALT HARD TYPE VALUE: The type \"loc\" does not have a default value.", "Do not try to declare a \"loc\" type with \"loc[]\".", part);
		return null;
	}
	
	@Override
	public String[] defaultValues()
	{
		Location[] locs = Location.values();
		String[] ret = new String[locs.length];
		
		for (int i = 0; i < ret.length; i++)
			ret[i] = locs[i].name().toLowerCase();
		
		return ret;
	}
	
	public class Type extends Obj<Location>
	{
		public Type(String id, Location value)
		{
			super(id, value);
		}
	}
}

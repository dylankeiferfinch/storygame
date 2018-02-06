package params;

import input.Input;
import input.InputError;
import input.SoftTypedArg;
import manual.Data;
import manual.Manual;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class PathObj extends ObjParam
{

	public PathObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		String[] datas = part.getFocus().split("->");
		Data prevLoc = Manual.getCurItem();
		Data d = null;
		
		for(String s : datas)
		{
			if (s.trim().equals("^"))
				s = "(PARENT)";
			
			d = prevLoc.getSubData(s.trim());
		
			if (d == null)
			{
				Input.setError(new InputError("BAD PATH", "Cannot access \"" + s.trim() + "\" from \"" + prevLoc.getTitle() + "\".", null, null, part));
				return null;
			}
			
			prevLoc = d;
		}
		
		return new Type(d);
	}
	
	@Override
	public String[] defaultValues()
	{
		return Manual.getCurItem().getAllPaths();
	}
	
	public class Type extends Obj<Data>
	{
		public Type(Data value)
		{
			super(id, value);
		}
	}
}

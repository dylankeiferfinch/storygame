package params;

import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class TypeObj extends ObjParam
{
	public TypeObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		ObjParam[] params = ObjParam.getObjParamList();
		
		for (int i = 0; i < params.length; i++)
			if (params[i].getId().equals(part.getFocus()))
				return new Type(id, params[i]);
		
		Input.setError("SOFT TYPE ERROR: Invalid type.");
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFUALT HARD TYPE VALUE: The type \"type\" does not have a default value.", "Do not try to declare a \"type\" type with \"type[]\".", part);
		return null;
	}
	
	@Override
	public String[] defaultValues()
	{
		ObjParam[] params = ObjParam.getObjParamList();
		String[] ret = new String[params.length];
		
		for (int i = 0; i < ret.length; i++)
			ret[i] = params[i].getId();
		
		return ret;
	}
	
	public class Type extends Obj<ObjParam>
	{
		public Type(String id, ObjParam value)
		{
			super(id, value);
		}
	}
}

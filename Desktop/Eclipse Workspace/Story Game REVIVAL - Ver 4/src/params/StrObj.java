package params;

import input.HardTypedArg;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class StrObj extends ObjParam
{	
	public StrObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		return new Type(part.getFocus());
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		return new Type("text");
	}
	@Override
	public String[] defaultValues()
	{
		return new String[] {"text"};
	}
	
	public class Type extends Obj<String>
	{
		public Type(String value)
		{
			super(id, value);
		}
	}
}

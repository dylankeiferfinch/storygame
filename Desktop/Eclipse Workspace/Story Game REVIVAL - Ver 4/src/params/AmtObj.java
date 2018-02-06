package params;

import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class AmtObj extends ObjParam
{
	public AmtObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}
	
	@Override
	protected ParamType value(SoftTypedArg part)
	{
		int test;
		
		try 
		{
			test = Integer.parseInt(part.getFocus()); 
		}
		
		catch (Exception e)
		{
			Input.setError("SOFT TYPE ERROR: The \"amt\" type must be a number.", part);
			return null;
		}

		if (test > 0)
		{
			return new Type(test);
		}
		
		Input.setError("SOFT TYPE ERROR: The \"amt\" type must be a positive number.", part);
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		return new Type(1);
	}

	@Override
	public String[] defaultValues()
	{
		return new String[] {"1"};
	}
	
	public class Type extends Obj<Integer>
	{
		public Type(Integer value)
		{
			super(id, value);
		}	
	}
}

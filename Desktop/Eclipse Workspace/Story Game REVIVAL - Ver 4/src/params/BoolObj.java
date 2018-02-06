package params;

import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class BoolObj extends ObjParam
{

	public BoolObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		String val = part.getFocus();
		
		if (val.equalsIgnoreCase("TRUE") || val.equals("1"))
		{
			return new Type(true);
		}
		
		else if (val.equalsIgnoreCase("FALSE") || val.equals("0"))
		{
			return new Type(false);
		}
		
		Input.setError("BAD BOOLEAN VALUE", "Boolean values may be only \"TRUE\" or \"FALSE\".", "That is not valid boolean value. Use \"TRUE\" or \"FALSE\".", null, part);
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError(InputError.genNoDefaultHardTypeError(this, part));
		return null;
	}
	
	@Override
	public String[] defaultValues()
	{
		return new String[] {"TRUE", "FALSE"};
	}

	public class Type extends Obj<Boolean>
	{
		public Type(Boolean value)
		{
			super(id, value);
		}
	}
}

package params;

import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;
import process_stuff.Function;

public class FuncObj extends ObjParam
{

	public FuncObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Function f = Function.getFunction(part.getFocus());
		
		if (f != null)
		{
			return new Type(f);
		}
		
		Input.setError(new InputError("BAD FUNCTION NAME", "That is not a valid function name.", null, null, part));
		return null;
	}

	public class Type extends Obj<Function>
	{
		public Type(Function value)
		{
			super(id, value);
		}
	}
}

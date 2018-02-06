package params;

import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;
import process_stuff.Command;

public class ComObj extends ObjParam
{
	public ComObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Command c = Command.getCommand(part.getFocus());
		
		if (c != null)
		{
			return new Type(c);
		}
		
		c = Command.getCommand("/" + part.getFocus());
		
		if (c != null)
		{
			return new Type(c);
		}
		
		Input.setError(new InputError("BAD COMMAND NAME", "That is not the name of a command.", null, null, part));
		return null;
	}

	public class Type extends Obj<Command>
	{
		public Type(Command value)
		{
			super(id, value);
		}
	}
}

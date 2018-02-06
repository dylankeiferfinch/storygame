package params;

import base.GameBase;
import base.GameRule;
import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;
import params.DirObj.Type;

public class BooleanGameRuleObj extends ObjParam
{
	public BooleanGameRuleObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		GameRule<Boolean> rule = GameRule.getBoolRule(part.getFocus());
		
		if (rule == null)
		{
			Input.setError("BAD GAMERULE NAME: Gamerule not found.", part);
			return null;
		}
		
		return new Type(rule);
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
		return GameRule.getBoolRuleNames();
	}
	
	public class Type extends Obj<GameRule<Boolean>>
	{
		public Type(GameRule<Boolean> value)
		{
			super(id, value);
		}
	}
}

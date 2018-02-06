package params;

import base.GameBase;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import map.Direction;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class DirObj extends ObjParam
{
	public DirObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{	
		Direction dir = Direction.parseDirection(part.getFocus());
		
		if (dir == null)
		{
			Input.setError("BAD DIRECTION: That is not a valid direction.", part);
			return null;
		}
		
		if (!GameBase.getRoom().hasExit(dir))
		{
			Input.setError("BAD DIRECTION: This room does not have an exit in that direction.", part);
			return null;
		}
		
		return new Type(dir);
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFUALT HARD TYPE VALUE: The type \"dir\" does not have a default value.", "Do not try to declare a \"enemy_id\" type with \"enemy_id[]\".", part);
		return null;
	}
	
	@Override
	public String[] defaultValues()
	{
		return GameBase.getRoom().getValidDirections();
	}
	
	public class Type extends Obj<Direction>
	{
		public Type(Direction value)
		{
			super(id, value);
		}
	}
}

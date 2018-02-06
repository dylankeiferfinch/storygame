package params;

import enemies.Enemy;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class EnemyIdObj extends ObjParam
{
	public EnemyIdObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Enemy e = Enemy.byId(part.getFocus());
		
		if (e == null)
		{
			Input.setError("SOFT TYPE ERROR: Could not find the enemy accosiated with the ID.", part);
			return null;
		}
		
		return new Type(part.getFocus());
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFUALT HARD TYPE VALUE: The type \"enemy_id\" does not have a default value.", "Do not try to declare a \"enemy_id\" type with \"enemy_id[]\".", part);
		return null;
	}
	
	@Override
	public String[] defaultValues()
	{
		return Enemy.getAllIds();
	}
	
	public class Type extends Obj<String>
	{
		public Type(String value)
		{
			super(id, value);
		}
	}
}

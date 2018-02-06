package params;

import base.GameBase;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import map.Interactable;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class ExaminableObj extends ObjParam
{
	public ExaminableObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}
	
	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Interactable e = GameBase.getRoom().canInteract(part.getFocus());
		
		if (e != null)
		{
			return new Type(e);
		}
		
		Input.setError("SOFT TYPE ERROR: That thing can't be examined in this room.");
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFAULT HARD TYPE");
		return null;
	}
	
	public class Type extends Obj<Interactable>
	{
		public Type(Interactable value)
		{
			super(id, value);
		}	
	}
}

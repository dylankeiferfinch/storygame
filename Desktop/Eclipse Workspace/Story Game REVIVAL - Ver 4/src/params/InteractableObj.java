package params;

import base.GameBase;
import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.SoftTypedArg;
import map.Interactable;
import map.RoomInteractable;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class InteractableObj extends ObjParam
{

	public InteractableObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Interactable r = GameBase.getRoom().canInteract(part.getFocus());
		
		if (r != null)
			return new Type(r);
		
		Input.setError("INVALID USE: You can not use things on that object.");
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
		//TODO
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

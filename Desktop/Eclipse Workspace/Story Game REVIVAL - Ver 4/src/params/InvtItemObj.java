package params;

import hero.Invt;
import hero.Item;
import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class InvtItemObj extends ObjParam
{	
	public InvtItemObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Item id = Invt.getIdInInvt(part.getFocus());
		Item name = Invt.getNameInInvt(part.getFocus());
		
		if (id != null)
			return new Type(id);
		
		if (name != null)
			return new Type(name);
		
		Input.setError(new InputError("BAD ITEM", "You do not have an item in your inventory that has that name or ID.", "You don't have that item!", "You can see the items you do have by typing \"invt\" to view your inventory!", part));
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError(InputError.genNoDefaultHardTypeError(this, part));
		return null;
	}
	
	public class Type extends Obj<Item>
	{
		public Type(Item value)
		{
			super(id, value);
		}
	}
}

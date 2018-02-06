package params;

import hero.Item;
import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class ItemObj extends ObjParam
{

	public ItemObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Item id = Item.itemById(part.getFocus());
		Item name = Item.itemByName(part.getFocus());
		
		if (id != null)
			return new Type(id);
		
		if (name != null)
			return new Type(name);
		
		Input.setError(new InputError("BAD ITEM", "This type must be either the name or ID of a defined item.", null, null, part));
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

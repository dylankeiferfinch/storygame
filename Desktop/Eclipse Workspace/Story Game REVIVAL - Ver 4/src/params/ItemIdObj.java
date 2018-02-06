package params;

import hero.Item;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class ItemIdObj extends ObjParam
{
	public ItemIdObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Item i = Item.itemById(part.getFocus());
		
		if (i == null)
		{
			Input.setError("SOFT TYPE ERROR: Could not find the item accosiated with the ID.");
			return null;
		}
		
		return new Type(part.getFocus());
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFUALT HARD TYPE VALUE: The type \"item_id\" does not have a default value.", "Do not try to declare a \"item_id\" type with \"item_id[]\".");
		return null;
	}

	@Override
	public String[] defaultValues()
	{
		return Item.getAllItemIds();
	}
	
	public class Type extends Obj<String>
	{
		public Type(String value)
		{
			super(id, value);
		}
	}
}

package params;

import hero.Item;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class NewItemNameObj extends ObjParam
{
	public NewItemNameObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}
	
	@Override
	protected ParamType value(SoftTypedArg part)
	{
		if (Item.isItemNameTaken(part.getFocus()))
		{
			Input.setError("SOFT TYPE ERROR: The chosen new item name is already taken by an item.", part);
			return null;
		}
		
		return new Type(part.getFocus());
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		return new Type(Item.getNewItem().getName());
	}

	@Override
	public String[] defaultValues()
	{
		return new String[] {Item.getNewItem().getName()};
	}
	
	public class Type extends Obj<String>
	{
		public Type(String value)
		{
			super(id, value);
		}
	}
}

package params;

import hero.Item;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class NewItemIdObj extends ObjParam
{	
	public NewItemIdObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		if (part.getFocus().contains(" "))
		{
			Input.setError("SOFT TYPE ERROR: Item IDs cannot conatin sapces.", "Remove the space from your New Item ID.", part);
		}
		
		if (Item.isItemIdTaken(part.getFocus()))
		{
			Input.setError("SOFT TYPE ERROR: New Item ID already taken.", "Choose another New Item ID or leave that tag blank.", part);
			return null;
		}
		
		return new Type(part.getFocus());
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		return new Type(Item.getNewItem().getId());
	}

	@Override
	public String[] defaultValues()
	{
		return new String[] {Item.getNewItem().getId()};
	}
	
	public class Type extends Obj<String>
	{
		public Type(String value)
		{
			super(id, value);
		}
	}
}

package params;

import hero.Item;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class NewItemObj extends ObjParam
{
	public NewItemObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Input.setError("NO SOFT TYPING: You cannot use soft typing with this type.");
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		String name = (String) tryTag(part, "name");
		String id = (String) tryTag(part, "id");
		String desc = (String) tryTag(part, "desc", "This item was cheated in... cheater.");
		
		return new Type(new Item(name, id, desc));
	}

	public class Type extends Obj<Item>
	{
		public Type(Item value)
		{
			super(id, value);
		}
	}
}

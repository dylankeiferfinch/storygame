package params;

import input.Input;
import input.InputError;
import input.SoftTypedArg;
import manual.Manual;
import manual.Page;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class PageObj extends ObjParam
{
	public PageObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Page p = Manual.getCurItem().getSubPage(part.getFocus());
		
		if (p == null)
		{
			Input.setError(new InputError("BAD PAGE NAME", "That page is not accessible from the current folder.", null, null, part));
			return null;
		}

		return new Type(p);
	}
	
	@Override
	public String[] defaultValues()
	{
		return Manual.getCurItem().getSubPageNames();
	}
	
	public class Type extends Obj<Page>
	{
		public Type(Page value)
		{
			super(id, value);
		}
	}
}

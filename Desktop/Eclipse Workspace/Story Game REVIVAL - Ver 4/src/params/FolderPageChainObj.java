package params;

import input.SoftTypedArg;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class FolderPageChainObj extends ObjParam
{
	public FolderPageChainObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		return null;
	}
}

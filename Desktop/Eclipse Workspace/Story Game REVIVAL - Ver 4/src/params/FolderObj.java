package params;

import input.Input;
import input.InputError;
import input.SoftTypedArg;
import manual.Folder;
import manual.Manual;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class FolderObj extends ObjParam
{

	public FolderObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		String title = part.getFocus().equals("^") ? "(PARENT)" : part.getFocus().toUpperCase();
		
		Folder f = Manual.getCurItem().getSubFolder(title);
		
		if (f == null)
		{
			Input.setError(new InputError("BAD FOLDER NAME", "That folder is not accessible from the current folder.", null, null, part));
			return null;
		}

		return new Type(f);
	}
	
	@Override
	public String[] defaultValues()
	{
		return Manual.getCurItem().getSubFolderNames();
	}

	public class Type extends Obj<Folder>
	{
		public Type(Folder value)
		{
			super(id, value);
		}
	}
}

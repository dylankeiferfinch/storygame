package params;

import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import map.Conversation;
import map.ResponseOption;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class DialogOptionObj extends ObjParam
{

	public DialogOptionObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		int test;
		
		try 
		{
			test = Integer.parseInt(part.getFocus()); 
		}
		
		catch (Exception e)
		{
			Input.setError("SOFT TYPE ERROR: The \"dialog_option\" type must be a number.", part);
			return null;
		}

		if (Conversation.isValidResponseNum(test))
		{
			return new Type(test);
		}
		
		Input.setError("SOFT TYPE ERROR: The \"dialog\" type must be one of the valid dialog options shown.", part);
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		return new Type(1);
	}
	
	@Override
	public String[] defaultValues()
	{	
		ResponseOption[] options = Conversation.getCurPoint().getOptions();
		String[] out = new String[options.length];
		
		for (int i = 0; i < options.length; i++)
		{
			out[i] = "" + (i + 1);
		}
		
		return out;
	}
	
	public class Type extends Obj<Integer>
	{
		public Type(Integer value)
		{
			super(id, value);
		}
	}
}

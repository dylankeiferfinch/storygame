package param_stuff;

import input.Arg;
import input.HardTypedArg;
import input.Input;
import input.SeperatorArg;
import input.SoftTypedArg;
import input.StaticArg;
import input.VariableArg;
import params.ForStatic;
import params.ItemsStatic;
import params.OnStatic;
import params.ThenStatic;

public abstract class StaticParam extends Param
{
	private static StaticParam[] staticParams;
	
	public StaticParam(String name)
	{
		super(name, name);
	}
	
	public static void initStaticList()
	{
		staticParams = new StaticParam[0];
		
		addStaticArg(new ItemsStatic("items"));
		addStaticArg(new ForStatic("for"));
		addStaticArg(new ThenStatic("then"));
		addStaticArg(new OnStatic("on"));
	}
	
	public static void addStaticArg(StaticParam s)
	{
		StaticParam[] temp = new StaticParam[staticParams.length + 1];
		
		for (int i = 0; i < staticParams.length; i++)
			temp[i] = staticParams[i];
		
		temp[temp.length - 1] = s;
		staticParams = temp;
	}
	
	public static StaticParam getStaticArg(String id)
	{
		for (int i = 0; i < staticParams.length; i++)
		{
			if (staticParams[i].name.equals(id))
				return staticParams[i];
		}
		
		return null;
	}

	public String toString()
	{
		return name;
	}

	@Override
	protected ParamType reallyParse(Arg part)
	{
		if (part == null)
		{
			Input.setError("UNKNOWN ERROR: A null argument was passed to ObjParam parsing.", part);
			return null;
		}
		
		if (part instanceof SoftTypedArg)
			return value((SoftTypedArg) part);
		
		if (part instanceof HardTypedArg)
			return value((HardTypedArg) part);
		
		if (part instanceof StaticArg)
			return value((StaticArg) part);
		
		if (part instanceof VariableArg)
			return value(new StaticArg("@" + part.getRaw()));
		
		Input.setError("UNKNOWN ERROR: An unknwon error occured druing StaticParam parsing.");
		return null;
	}
	
	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Input.setError("ARGUMENT MISMATCH: Expected Static Argument, but got Soft Typed Argument.", "Declare a Static Argument like this: \"@arg\".", part);
		return null;
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("ARGUMENT MISMATCH: Expected Static Argument, but got Hard Typed Argument.", "Declare a Static Argument like this: \"@arg\".", part);
		return null;
	}
	
	@Override
	protected ParamType value(StaticArg part)
	{
		if (part.getFocus().equals(name))
		{
			return this.getType();
		} 
		
		else
		{
			Input.setError("WRONG STATIC ARGUMENT: Static argument \"" + part.getFocus() + "\" did not exactly match \"" + name +"\".", part);
			return null;
		}
	}
	
	@Override
	protected ParamType value(SeperatorArg part)
	{
		if (part.getFocus().equals(name))
		{
			return this.getType();
		} 
		
		else
		{
			Input.setError("WRONG SEPERATOR ARGUMENT: Seperator argument \"" + part.getFocus() + "\" did not exactly match \"" + name +"\".", part);
			return null;
		}
	}

	@Override
	public String[] defaultValues()
	{
		return new String[] {"@" + name};
	}
	
	protected abstract Static getType();
}

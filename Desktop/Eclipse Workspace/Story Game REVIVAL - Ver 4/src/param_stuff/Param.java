package param_stuff;

import base.Thing;
import input.Arg;
import input.HardTypedArg;
import input.SeperatorArg;
import input.SoftTypedArg;
import input.StaticArg;

public abstract class Param extends Thing
{
	public Param(String name, String id)
	{
		super(name, id);
	}
	
	public ParamType parse(Arg part)
	{
		return this.reallyParse(part);
	}
	
	protected abstract ParamType reallyParse(Arg part);
	protected abstract ParamType value(SoftTypedArg part);
	protected abstract ParamType value(HardTypedArg part);
	protected abstract ParamType value(StaticArg part);
	protected abstract ParamType value(SeperatorArg part);
	public abstract String[] defaultValues();
}

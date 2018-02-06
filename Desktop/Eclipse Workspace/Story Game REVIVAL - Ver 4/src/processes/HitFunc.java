package processes;

import base.Output;
import enemies.Fight;
import process_stuff.Function;

public class HitFunc extends Function
{
	public HitFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public static Output execute()
	{
		return Output.genGenericOutput(Fight.hitEnemy());
	}
}

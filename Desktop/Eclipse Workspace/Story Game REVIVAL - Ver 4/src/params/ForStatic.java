package params;

import param_stuff.Static;
import param_stuff.StaticParam;

public class ForStatic extends StaticParam
{
	public ForStatic(String name)
	{
		super(name);
	}

	@Override
	protected Static getType()
	{
		return new Type();
	}
	
	public class Type extends Static
	{
		public Type()
		{
			super(name);
		}
	}
}

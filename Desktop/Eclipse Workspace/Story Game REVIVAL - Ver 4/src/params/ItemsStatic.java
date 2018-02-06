package params;

import param_stuff.Static;
import param_stuff.StaticParam;

public class ItemsStatic extends StaticParam
{
	public ItemsStatic(String name)
	{
		super(name);
	}

	@Override
	protected Type getType()
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

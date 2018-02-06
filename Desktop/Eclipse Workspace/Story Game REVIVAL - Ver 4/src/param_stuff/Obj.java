package param_stuff;

public abstract class Obj<T> extends ParamType
{
	private String id;
	private T value;
	
	public Obj(String id, T value)
	{
		this.id = id;
		this.value = value;
	}
	
	@Override
	public T getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return id + ":[" + value.toString() + "]";
	}
	
	protected static String[] getStrArray(Object... obj)
	{
		String[] ret = new String[obj.length];
		
		for (int i = 0; i < ret.length; i++)
			ret[i] = obj[i].toString();
		
		return ret;
	}
}

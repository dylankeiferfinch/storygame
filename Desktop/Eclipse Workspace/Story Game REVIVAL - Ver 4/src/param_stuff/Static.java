package param_stuff;

public class Static extends ParamType
{
	private String name;
	
	public Static(String name)
	{
		super();
		
		this.name = name;
	}
	
	public String getValue()
	{
		return name;
	}
	
	public String toString()
	{
		return "@" + name;
	}
}

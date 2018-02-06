package input;

public class ArgTag
{
	private String name;
	private Arg value;
	
	public ArgTag(String name, Arg value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Arg getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return value.toString();
	}
}

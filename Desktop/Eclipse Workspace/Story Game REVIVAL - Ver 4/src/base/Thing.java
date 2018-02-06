package base;

public class Thing
{
	protected String name;
	protected String id;
	
	public Thing(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String toString()
	{
		return name + " (ID: " + id + ")";
	}
	
	public String devString()
	{
		return this.getClass().getName() + ": " + name + " (ID: " + id + ")";
	}
}

package base;

public class Described extends Thing
{
	protected String desc;
	
	public Described(String name, String id, String desc)
	{
		super(name, id);
		
		this.desc = desc;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public String toString()
	{
		return super.toString() + " - " + desc;
	}
}

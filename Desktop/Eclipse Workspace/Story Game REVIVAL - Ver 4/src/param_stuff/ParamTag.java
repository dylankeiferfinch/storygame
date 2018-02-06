package param_stuff;

public class ParamTag
{
	private String name;
	private ObjParam type;
	
	public ParamTag(String name, ObjParam type)
	{
		this.name = name;
		this.type = type;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ObjParam getType()
	{
		return type;
	}
}

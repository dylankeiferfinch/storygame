package map;

import hero.Item;

public class RoomObject implements Interactable
{
	protected String desc;
	private String[] names;
	
	public RoomObject(String desc, String... names)
	{
		this.desc = desc;
		this.names = names;
	}
	
	public RoomObject(String name, String desc, String[] otherNames)
	{
		this.desc = desc;
		this.names = new String[otherNames.length + 1];
		
		this.names[0] = name;
		
		for (int i = 0; i < otherNames.length; i++)
		{
			this.names[i + 1] = otherNames[i];
		}
	}
	
	public boolean isRef(String str)
	{
		for (int i = 0; i < names.length; i++)
			if (names[i].equalsIgnoreCase(str))
				return true;
		
		return false;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public String examine()
	{
		return desc;
	}
}

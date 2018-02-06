package map;

import hero.Item;

public abstract class RoomInteractable extends RoomObject
{
	protected boolean canBeUsed;
	
	public RoomInteractable(String name, String desc, String... otherNames)
	{
		super(name, desc, otherNames);
		canBeUsed = true;
	}
	
	public abstract String use(Item i);
}

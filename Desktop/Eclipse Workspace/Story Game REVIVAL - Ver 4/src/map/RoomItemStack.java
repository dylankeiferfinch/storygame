package map;

import hero.ItemStack;

public class RoomItemStack extends RoomObject
{
	private ItemStack s;
	
	public RoomItemStack(ItemStack s, String... otherNames)
	{
		super(s.getItemName(), s.getItemDesc(), otherNames);
	}
	
	public ItemStack getItemStack()
	{
		return s;
	}
}

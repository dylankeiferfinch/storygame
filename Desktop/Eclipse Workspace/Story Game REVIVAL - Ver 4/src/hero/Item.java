package hero;

import static hero.Stat.*;

public class Item
{
	private static Item[] defItems;
	private static Item[] userItems;
	
	private String name;
	private String id;
	private String desc;
	
	public Item(String name, String id, String desc)
	{
		this.name = name;
		this.id = id;
		this.desc = desc;
	}
	
	public static void initItemList()
	{
		addItem(new Item("Nail", "nail", "A rusty nail."));
		addItem(new Item("Stone Key", "key_one", "A simple key given to you by that crazy guy."));
		addItem(new Weapon("Small Sword", "sword", "A small and almost useless sword.", new Value(ATT, 4)).setEffects(Effect.Id.FIRE));
		
		userItems = new Item[0];
	}
	
	public static void addItem(Item newItem)
	{
		if (defItems == null)
			defItems = new Item[] {newItem};
		
		else
		{
			Item[] temp = new Item[defItems.length + 1];
			
			for (int i = 0; i < defItems.length; i++)
				temp[i] = defItems[i];
			
			temp[temp.length - 1] = newItem;
			defItems = temp;
		}
	}
	
	public static void addUserItem(Item newItem)
	{
		if (userItems == null)
			userItems = new Item[] {newItem};
		
		else
		{
			Item[] temp = new Item[userItems.length + 1];
			
			for (int i = 0; i < userItems.length; i++)
				temp[i] = userItems[i];
			
			temp[temp.length - 1] = newItem;
			userItems = temp;
		}
	}
	
	public static Item getNewItem()
	{
		int i = 0;
		String name;
		String id;
		
		do
		{
			i++;
			name = "New Item #" + i;
			id = "item_" + i;
		}
		while(isItemNameTaken(name) || isItemIdTaken(id));
			
		return new Item(name, id, "Description.");
	}
	
	public static String[] getAllItemIds()
	{
		String[] ret = new String[userItems.length + defItems.length];
		
		for (int i = 0; i < userItems.length; i++)
			ret[i] = userItems[i].id;
		
		for (int i = 0; i < defItems.length; i++)
			ret[userItems.length + i] = defItems[i].id;
		
		return ret;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public String toString()
	{
		return name + " (ID: " + id + ") - " + desc;
	}
	
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		
		if (obj == this)
			return true;
		
		if (obj.getClass() == this.getClass())
		{
			Item other = (Item) obj;
			return this.toString().equals(other.toString());
		}
		
		return false;
	}
	
	public static boolean isItemNameTaken(String name)
	{	
		return defItemByName(name) != null || userItemByName(name) != null || Invt.getNameInInvt(name) != null;
	}
	
	public static boolean isItemIdTaken(String id)
	{
		return defItemById(id) != null || userItemById(id) != null || Invt.getIdInInvt(id) != null;
	}
	
	public static Item itemById(String id)
	{
		for (Item i : defItems)
			if (i.id.equals(id))
				return i;
		
		for (Item i : userItems)
			if (i.id.equals(id))
				return i;
		
		return null;
	}
	
	public static Item itemByName(String name)
	{
		for (Item i : defItems)
			if (i.name.equals(name))
				return i;
		
		for (Item i : userItems)
			if (i.name.equals(name))
				return i;
		
		return null;
	}
	
	public static Item defItemById(String id)
	{
		for (Item i : defItems)
			if (i.id.equals(id))
				return i;
		
		return null;
	}
	
	public static Item defItemByName(String name)
	{
		for (Item i : defItems)
			if (i.name.equals(name))
				return i;
		
		return null;
	}
	
	public static Item userItemById(String id)
	{
		for (Item i : userItems)
			if (i.id.equals(id))
				return i;
		
		return null;
	}
	
	public static Item userItemByName(String name)
	{
		for (Item i : userItems)
			if (i.name.equals(name))
				return i;
		
		return null;
	}
	
	public static String getItemsAsString()
	{
		String out = "Predefined Items: ";
		
		for (Item i : defItems)
			out += "\n" + i.toString();
		
		out += "\n\nUser-Defined Items:";
		
		for (Item i : userItems)
			out += "\n" + i.toString();
		
		return out;
	}
}

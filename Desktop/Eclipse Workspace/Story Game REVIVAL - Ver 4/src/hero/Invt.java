package hero;

import java.util.ArrayList;

public class Invt
{
	private static int maxItems;
	private static ArrayList<ItemStack> items;
	
	public static void initInvt()
	{
		items = new ArrayList<ItemStack>();
		maxItems = 700;
	}
	
	public static boolean addItemStack(ItemStack stack)
	{
		if (stack.getAmt() + items.size() > maxItems)
			return false;
		
		for(ItemStack s : items)
		{
			if (s.canCombineWith(stack))
			{
				s.combineWith(stack);
				return true;
			}
		}
		
		items.add(stack);
		return true;
	}
	
	public static boolean addItem(Item i)
	{
		return addItemStack(new ItemStack(i, 1));
	}
	
	public static boolean addItem(Item i, int amt)
	{
		return addItemStack(new ItemStack(i, amt));
	}
	
	public static Item getIdInInvt(String id)
	{
		for (ItemStack i : items)
			if (i.getItemId().equals(id))
				return i.getItem();
		
		return null;
	}
	
	public static Weapon getWeaponIdInInvt(String id)
	{
		for (ItemStack i : items)
			if (i.getItemId().equals(id) && i.getItem() instanceof Weapon)
				return (Weapon) i.getItem();
		
		return null;
	}
	
	public static Weapon getWeaponNameInInvt(String name)
	{
		for (ItemStack i : items)
			if (i.getItemName().equals(name) && i.getItem() instanceof Weapon)
				return (Weapon) i.getItem();
		
		return null;
	}
	
	public static Item getNameInInvt(String name)
	{
		for (ItemStack i : items)
			if (i.getItemName().equals(name))
				return i.getItem();
		
		return null;
	}
	
	public static String getInvtAsString()
	{
		if (items.size() == 0)
			return "You don't have any items....";
		
		String out = "Weapon: " + (Hero.getHero().getWeapon() == null ? "(None)" : Hero.getHero().getWeapon().getName()) + "\n\n";
		
		for (ItemStack i : items)
			out += i.toString() + "\n";
		
		out = out.substring(0, out.length() - 1); 
		
		return out;
	}
}

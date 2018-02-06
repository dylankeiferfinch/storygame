package hero;

import map.Interactable;

public class ItemStack implements Interactable
{
	private Item item;
	private int amt;
	
	public ItemStack(Item item, int amt)
	{
		this.item = item;
		this.amt = amt;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public int getAmt()
	{
		return amt;
	}
	
	public String getItemName()
	{
		return item.getName();
	}
	
	public String getItemId()
	{
		return item.getId();
	}
	
	public String getItemDesc()
	{
		return item.getDesc();
	}
	
	public String toString()
	{
		return (amt > 1 ? "(" + amt + ") " : "") + item.toString();
	}
	
	public String toStringWithoutId()
	{
		return (amt > 1 ? "(" + amt + ") " : "") + item.getName();
	}
	
	public boolean canCombineWith(ItemStack other)
	{
		if (this.item.equals(other.item))
			return true;
		
		return false;
	}
	
	public void combineWith(ItemStack other)
	{
		if (this.canCombineWith(other))
			this.amt += other.amt;
	}

	@Override
	public String examine()
	{
		return item.getDesc();
	}

	@Override
	public boolean isRef(String str)
	{
		return str.equals(getItemName());
	}
}

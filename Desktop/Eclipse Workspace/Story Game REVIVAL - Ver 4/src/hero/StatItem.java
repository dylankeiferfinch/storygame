package hero;

public class StatItem extends Item implements StatChanger
{
	protected StatSet stats;
	
	public StatItem(String name, String id, String desc, Value... stats)
	{
		super(name, id, desc);
	
		this.stats = new StatSet(stats);
	}
	
	public StatSet getStats()
	{
		return stats;
	}
	
	public double getValue(Stat stat)
	{	
		return stats.get(stat);
	}
	
	public void setValue(Stat stat, double value)
	{
		stats.set(stat, value);
	}
	
	public void changeValue(Stat stat, double by)
	{
		stats.change(stat, by);
	}
	
	public String toString()
	{
		return super.toString() + "\n   On equip: " + stats.toString();
	}
}

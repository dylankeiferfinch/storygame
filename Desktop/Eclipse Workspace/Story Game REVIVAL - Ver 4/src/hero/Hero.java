package hero;

import static hero.Stat.*;

public class Hero
{
	private static Actor hero;
	
	public static void initHero()
	{
		hero = new Actor("You", "hero", "The player.", new Value(MHP, 20), new Value(CHP, 20), new Value(ATT, 2), new Value(DEF, 0), 
				new Value(LUC, 0), new Value(MNY, 0), new Value(EXP, 0), new Value(LVL, 1));
	}
	
	public static Actor getHero()
	{
		return hero;
	}
	
	public static void setHero(Actor a)
	{
		hero = a;
	}
	
	public static void changeStats(StatSet stats)
	{
		for (Value s : stats.getStats())
			hero.changeValue(s.getStat(), s.getValue());
	}
	
	public static double getValue(Stat stat)
	{	
		return hero.getValue(stat);
	}
	
	public static void setValue(Stat stat, double value)
	{
		hero.setValue(stat, value);
	}
	
	public static void changeValue(Stat stat, double by)
	{
		hero.changeValue(stat, by);
	}
}

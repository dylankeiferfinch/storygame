package enemies;

import hero.Actor;
import hero.Value;

import static hero.Stat.*;
import static enemies.Enemy.Id.*;

import java.util.Collection;
import java.util.HashMap;

public class Enemy extends Actor
{	
	private static HashMap<Id, Enemy> enemies;
	
	public static void initEnemyList()
	{
		enemies = new HashMap<Id, Enemy>();
		
		add(BAT, "Bat", "bat", "It's a bat.... That's it.", 3, 1, 0, 1, 1, 1);
		add(RAT, "Rat", "rat", "It's a rat.... That's it.", 4, 1.2, 1, 1, 2, 2);
	}
	
	public static Enemy byDevId(Id devId)
	{
		return enemies.get(devId);
	}
	
	private static void add(Id devId, String name, String id, String desc, double mhp, double att, double def, double luc, double mny, double exp)
	{
		enemies.put(devId, new Enemy(name, id, desc, mhp, att, def, luc, mny, exp));
	}
	
	public Enemy(String name, String id, String desc, Value... stats)
	{
		super(name, id, desc, stats);
	}
	
	public Enemy(String name, String id, String desc, double mhp, double att, double def, double luc, double mny, double exp)
	{
		this(name, id, desc, new Value(MHP, mhp), new Value(CHP, mhp), new Value(ATT, att), new Value(DEF, def), 
				new Value(LUC, luc), new Value(MNY, mny), new Value(EXP, exp), new Value(LVL, 1));
	}
	
	public Enemy spawn(double level)
	{
		return new Enemy(name, id, desc, stats.multiplyStats(level));
	}
	
	public Enemy spawn()
	{
		return spawn(1);
	}
	
	@Override
	public String getName()
	{
		if (this.getValue(LVL) != 1)
			return "Level " + (int)this.getValue(LVL) + " " + name;
		
		return name;
	}
	
	public static Enemy byId(String id)
	{
		for (Enemy e : enemies.values())
			if (e.id.equals(id))
				return e;
		
		return null;
	}
	
	public static String[] getAllIds()
	{
		Collection<Enemy> list = enemies.values();
		
		String[] out = new String[list.size()];
		
		int i = 0;
		for (Enemy e : list)
		{
			out[i] = e.id;
			i++;
		}
		
		return out;
	}
	
	public enum Id
	{
		BAT, RAT;
	}
}

package hero;

import java.util.HashMap;

import base.Described;

import static hero.Effect.Id.*;
import static hero.Stat.*;

public class Effect extends Described implements StatChanger
{
	private static HashMap<Effect.Id, Effect> effects;
	
	public static void initEffectList()
	{
		effects = new HashMap<Effect.Id, Effect>();
		
		effects.put(FIRE, new TurnlyEffect("Fire", "fire", "on fire", 2, new Value(CHP, -3)));
	}
	
	public static Effect byDevId(Effect.Id devId)
	{
		return effects.get(devId);
	}
	
	private StatSet stats;
	
	public Effect(String name, String id, String desc, Value... stats)
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
		return super.toString() + "\n   When applied: " + stats.toString();
	}
	
	public enum Id
	{
		FIRE;
	}
}

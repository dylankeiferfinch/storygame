package hero;

import java.util.HashMap;

public class Weapon extends StatItem
{
	private HashMap<Effect.Id, Effect> effects;
	
	public Weapon(String name, String id, String desc, Value... stats)
	{
		super(name, id, desc, stats);
		
		effects = new HashMap<Effect.Id, Effect>();
	}
	
	public Weapon setEffects(Effect.Id... effects)
	{
		for (Effect.Id e : effects)
			this.effects.put(e, Effect.byDevId(e));
		
		return this;
	}
	
	public Effect[] getEffects()
	{
		return this.effects.values().toArray(new Effect[0]);
	}
	
	public String toString()
	{
		return super.toString() + " [Equip as a weapon.]";
	}
}

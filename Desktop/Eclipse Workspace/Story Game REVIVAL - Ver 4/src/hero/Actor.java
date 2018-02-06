package hero;

import java.util.ArrayList;
import java.util.Iterator;

import base.Described;
import util.Gen;

public class Actor extends Described
{
	protected StatSet stats;
	protected ArrayList<Armor> armor;
	protected ArrayList<Effect> effects;
	protected Weapon weapon;
	
	public Actor(String name, String id, String desc, Value... stats) 
	{
		super(name, id, desc);

		this.stats = new StatSet(stats);
		this.armor = new ArrayList<Armor>();
		this.effects = new ArrayList<Effect>();
		this.weapon = null;
	}
	
	public double getValue(Stat stat)
	{	
		return stats.get(stat);
	}
	
	public void setValue(Stat stat, double value)
	{
		this.stats.set(stat, value);
	}
	
	public void changeValue(Stat stat, double by)
	{
		this.stats.change(stat, by);
	}
	
	public String hit(Actor a)
	{
		String out = "";
		
		for (Effect e : a.getEffects())
			if (e instanceof TurnlyEffect)
				out += (a.name.equals("You") ? "You are " : "The " + a.getName() + " is ") + e.getDesc() + ": " + e.getStats().toString() + "\n";
		
		a.applyTurnlyEffects();
		
		double dmg = calcDmg(this.getValue(Stat.ATT), a.getValue(Stat.DEF));
		a.changeValue(Stat.CHP, -dmg);
		
		if (name.equals("You"))
			out += "You hit the " + a.getName() + " doing " + dmg + " damage!"; 
		
		else
			out += "The " + this.getName() + " hits you, doing " + dmg + " damage!"; 
		
		Effect[] applied = a.applyWeaponEffects(this.weapon);
		
		for (Effect e : applied)
			out += "\n" + (a.name.equals("You") ? "You are" : "The " + a.getName() + " is") + " now " + e.getDesc() + ".";
		
		return out;
	}
	
	public Effect[] applyWeaponEffects(Weapon w)
	{
		Effect[] toApply = (w == null ? new Effect[0] : w.getEffects());
		ArrayList<Effect> applied = new ArrayList<Effect>();
		
		for (Effect e : toApply)
		{
			if (!effects.contains(e))
			{
				effects.add(e);
				applied.add(e);
			}
		}
		
		return applied.toArray(new Effect[0]);
	}
	
	public Weapon equip(Weapon w)
	{
		if (weapon != null && w.getId().equals(weapon.getId()))
			return null;
		
		stats.add(w.getStats());
		
		weapon = w;
		
		return w;
	}
	
	public Weapon unequipWeapon()
	{
		stats.subtract(weapon.getStats());
		
		Weapon out = weapon;
		weapon = null;
		return out;
	}
	
	public Weapon getWeapon()
	{
		return weapon;
	}
	
	public void equip(Armor a)
	{
		this.armor.add(a);
		this.stats.add(a.getStats());
	}
	
	public void unequip(Armor a)
	{
		this.armor.remove(a);
		this.stats.subtract(a.getStats());
	}
	
	public void apply(TurnlyEffect e)
	{
		this.effects.add(e);
	}
	
	public void unapply(TurnlyEffect e)
	{
		this.effects.remove(e);
	}
	
	public void apply(ImmEffect e)
	{
		this.effects.add(e);
		this.stats.add(e.getStats());
	}
	
	public void unapply(ImmEffect e)
	{
		this.effects.remove(e);
		this.stats.subtract(e.getStats());
	}
	
	public void applyTurnlyEffects()
	{
		for (Iterator<Effect> i = effects.iterator(); i.hasNext(); )
		{
			Effect e = i.next();
			
			if (e instanceof TurnlyEffect)
			{
				TurnlyEffect turnlyEffect = (TurnlyEffect) e;
				
				if (turnlyEffect.stillOn())
				{
					stats.add(e.getStats());
					turnlyEffect.turn();
				}
				
				else
				{
					i.remove();
				}
			}
		}
	}
	
	public Effect[] getEffects()
	{
		return effects.toArray(new Effect[0]);
	}
	
	public void clearEffects()
	{
		effects.clear();
		stats = new StatSet();
	}
	
	public Actor spawn()
	{
		return new Actor(name, id, desc, stats.getStats());
	}
	
	private static double calcDmg(double attackerAtt, double defenderDef)
	{
		double dmg = attackerAtt - defenderDef + Gen.randDouble(-attackerAtt / 10, attackerAtt / 10, 2);
		
		return dmg < 0 ? 0 : dmg;
	}
}

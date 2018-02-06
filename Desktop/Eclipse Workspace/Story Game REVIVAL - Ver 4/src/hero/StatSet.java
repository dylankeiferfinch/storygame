package hero;

import java.util.Collection;
import java.util.HashMap;

import util.Dfl;

public class StatSet
{
	private HashMap<Stat, Value> stats;
	
	public StatSet(Value... stats)
	{
		this.stats = new HashMap<Stat, Value>();
		
		for (Value s : stats)
			this.stats.put(s.getStat(), s);
		
		for (Stat s : Stat.values())
			if (!this.stats.containsKey(s))
				this.stats.put(s, new Value(s, 0));
	}
	
	public double get(Stat what)
	{
		return stats.get(what).getValue();
	}
	
	public void set(Stat what, double to)
	{
		stats.get(what).setValue(to);
	}
	
	public void change(Stat what, double by)
	{
		stats.get(what).changeValue(by);
	}
	
	public void add(StatSet stats)
	{
		for(Value s : stats.getStats())
			this.change(s.getStat(), s.getValue());
	}
	
	public void subtract(StatSet stats)
	{
		for(Value s : stats.getStats())
			this.change(s.getStat(), -s.getValue());
	}
	
	public Value[] getStats()
	{
		return stats.values().toArray(new Value[0]);
	}
	
	public Value[] multiplyStats(double by)
	{
		Collection<Value> s = stats.values();
		
		Value[] ret = new Value[s.size()];
		
		int i = 0;
		
		for(Value v : s)
		{
			ret[i] = new Value(v.getStat(), v.getValue() * by);
			i++;
		}
		
		return ret;
	}
	
	public String toString()
	{
		String out = "";
		
		for (Value v : stats.values())
			if (v.getValue() != 0)
				out += v.getSignedValue() + " " + v.getStatName() + ", ";
		
		if (out.length() == 0)
			return "(No stat change.)";
		
		else
			return Dfl.cutString(out, 2);
	}
}

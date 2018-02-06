package hero;

public interface StatChanger
{
	public StatSet getStats();
	public double getValue(Stat stat);
	public void setValue(Stat stat, double value);
	public void changeValue(Stat stat, double by);
}

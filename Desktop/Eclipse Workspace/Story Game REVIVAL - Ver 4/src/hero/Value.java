package hero;

public class Value
{
	private Stat stat;
	private double value;
	
	public Value(Stat stat, double value)
	{
		this.stat = stat;
		this.value = value;
	}
	
	public Stat getStat()
	{
		return stat;
	}
	
	public String getStatName()
	{
		return stat.base().getName();
	}
	
	public String getStatDesc()
	{
		return stat.base().getDesc();
	}
	
	public String getStatId()
	{
		return stat.base().getId();
	}
	
	public double getValue()
	{
		return value;
	}
	
	public String getSignedValue()
	{
		return (value >= 0 ? "+" : "") + value;
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
	public void changeValue(double by)
	{
		this.value += by;
	}
}

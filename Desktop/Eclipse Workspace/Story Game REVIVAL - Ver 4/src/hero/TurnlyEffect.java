package hero;

public class TurnlyEffect extends Effect
{
	private int turnsLeft;
	
	public TurnlyEffect(String name, String id, String desc, int turns, Value... stats)
	{
		super(name, id, desc, stats);
		
		turnsLeft = turns;
	}
	
	public void turn()
	{
		turnsLeft--;
	}
	
	public boolean stillOn()
	{
		return turnsLeft > 0;
	}
}

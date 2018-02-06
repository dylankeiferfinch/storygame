package processes;

import base.Output;
import enemies.Enemy;
import enemies.Fight;
import params.EnemyIdObj;
import process_stuff.Command;

public class SpawnCom extends Command
{
	public SpawnCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public static Output execute(EnemyIdObj.Type e)
	{
		Fight.startFight(Enemy.byId(e.getValue()));
		
		return Output.genGenericOutput("You begin to fight the " + Enemy.byId(e.getValue()).getName() + ".");
	}
}

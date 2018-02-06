package processes;

import base.GameBase;
import base.Output;
import map.GameMap;
import params.DirObj;
import process_stuff.Command;

public class GoCom extends Command
{
	public GoCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(DirObj.Type dir)
	{
		boolean wasLocked = !GameBase.getRoom().isExitUnlocked(dir.getValue());
		
		
		boolean hasEnemy = GameMap.move(dir.getValue()).getStartEnemy() != null;
		
		return Output.genGenericOutput("You move into the room to the " + dir.getValue() + (wasLocked ? ", even though the door was locked." : ".") + (hasEnemy ? " Just as you begin to look around the room, an enemy jumps you!" : ""));
	}
}

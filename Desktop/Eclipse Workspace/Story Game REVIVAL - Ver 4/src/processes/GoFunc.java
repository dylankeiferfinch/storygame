package processes;

import base.GameBase;
import base.Output;
import map.GameMap;
import params.DirObj;
import process_stuff.Function;

public class GoFunc extends Function
{
	public GoFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(DirObj.Type arg0)
	{
		if (!GameBase.getRoom().isExitUnlocked(arg0.getValue()))
		{
			return Output.genGenericOutput("Unfortunately, that way is locked. There is probably a key around here that you can use on it, though.");
		}
		
		boolean hasEnemy = GameMap.move(arg0.getValue()) != null;
		
		return Output.genGenericOutput("You move into the room to the " + arg0.getValue() + "." + (hasEnemy ? " Just as you begin to look around the room, an enemy jumps you!" : ""));
	}
}

package processes;

import base.Output;
import enemies.Fight;
import process_stuff.Command;

public class KillCom extends Command
{
	public KillCom(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute()
	{
		return Output.genGenericOutput("Killed the enemy.\n\n" + Fight.win());
	}
}

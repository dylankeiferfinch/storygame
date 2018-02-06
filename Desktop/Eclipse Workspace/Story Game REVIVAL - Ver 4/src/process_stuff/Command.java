package process_stuff;

import base.Location;
import processes.*;

public class Command extends Process
{
	private static Command[] commands;
	
	public Command(String name, String desc, String... followWith)
	{
		super(name, "This command " + desc + ".", followWith);
	}

	public static void initCommands()
	{
		commands = new Command[0];
		
		Command echoFunc = new EchoCom("/echo", "prints out the given arguments that follow", "<str>", "<item>", "<+items>");
		echoFunc.addDescAndArgs("prints the given string the given number of times", "<str> <amt>", "<str> <+for> <amt>", "<str> <+for> <amt> <+then> <str> <+for> <amt>");
		echoFunc.setValidLocs(Location.ROOM);
		addCommand(echoFunc);
		
		Command additemCom = new AdditemCom("/additem", "adds an item to the game's internal list so that the item can be used by other commands", "<new:item>");
		addCommand(additemCom);
		
		Command listitemsCom = new ListitemsCom("/listitems", "lists the items in the game", "<null>");
		addCommand(listitemsCom);
		
		Command giveCom = new GiveCom("/give", "gives an item", "<item>", "<item> <amt>", "<new:item>", "<new:item> <amt>");
		addCommand(giveCom);
		
		Command spawnCom = new SpawnCom("/spawn", "spawns an enemy", "<enemy_id>");
		addCommand(spawnCom);
		
		Command gotoCom = new GotoCom("/goto", "changes the current game location to the given one. This can be used to get out of invalid locations reached through errors", "<loc>");
		addCommand(gotoCom);
		
		Command infoCom = new InfoCom("/info", "gives information on various things", "<type>");
		addCommand(infoCom);
		
		Command setCom = new SetCom("/set", "change the value of a GameRule", "<null>", "<bool_rule> <bool>", "<bool_rule>");
		addCommand(setCom);
		
		Command goCom = new GoCom("/go", "lets you move into an adacent room, regradless of any locked doors", "<dir>");
		addCommand(goCom);
		
		Command helpCom = new HelpCom("/help", "gives you help with commands", "<null>", "<com>");
		addCommand(helpCom);
		
		Command killCom = new KillCom("/kill", "kills an enemy", "<null>");
		addCommand(killCom);
	}
	
	public static Command getCommand(String name)
	{
		for (int i = 0; i < commands.length; i++)
			if (commands[i].getName().equals(name))
				return commands[i];
		
		return null;
	}
	
	public static Command[] getCommands()
	{
		return commands;
	}
	
	private static void addCommand(Command c)
	{
		Command[] temp = new Command[commands.length + 1];
		
		for (int i = 0; i < commands.length; i++)
			temp[i] = commands[i];
			
		temp[temp.length - 1] = c;
		commands = temp;
	}
}

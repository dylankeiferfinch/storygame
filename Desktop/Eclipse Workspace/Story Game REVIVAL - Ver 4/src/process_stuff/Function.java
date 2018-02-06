package process_stuff;

import base.Location;
import processes.*;

public class Function extends Process
{
	private static Function[] functions;
	
	public Function(String name, String desc, String... followWith)
	{
		super(name, "This function " + desc + ".", followWith);
	}

	public static void initFunctions()
	{
		functions = new Function[0];
		
		Function invtFunc = new InvtFunc("invt", "prints the inventory", "<null>");
		invtFunc.setValidLocs(Location.ROOM);
		addFunction(invtFunc);
		
		Function helpFunc = new HelpFunc("help", "gives information about the game and valid processes", "<null>", "<func>");
		addFunction(helpFunc);
		
		Function exitFunc = new ExitFunc("exit", "exits the inventory or the manual", "<null>");
		exitFunc.addDescAndArgs(Location.INVENTORY, "Exits the inventory.", "<null>");
		exitFunc.addDescAndArgs(Location.MANUAL, "Exits the manual.", "<null>");
		//exitFunc.setValidLocs(Location.INVENTORY, Location.MANUAL);
		addFunction(exitFunc);
		
		Function hitFunc = new HitFunc("hit", "hits the enemy", "<null>");
		hitFunc.setValidLocs(Location.COMBAT);
		addFunction(hitFunc);
		
		Function respawnFunc = new RespawnFunc("respawn", "respawns you", "<null>");
		respawnFunc.setValidLocs(Location.DEATH);
		addFunction(respawnFunc);
		
		Function onwardFunc = new OnwardFunc("onward", "continues you on your quest", "<null>");
		onwardFunc.setValidLocs(Location.VICTORY);
		addFunction(onwardFunc);
		
		Function contFunc = new ContFunc("cont", "continues you on your quest", "<null>");
		contFunc.setInvalidLocs(Location.GAME_START);
		addFunction(contFunc);
		
		Function speakFunc = new SpeakToFunc("speak to", "talk to someone! Maybe, you'll make a new friend", "<person>");
		speakFunc.setValidLocs(Location.ROOM);
		addFunction(speakFunc);
		
		Function optionFunc = new OptionFunc("option", "choose an option", "<dialog>");
		optionFunc.setValidLocs(Location.CONVERSATION);
		addFunction(optionFunc);
		
		Function lookFunc = new LookFunc("look", "look around the current room and get some extra info", "<null>");
		lookFunc.setValidLocs(Location.ROOM);
		addFunction(lookFunc);
		
		Function leaveFunc = new LeaveFunc("leave", "leave the conversation you're currently in", "<null>");
		leaveFunc.setValidLocs(Location.CONVERSATION);
		addFunction(leaveFunc);
		
		Function startFunc = new StartFunc("start", "starts a new game! It's pretty useless after the beginning of the game...", "<null>");
		startFunc.setValidLocs(Location.GAME_START);
		addFunction(startFunc);
		
		Function goFunc = new GoFunc("go", "moves you into a new room", "<dir>");
		goFunc.setValidLocs(Location.ROOM);
		addFunction(goFunc);
		
		Function mapFunc = new MapFunc("map", "shows the current map", "<null>");
		mapFunc.setValidLocs(Location.ROOM);
		addFunction(mapFunc);
		
		Function examineFunc = new ExamineFunc("examine", "examines an object in the room", "<examinable>");
		examineFunc.setValidLocs(Location.ROOM);
		addFunction(examineFunc);
		
		Function skipFunc = new SkipFunc("skip", "skips the rest of the tutorial and starts the real game", "<null>");
		skipFunc.setValidLocs(Location.TUTORIAL);
		addFunction(skipFunc);
		
		Function nextFunc = new NextFunc("next", "goes to the next page in the tutorial", "<null>");
		nextFunc.setValidLocs(Location.TUTORIAL);
		addFunction(nextFunc);
		
		Function useFunc = new UseFunc("use", "use an item to prgress the plot", "<invt_item> <+on> <inter>");
		useFunc.setValidLocs(Location.ROOM);
		addFunction(useFunc);
		
		Function saveFunc = new SaveFunc("save", "saves the game", "<null>");
		addFunction(saveFunc);
		
		Function manualFunc = new ManualFunc("manual", "opens the manual, which contains lots of helpful info about how the game works", "<null>");
		manualFunc.setInvalidLocs(Location.MANUAL);
		addFunction(manualFunc);
		
		Function openFunc = new OpenFunc("open", "opens a folder or page in the manual", "<path>");
		openFunc.setValidLocs(Location.MANUAL);
		addFunction(openFunc);
		
		Function equipFunc = new EquipFunc("equip", "equips a weapon or armor", "<invt_weapon>");
		equipFunc.setValidLocs(Location.INVENTORY);
		addFunction(equipFunc);
		
		Function treeFunc = new TreeFunc("tree", "shows a tree view of your cureent position in the manual", "<null>");
		treeFunc.setValidLocs(Location.MANUAL);
		addFunction(treeFunc);
	}
	
	public static Function getFunction(String name)
	{
		for (int i = 0; i < functions.length; i++)
			if (functions[i].getName().equals(name))
				return functions[i];
		
		return null;
	}
	
	public static Function[] getFunctions()
	{
		return functions;
	}
	
	private static void addFunction(Function f)
	{
		Function[] temp = new Function[functions.length + 1];
		
		for (int i = 0; i < functions.length; i++)
			temp[i] = functions[i];
			
		temp[temp.length - 1] = f;
		functions = temp;
	}
}

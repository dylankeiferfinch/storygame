package base;

import enemies.Fight;
import faults.BackgroundProcessFailedFault;
import hero.Hero;
import hero.Invt;
import hero.Stat;
import input.Input;
import input.InputError;
import manual.Manual;
import map.Conversation;
import map.Room;
import util.Dfl;

public class GameBase
{
	private static final int VERSION = 4;
	private static final boolean DEBUG = true;
	
	private static Location gameArea;
	private static Room curRoom;
	private static Output output;
	private static boolean hasLooked;
	
	public static boolean isDebug()
	{
		return DEBUG;
	}
	
	public static void initGameBase()
	{
		gameArea = DEBUG ? Location.ROOM : Location.GAME_START;
		hasLooked = false;
	}
	
	public static void setCurRoom(Room r)
	{
		curRoom = r;
	}
	
	public static Output getOutput()
	{
		return output;
	}
	
	public static void processInput(String input)
	{
		Input curInput = new Input(input);
		
		output = curInput.getOutput();
	}
	
	public static void runInBackground(String input)
	{
		new Input(input);
		
		InputError err = Input.getError();
		
		if (err != null)
		{
			throw new BackgroundProcessFailedFault(err.getHardErrorMsg());
		}
	}
	
	public static void setLocation(Location loc)
	{
		gameArea = loc;
		InputHelp.initInputHelp();
	}
	
	public static Location getLocation()
	{
		return gameArea;
	}
	
	public static String getStartingPromptText()
	{
		return getPromptText();
	}
	
	public static String getStartingInputText()
	{
		return "Type \"start\" to begin the game, or press [ENTER] for some help.";
	}
	
	public static String getPromptText()
	{
		switch(gameArea)
		{
			case ROOM:
			{
				if (!hasLooked)
					return genPrompt("DUNGEON ROOM", "What would you like to do?", curRoom.getStory());
					
				else
					return "DUNGEON ROOM\n\n" + curRoom.getDesc() + "\n\nWhat would you like to do?";
			}
				
			case INVENTORY:
				return "INVENTORY\n\n" + Invt.getInvtAsString() + "\n\nWhat would you like to do?";
			
			case COMBAT:
				return "COMBAT\n\nYou vs. " + Fight.getEnemy().getName() + "\nYour health: " + Dfl.round(Hero.getValue(Stat.CHP), 2) + 
						" / " + Dfl.round(Hero.getValue(Stat.MHP), 2) + "\nThe " + Fight.getEnemy().getName() + "'s health: " + 
						Dfl.round(Fight.getEnemy().getValue(Stat.CHP), 2) + " / " + Dfl.round(Fight.getEnemy().getValue(Stat.MHP), 2) + 
						"\n\nWhat would you like to do?";
				
			case DEATH:
				return "YOU DIED\n\nDang. Well... I guess you can just try again?\n\nType \"respawn\" to respawn:";
				
			case VICTORY:
				return "VICTORY COCKTAIL RECEPTION\n\nCongratulations, good sir!\n\n" + Fight.getLastSpoils() + "\n\nType \"onward\" to continue you adventure:";
				
			case CONVERSATION:
				return "CONVERSATION\n\nHow will you respond?\n\n" + Conversation.getOptionsAsString();
				
			case GAME_START:
				return "MAIN MENU\n\nWelcome to Story Game: A Tale of Adventure and Intrigue (beta " + VERSION + ")\nA game by Dylan Finch.\n\nWhat would you like to do?";
				
			case MANUAL:
				return Manual.getPrompt();
				
			case TUTORIAL:
				return Tutorial.getPrompt();
				
			default:
				return "ERROR: No specified prompt for this location.";
		}
	}
	
	public static String genPrompt(String title, String prompt, String... extras)
	{
		String ret = "";
		
		for (String str : extras)
		{
			ret += str + "\n\n";
		}
		
		return title + (GameRule.getBoolRule("CheatMode").getValue() ? " - CHEATS ENABLED" : "") + "\n\n" + ret + prompt;
	}
	
	public static Room getRoom()
	{
		return curRoom;
	}
	
	public static void look()
	{
		hasLooked = true;
	}
}

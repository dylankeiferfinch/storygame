package base;

public class Tutorial
{
	private static int page;
	private static final int MAX_PAGE = 6;
	
	public static void initTutorial()
	{
		page = 1;
	}
	
	public static String getPrompt()
	{
		switch(page)
		{
			case 1:
				return fillPrompt("tutorial basics", "This tutorial will teach you the basics of Story Game.\n\nDuring the tutorial, you can type:\nnext - Takes you to the next page of the tutorial.\nskip - Skips the rest of the tutorial and gets you right into the action.");
			
			case 2:
				return fillPrompt("help", "Help is important. In this game, you can get help at any time by typing \"help\". You can even try it now! The help manu will tell you what you can currently do.\n\nFor more in-depth help on more than just what you can type, you can use the this game's manual. It is accessible by typing \"manual\". It contains many pages of information about game mechanics.");
				
			case 3:
				return fillPrompt("doing stuff", "For better or for worse, this is a text-based game. So, to do anything you have to type stuff in that text box down there. Input is processed with a preceeding process (like \"help\" or \"examine\") that is then followed by zero or more arguments (like the thing you want to examine). The game is pretty good about guessing what you mean. Many different names for things are acceptable.");
				
			case 4: 
				return fillPrompt("doing stuff, cont", "You can use single quote marks to designate a single argument that conatins spaces. Otherwise, each space designates a seperate argumanet. However, the game is pretty good at guess what you mean. But if the game gets confused, using quotes may help.\n\nFor example:\n > use 'Silver Key' on door\nis, in most cases, funcationally the same as\n > use Silver Key on door\nYou're welcome.");
				
			case 5:
				return fillPrompt("autocomplete", "When typing input, you can use the [UP ARROW] and the [DOWN ARROW] to cycle through different input options. You can even try it now! Then, use the [RIGHT ARROW] to confirm autocompleted text.");
				
			case 6:
				return fillPrompt("lookinng around", "In rooms, you can look around by typing \"look\". This will give you some extra info about your current location and may give you some hints about what you need to do next. Then, you can examine specific thing by using the examine function.");
				
			default:
				return "unknown page - you shouldn't be here";
		}
	}
	
	public static void nextPage()
	{
		if (page == MAX_PAGE)
			GameBase.setLocation(Location.ROOM);
		
		else
			page++;
	}
	
	public static String fillPrompt(String title, String str)
	{
		return "TUTORIAL - " + title.toUpperCase() + " (PAGE " + page + " OF " + MAX_PAGE + ")\n\n" + str + "\n\nType below:";
	}
}

package manual;

import readers.Reader;

public class Manual
{	
	public static Folder root;
	public static Data curItem;
	public static Reader r;
	
	public static void initManual()
	{
		r = new Reader("manual_pages");
		
		Data manInfo = fromResource("Manual Info");
		Data autocompleteBasics = fromResource("Autocomplete Basics");
		Folder commands = new Folder("COMMANDS");
		Folder input = new Folder("INPUT", autocompleteBasics);
		root = new Folder("MANUAL", commands, input, manInfo);
	}
	
	public static Page fromResource(String title)
	{
		return new Page(title, r.readAllUnderTag(title));
	}
	
	public static void startManual()
	{
		curItem = root;
	}
	
	public static String getPrompt()
	{
		return curItem.toString() + "\nWhat would you like to do?"; 
	}
	
	public static String moveTo(Data data)
	{
		curItem = data;
		return curItem.getTitle();
	}
	
	public static Data getCurItem()
	{
		return curItem;
	}
	
	public static String tree()
	{
		if (curItem instanceof Folder)
			return ((Folder) curItem).getTree();
		
		return "(PARENT) - " + curItem.getParent().getTitle() + "\n|->" + curItem.getTitle();
	}
}

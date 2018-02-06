package input;

import base.GameBase;
import base.Location;

public class InputAutocorrect
{
	private static InputAutocorrect[] list;
	
	private String text;
	private String correct;
	private Location loc;
	
	public InputAutocorrect(String text, String correct)
	{
		this(text, correct, null);
	}
	
	public InputAutocorrect(String text, String correct, Location loc)
	{
		this.text = text;
		this.correct = correct;
		this.loc = loc;
	}
	
	public static void init()
	{
		list = new InputAutocorrect[0];
		
		add(GameBase.getStartingInputText(), "[SuppressInputRepeat] help");
		add("Dylan is cool.", "[SuppressInputRepeat] /echo 'I know.'");
		add("", "[SuppressInputRepeat] cont");
		add("s", "speak to Steve");
		add("close", "open (PARENT)");
		add("~", "onward", Location.VICTORY);
		
		for (int i = 1; i <= 10; i++)
			add(i + "", "option " + i);
	}
	
	public static String getReplacement(String input)
	{
		for (int i = 0; i < list.length; i++)
			if ((input.equals(list[i].text) || list[i].text.equals("~")) && (list[i].loc == null || list[i].loc == GameBase.getLocation()))
			{
				Log.log("Replaced input \"" + input + "\" with \"" + list[i].correct + "\"");
				return list[i].correct;
			}
		
		return input;
	}
	
	private static void add(String text, String correct)
	{
		add(new InputAutocorrect(text, correct));
	}
	
	private static void add(String text, String correct, Location loc)
	{
		add(new InputAutocorrect(text, correct, loc));
	}
	
	private static void add(InputAutocorrect ia)
	{
		InputAutocorrect[] temp = new InputAutocorrect[list.length + 1];
		
		for (int i = 0; i < list.length; i++)
			temp[i] = list[i];
		
		temp[temp.length - 1] = ia;
		
		list = temp;
	}
}

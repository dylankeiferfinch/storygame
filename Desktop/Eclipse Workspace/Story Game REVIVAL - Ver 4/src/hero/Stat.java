package hero;

import base.Described;

public enum Stat
{	
	CHP("Current HP", "chp", "The amount of health that you currently have."),
	MHP("Max HP", "mhp", "The maximum amount of health that you can have."),
	ATT("Attack", "att", "Dictates how much damage your attacks will do."),
	DEF("Defense", "def", "Dictates how much incoming damage will be reduced."),
	LUC("Luck", "luc", "Helps in various luck-based situations."),
	MNY("Cash", "mny", "The amount of cash that you currently have."),
	EXP("Experience", "exp", "The amount of experience you have towards your next level up."),
	LVL("Level", "lvl", "Your current level, based on how much experience you've collected.");
		
	private Base stat;
		
	Stat(Base stat)
	{
		this.stat = stat;
	}
		
	Stat(String name, String id, String desc)
	{
		this(new Base(name, id, desc));
	}
	
	public Base base()
	{
		return stat;
	}
	
	public static class Base extends Described
	{
		private Base(String name, String id, String desc)
		{
			super(name, id, desc);
		}
	}
}

package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameRule<T>
{
	public static String CHEATS = "CheatMode",
						 SOFT_ERRORS = "SoftErrorMessages",
						 FULL_MAP = "AlwaysShowFullMap";
	
	private static Map<String, GameRule<?>> rules;
	private static Map<String, GameRule<Boolean>> boolRules;
	
	private String name;
	private String desc;
	private T defaultValue;
	private T currentValue;
	
	public GameRule(String name, String desc, T defaultValue)
	{
		this.name = name;
		this.desc = desc;
		this.defaultValue = defaultValue;
		this.currentValue = defaultValue;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDesc()
	{
		return desc;
	}
	
	public T getValue()
	{
		return currentValue;
	}
	
	public void setValue(T value)
	{
		currentValue = value;
	}
	
	public String toString()
	{
		if (currentValue instanceof Boolean)
		{
			return name + ": " + ((boolean) currentValue ? "TRUE" : "FALSE");
		}
		
		return name + ": " + currentValue;
	}
	
	public static void initGameRules()
	{
		rules = new HashMap<String, GameRule<?>>();
		boolRules = new HashMap<String, GameRule<Boolean>>();
		add(new GameRule<Boolean>(SOFT_ERRORS, "When set to TRUE, error messages will be friendly and conatin less debug-type information.", !GameBase.isDebug()));
		add(new GameRule<Boolean>(CHEATS, "When set to TRUE, you are allowed to use commands to change the game state.", GameBase.isDebug()));
		add(new GameRule<Boolean>(FULL_MAP, "When set to TRUE, the full map is always shown when using the \"map\" function, regradless of which rooms have been discovered.", GameBase.isDebug()));
	}
	
	public static void add(GameRule<?> rule)
	{
		rules.put(rule.name, rule);
		
		if (rule.defaultValue instanceof Boolean)
			boolRules.put(rule.name, (GameRule<Boolean>) rule);
	}
	
	public static GameRule<?> getRule(String name)
	{
		return rules.get(name);
	}
	
	public static GameRule<Boolean> getBoolRule(String name)
	{
		return boolRules.get(name);
	}
	
	public static String[] getBoolRuleNames()
	{
		return boolRules.keySet().toArray(new String[0]);
	}
	
	public static String getGameRulesAsString()
	{
		String out = "";
		
		for (GameRule<?> rule : rules.values())
		{
			out += rule + "\n";
		}
		
		return out;
	}
}

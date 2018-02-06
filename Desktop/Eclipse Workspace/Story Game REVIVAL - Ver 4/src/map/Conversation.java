package map;

import base.GameBase;
import base.Location;

public class Conversation
{
	public static RoomPerson curPartner;
	public static TalkingPoint curPoint;
	
	public static void beginConversationWith(RoomPerson p)
	{
		curPartner = p;
		curPoint = curPartner.converse(curPartner.getStartTag());
	}
	
	public static void converse(int next)
	{
		String nextText = curPoint.getOptions()[next - 1].getResponseTag();
		curPoint = curPartner.converse(nextText);
		
		if (curPoint == null)
		{
			GameBase.setLocation(Location.ROOM);
		}
	}
	
	public static TalkingPoint getCurPoint()
	{
		return curPoint;
	}
	
	public static String getResponse()
	{
		if (curPoint == null)
		{
			return "You leave the conversation.";
		}
		
		return curPoint.getResponse();
	}
	
	public static String getOptionsAsString()
	{
		String out = "";
		ResponseOption[] options = curPoint.getOptions();
		
		for (int i = 0; i < options.length; i++)
		{
			out += "[" + (i + 1) + "] "  + options[i] + "\n";
		}
		
		return out;
	}
	
	public static boolean isValidResponseNum(int num)
	{
		if (num >= 1 && num <= curPoint.getOptions().length)
		{
			return true;
		}
		
		return false;
	}
}

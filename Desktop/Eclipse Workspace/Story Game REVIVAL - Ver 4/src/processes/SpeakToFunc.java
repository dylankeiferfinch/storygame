package processes;

import base.GameBase;
import base.Location;
import base.Output;
import map.Conversation;
import params.PersonInRoomObj;
import process_stuff.Function;
import util.Dfl;

public class SpeakToFunc extends Function
{
	public SpeakToFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}
	
	public Output execute(PersonInRoomObj.Type arg0)
	{
		GameBase.setLocation(Location.CONVERSATION);
		Conversation.beginConversationWith(arg0.getValue());
		return Output.genGenericOutput(Conversation.getResponse());
	}
}

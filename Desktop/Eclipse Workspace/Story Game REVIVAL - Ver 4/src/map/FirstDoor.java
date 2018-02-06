package map;

import java.lang.reflect.InvocationTargetException;

import base.GameBase;
import hero.Invt;
import hero.Item;

public class FirstDoor extends RoomInteractable
{	
	public FirstDoor(String name, String desc, String... otherNames)
	{
		super(name, desc, otherNames);
	}

	@Override
	public String use(Item i)
	{
		if (i.equals(Invt.getIdInInvt("key_one")))
		{
			canBeUsed = false;
			GameBase.getRoom().getExit(Direction.EAST).unlock();
			return "You insert the key and swing the door open with all of your strength. You are free from this room.";
		}
		
		return "How would that work, smart guy? You should try a key.";
	}
}

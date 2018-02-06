package params;

import base.GameBase;
import input.HardTypedArg;
import input.Input;
import input.SoftTypedArg;
import map.RoomPerson;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class PersonInRoomObj extends ObjParam
{
	public PersonInRoomObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		RoomPerson p = GameBase.getRoom().personInRoom(part.getFocus());
		
		if (p == null)
		{
			Input.setError("SOFT TYPE ERROR: That person isn't in this room.", part);
			return null;
		}
		
		return new Type(p);
	}

	@Override
	protected ParamType value(HardTypedArg part)
	{
		Input.setError("NO DEFUALT HARD TYPE VALUE: The type \"loc\" does not have a default value.", "Do not try to declare a \"loc\" type with \"loc[]\".", part);
		return null;
	}
	
	@Override
	public String[] defaultValues()
	{
		RoomPerson[] people = GameBase.getRoom().getPeople();
		String[] out = new String[people.length];
		
		for (int i = 0; i < people.length; i++)
		{
			out[i] = people[i].getName();
		}
		
		return out;
	}
	
	public class Type extends Obj<RoomPerson>
	{
		public Type(RoomPerson value)
		{
			super(id, value);
		}
	}
}

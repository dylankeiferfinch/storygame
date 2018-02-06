package params;

import hero.Invt;
import hero.Weapon;
import input.Input;
import input.InputError;
import input.SoftTypedArg;
import param_stuff.Obj;
import param_stuff.ObjParam;
import param_stuff.ParamTag;
import param_stuff.ParamType;

public class InvtWeaponObj extends ObjParam
{
	public InvtWeaponObj(String id, String name, String desc, ParamTag... tags)
	{
		super(id, name, desc, tags);
	}

	@Override
	protected ParamType value(SoftTypedArg part)
	{
		Weapon id = Invt.getWeaponIdInInvt(part.getFocus());
		Weapon name = Invt.getWeaponNameInInvt(part.getFocus());
		
		if (id != null)
			return new Type(id);
		
		if (name != null)
			return new Type(name);
		
		Input.setError(new InputError("BAD WEAPON", "You do not have a weapon in your inventory that has that name or ID.", "You don't have that weapon!", "You can see the items you do have by typing \"invt\" to view your inventory! Weapons will be marked.", part));
		return null;
	}
	
	public class Type extends Obj<Weapon>
	{
		public Type(Weapon value)
		{
			super(id, value);
		}	
	}
}

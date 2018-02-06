package processes;

import base.Output;
import hero.Hero;
import params.InvtWeaponObj;
import process_stuff.Function;

public class EquipFunc extends Function
{
	public EquipFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute(InvtWeaponObj.Type weapon)
	{
		Hero.getHero().equip(weapon.getValue());
		return Output.genGenericOutput("You equip the " + weapon.getValue().getName() + ".");
	}
}

package map;

import hero.Item;
import util.Gen;

public interface Interactable
{
	public String examine();
	boolean isRef(String str);
	default String use(Item i)
	{
		int num = Gen.randInt(1, 5);
		
		switch(num)
		{
			case 1:
				return "Now that's kinda dumb.";
			case 2:
				return "How in the heck would you do that?";
			case 3:
				return "Sure, you can do that... when pigs fly! Ha!";
			case 4:
				return "Nah.";
			case 5:
				return "Buddy, ya can't do that!";
			default:
				return "Nope.";
		}
	}
}

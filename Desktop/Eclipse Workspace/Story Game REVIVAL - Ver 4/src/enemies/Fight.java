package enemies;

import base.GameBase;
import base.Location;
import hero.Actor;
import hero.Hero;
import util.Gen;
import static hero.Stat.*;

public class Fight
{
	private static Enemy curEnemy;
	private static Enemy lastEnemy;
	private static Actor hero;
	
	public static void startFight(Enemy e)
	{
		hero = Hero.getHero().spawn();
		curEnemy = e.spawn(5);
		GameBase.setLocation(Location.COMBAT);
	}
	
	public static Enemy getEnemy()
	{
		return curEnemy;
	}
	
	private static double calcDmg(double attackerAtt, double defenderDef)
	{
		double dmg = attackerAtt - defenderDef + Gen.randDouble(-attackerAtt / 10, attackerAtt / 10, 2);
		
		return dmg < 0 ? 0 : dmg;
	}
	
	public static void doEnemyTurn()
	{
		Hero.changeValue(CHP, -calcDmg(curEnemy.getValue(ATT), Hero.getValue(DEF)));
	}
	
	public static String getLastSpoils()
	{
		return "You killed the " + lastEnemy.getName() + ".\nXP gained: " + lastEnemy.getValue(EXP) + "\nCash gained: " + lastEnemy.getValue(MNY);
	}
	
	public static void endFight()
	{
		Hero.getHero().setValue(CHP, hero.getValue(CHP));
		lastEnemy = curEnemy;
		curEnemy = null;
	}
	
	public static String win()
	{
		GameBase.setLocation(Location.VICTORY);
		endFight();
		return "The " + lastEnemy.getName() + " died.... Congratulations!\nYou won " + lastEnemy.getValue(EXP) + " XP and " + lastEnemy.getValue(MNY) + " Cash!";
	}
	
	public static String hitEnemy()
	{
		String out = "";
		
		out += Hero.getHero().hit(curEnemy);
		
		if (curEnemy.getValue(CHP) <= 0)
		{
			return out += "\n\n" + win();
		}
		
		out += "\n\n" + curEnemy.hit(hero);
		
		if (Hero.getValue(CHP) <= 0)
		{
			GameBase.setLocation(Location.DEATH);
			endFight();
			return out + "\n\nOh man, tough luck there, buddy. Looks like you died.\nThe " + lastEnemy.getName() + " took all your stuff and contuied on your quest. Sadly, it only wore your fedora ironically.";
		}
		
		return out;
	}
}

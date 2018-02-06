package enemies;

import static enemies.Enemy.Id.*;

import util.Gen;

public enum EnemyList
{
	EASY(Enemy.byDevId(BAT));
	
	private Enemy[] enemies;
	
	EnemyList(Enemy... enemies)
	{
		this.enemies = enemies;
	}
	
	public Enemy randEnemy()
	{
		if (enemies.length == 0)
			return null;
		
		return enemies[Gen.randInt(0, enemies.length - 1)];
	}
	
	public Enemy[] getEnemies()
	{
		return enemies;
	}
}

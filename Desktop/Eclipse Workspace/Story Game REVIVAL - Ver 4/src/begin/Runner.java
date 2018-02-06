package begin;

import base.GameBase;
import base.GameRule;
import base.GameWindow;
import base.InputHelp;
import base.Tutorial;
import enemies.Enemy;
import hero.Effect;
import hero.Hero;
import hero.Invt;
import hero.Item;
import input.InputAutocorrect;
import input.Log;
import manual.Manual;
import map.GameMap;
import param_stuff.ObjParam;
import param_stuff.StaticParam;
import process_stuff.Command;
import process_stuff.Function;

public class Runner
{	
	/*
	 * TODO LIST:
	 * TODO: Save game feature.
	 * TODO: Armor
	 * TODO: Shops
	 * TODO: enemies drop items
	 */
	
	public static void main(String[] args)
	{	
		Log.log("Game started.");
		
		Log.begin("Intializing game");
		
		Effect.initEffectList();
		Enemy.initEnemyList();
		Manual.initManual();
		GameRule.initGameRules();
		Tutorial.initTutorial();
		ObjParam.initObjParamList();
		StaticParam.initStaticList();
		Item.initItemList();
		Invt.initInvt();
		Function.initFunctions();
		Command.initCommands();	
		GameBase.initGameBase();
		InputHelp.initInputHelp();
		Hero.initHero();
		InputAutocorrect.init();
		GameMap.initMap();
		
		Log.end("Intializing game");
		
		new GameWindow();
	}
}



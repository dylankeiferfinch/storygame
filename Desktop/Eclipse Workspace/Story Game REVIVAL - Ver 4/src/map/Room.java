package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import enemies.Enemy;
import enemies.Fight;
import hero.Item;
import hero.ItemStack;
import util.Dfl;

import static map.Direction.*;

public class Room
{
	private String name;
	private String story;
	private String desc;
	private RoomItemStack[] takables;
	private RoomObject[] examinables;
	private ItemStack[] droppedItems;
	private RoomInteractable[] ris;
	private Enemy[] enemies;
	private RoomPerson[] people;
	private double spawnChance;
	private Enemy startEnemy;

	private Map<Direction, RoomDoor> exits;
	
	private boolean isNew;
	private boolean visited;
	
	public Room(String name)
	{
		exits = new HashMap<Direction, RoomDoor>();
		
		this.name = name;
		story = "You enter an average room.";
		desc = "There isn't anything of interest here.";
		takables = new RoomItemStack[0];
		examinables = new RoomObject[0];
		droppedItems = new ItemStack[0];
		ris = new RoomInteractable[0];
		enemies = new Enemy[0];
		people = new RoomPerson[0];
		spawnChance = 0;
		startEnemy = null;
		
		isNew = true;
		visited = false;
	}
	
	public Enemy getStartEnemy()
	{
		return startEnemy;
	}
	
	public Room setStartEnemy(Enemy e)
	{
		startEnemy = e;
		
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Room setStory(String story)
	{
		this.story = story;
		
		return this;
	}
	
	public Room setDesc(String desc)
	{
		this.desc = desc;
		
		return this;
	}
	
	public Room setTakables(RoomItemStack... takables)
	{
		this.takables = takables;
		
		return this;
	}
	
	public Room setExaminables(RoomObject... examinables)
	{
		this.examinables = examinables;
		
		return this;
	}
	
	public Room setEnemies(Enemy... enemies)
	{
		this.enemies = enemies;
		
		return this;
	}
	
	public Room setSpawnChance(double spawnChance)
	{
		this.spawnChance = spawnChance;
		
		return this;
	}
	
	public Room setPeople(RoomPerson... people)
	{
		this.people = people;
		
		return this;
	}
	
	public Room setExit(Direction dir, Room room)
	{
		return setExit(dir, new RoomDoor(room, false));
	}
	
	public Room setLockedExit(Direction dir, Room room)
	{
		return setExit(dir, new RoomDoor(room, true));
	}
	
	public Room setExit(Direction dir, RoomDoor door)
	{
		exits.put(dir, door);
		door.getRoom().exits.put(dir.reverse(), new RoomDoor(this, door.isLocked()));
		
		return this;
	}
	
	public RoomDoor getExit(Direction dir)
	{
		return exits.get(dir);
	}
	
	public boolean hasExit(Direction dir)
	{
		return exits.get(dir) != null;
	}
	
	public boolean isExitUnlocked(Direction dir)
	{
		return exits.get(dir) != null && !exits.get(dir).isLocked();
	}
	
	public Room setInteractables(RoomInteractable... interactables)
	{
		this.ris = interactables;
		
		return this;
	}
	
	public void visit()
	{
		visited = true;
		
		if (startEnemy != null)
		{
			Fight.startFight(startEnemy);
		}
	}
	
	public boolean hasVisited()
	{
		return visited;
	}
	
	public RoomInteractable getInteractable(String name)
	{
		for(RoomInteractable r : ris)
			if (r.isRef(name))
				return r;
		
		return null;
	}

	public String getStory()
	{
		isNew = false;
		return story;
	}

	public String getDesc()
	{
		return desc;
	}

	public RoomItemStack[] getTakables()
	{
		return takables;
	}

	public RoomObject[] getExaminables()
	{
		return examinables;
	}

	public ItemStack[] getDroppedItems()
	{
		return droppedItems;
	}

	public Enemy[] getEnemies()
	{
		return enemies;
	}
	
	public RoomPerson[] getPeople()
	{
		return people;
	}

	public double getSpawnChance()
	{
		return spawnChance;
	}
	
	public Room getEast()
	{
		return exits.get(EAST) != null ? exits.get(EAST).getRoom() : null;
	}
	
	public Room getNorth()
	{
		return exits.get(NORTH) != null ? exits.get(NORTH).getRoom() : null;
	}
	
	public Room getWest()
	{
		return exits.get(WEST) != null ? exits.get(WEST).getRoom() : null;
	}
	
	public Room getSouth()
	{
		return exits.get(SOUTH) != null ? exits.get(SOUTH).getRoom() : null;
	}

	public boolean isNew()
	{
		return isNew;
	}
	
	public RoomPerson personInRoom(String name)
	{
		for (RoomPerson p : people)
		{
			if (p.isRef(name))
			{
				return p;
			}
		}
		
		return null;
	}
	
	public String[] getValidDirections()
	{
		Set<Direction> dirs = exits.keySet();
		ArrayList<String> ret = new ArrayList<String>();
		
		for (Direction dir : dirs)
		{
			ret.add(dir.toString());
		}
		
		return ret.toArray(new String[0]);
	}
	
	public Interactable canInteract(String name)
	{
		for (Interactable e : takables)
			if (e.isRef(name))
				return e;
		
		for (Interactable e : examinables)
			if (e.isRef(name))
				return e;
		
		for (Interactable e : ris)
			if (e.isRef(name))
				return e;
		
		for (Interactable e : people)
			if (e.isRef(name))
				return e;
		
		for (Interactable e : droppedItems)
			if (e.isRef(name))
				return e;
		
		return null;
	}
}

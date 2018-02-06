package map;

import java.util.ArrayList;

import base.GameBase;
import base.GameRule;
import enemies.Enemy;
import enemies.EnemyList;
import hero.Item;
import hero.ItemStack;
import readers.Line;
import readers.Reader;
import util.Dfl;

import static map.Direction.*;

public class GameMap
{
	private static ArrayList<Room> rooms;
	private static ArrayList<Room> roomsInMap;
		
	public static void initMap()
	{
		Room other = new Room("Other Room");
		Room start = new Room("Starting Room");
		Room room3 = new Room("");
		Room room4 = new Room("");
		Room room5 = new Room("");
		
		start.setStory("You pull your head up slowly from the baren cot you were laying on. You're in some sort of small dungeon room. The last thing you remember is being at the company Chirstmas party. But how in the hell did that lead you here?");
		start.setDesc("The room is fairly empty. However, there are some nails in the corner. They're lying on what looks like a bed. Beside the bed is a large stone door. There's also a strange man grumbling about something in the corner adjcent to you.");
		start.setExaminables(new RoomObject("It's pretty gross, but it would probably work for a night's rest.", "bed"));
		start.setTakables(new RoomItemStack(new ItemStack(Item.itemById("nail"), 1), "nails"));
		start.setEnemies(EnemyList.EASY.getEnemies());
		start.setSpawnChance(50);
		start.setLockedExit(EAST, other);
		start.setInteractables(new FirstDoor("First Door", "It's the first and smallest obsticale in your journey.", "door", "large door", "stone door", "large stone door", "dor"));
		start.setPeople(new RoomPerson("Steve", "He's a mysterious man who loves looking at his wall.", "dialog/steve", "steve", "man", "strange", "strange man", "grmbling man", "strange grumbling man"));
		start.visit();
		rooms = new ArrayList<Room>();
		
		other.setStory("You wander into the next room. It looks much the same to the other one.");
		other.setDesc("There is nothing in this room.");
		other.setStartEnemy(Enemy.byDevId(Enemy.Id.BAT));
		
		//other.setWest(start);
		
		other.setExit(NORTH, room3);
		room3.setExit(EAST, room4);
		other.setExit(SOUTH, room5);
		
		GameBase.setCurRoom(start);
	}
	
	public static Room move(Direction dir)
	{
		RoomDoor exit = GameBase.getRoom().getExit(dir);
		
		if (exit == null)
		{
			return null;
		}
		
		else
		{
			Room newRoom = exit.getRoom();
			GameBase.setCurRoom(newRoom);
			newRoom.visit();
			return newRoom;
		}
	}
	
	public static String getMapAsString()
	{
		roomsInMap = new ArrayList<Room>();
		char[][] map = addRoomToMap(new char[3][3], 1, 1, GameBase.getRoom());
		String ret = "";
		
		for (int row = 0; row < map.length; row++)
		{
			for (int col = 0; col < map[0].length; col++)
			{
				if (col == 0 || row == 0 || col == map[0].length - 1 || row == map.length - 1)
				{
					ret += "#";
				}
				
				else if (map[row][col] != '\u0000')
				{
					ret += map[row][col];
				}
				
				else
				{
					ret += " ";
				}
			}
			
			ret += "\n";
		}
		
		return ret;
	}
	
	public static char[][] addRoomToMap(char[][] map, int row, int col, Room room)
	{
		roomsInMap.add(room);
		
		map = newMap(map, row, col);
		
		if (row < 0)
		{
			row = row + 2;
		}
		
		if (col < 0)
		{
			col = col + 2;
		}
		
		if (room == GameBase.getRoom())
		{
			map[row][col] = '@';
		}
		
		else if (!room.hasVisited())
		{
			map[row][col] = '?';
		}
		
		else
		{
			map[row][col] = 'O';
		}
		
		if (room.hasVisited() || GameRule.getBoolRule(GameRule.FULL_MAP).getValue())
		{	
			if (room.getEast() != null && !roomsInMap.contains(room.getEast()))
			{
				if (room.getEast().getWest() == room)
				{
					map[row][col + 1] = '-';
				}
				
				else
				{
					map[row][col + 1] = '>';
				}
				
	
				map = addRoomToMap(map, row, col + 2, room.getEast());
			}
			
			if (room.getNorth() != null  && !roomsInMap.contains(room.getNorth()))
			{
				if (room.getNorth().getSouth() == room)
				{
					map[row - 1][col] = '|';
				}
				
				else
				{
					map[row - 1][col] = '^';
				}
				
				map = addRoomToMap(map, row - 2, col, room.getNorth());
				
				row += 2;
			}
			
			if (room.getWest() != null  && !roomsInMap.contains(room.getWest()))
			{
				if (room.getWest().getEast() == room)
				{
					map[row][col - 1] = '-';
				}
				
				else
				{
					map[row][col - 1] = '<';
				}
				
				map = addRoomToMap(map, row, col - 2, room.getWest());
				
				col += 2;
			}
			
			if (room.getSouth() != null  && !roomsInMap.contains(room.getSouth()))
			{
				if (room.getSouth().getNorth() == room)
				{
					map[row + 1][col] = '|';
				}
				
				else
				{
					map[row + 1][col] = 'v';
				}
				
				map = addRoomToMap(map, row + 2, col, room.getSouth());
			}
		}
		
		return map;
	}
	
	private static char[][] newMap(char[][] array, int r, int c)
	{
		int orgRows = array.length;
		int orgCols = array[0].length;
		
		int rowLength = array.length + (r < 0 || r >= orgRows ? 2 : 0);
		int colLength = array[0].length + (c < 0 || c >= orgCols ? 2 : 0);
		
		if (orgRows == rowLength && orgCols == colLength)
		{
			return array;
		}
		
		char[][] ret = new char[rowLength][colLength];
		
		for(int row = 0; row < rowLength; row++)
		{
			for (int col = 0; col < colLength; col++)
			{
				if (row < orgRows && col < orgCols)
				{
					if (r < 0)
					{
						ret[row + 2][col] = array[row][col];
					}
					
					else if (c < 0)
					{
						ret[row][col + 2] = array[row][col];
					}
					
					else
					{
						ret[row][col] = array[row][col];
					}
				}
			}
		}
	
		return ret;
	}
	
	public static void readRooms()
	{
		Reader read = new Reader("rooms");
		
		read.gotoStart();
		Room curRoom = null;
		int pos = 0;
		
		while(read.hasNext())
		{
			Line curLine = read.next();
			
			if (curLine.isTag())
			{
				if (curRoom != null)
				{
					rooms.add(curRoom);
				}
				
				pos = 0;
				curRoom = new Room(curLine.getData());
			}
			
			else if (pos == 0)
			{
				curRoom.setStory(curLine.getData());
			}
			
			else if (pos == 1)
			{
				curRoom.setDesc(curLine.getData());
			}
			
			else if (pos == 2)
			{
				//curRoom.setEnemies(Enemy.getEnemies(Dfl.getInt(curLine.getData())));
			}
			
			else if (pos == 3)
			{
				curRoom.setSpawnChance(Dfl.getInt(curLine.getData()));
			}
			
			else if (curLine.isSubTag("X"))
			{
				//curRoom.
			}
		}
	}
}

package map;

public class RoomDoor
{
	private Room room;
	private boolean isLocked;
	
	public RoomDoor(Room room, boolean isLocked)
	{
		this.room = room;
		this.isLocked = isLocked;
	}
	
	public void unlock()
	{
		isLocked = false;
	}
	
	public void lock()
	{
		isLocked = true;
	}
	
	public boolean isLocked()
	{
		return isLocked;
	}
	
	public Room getRoom()
	{
		return room;
	}
}

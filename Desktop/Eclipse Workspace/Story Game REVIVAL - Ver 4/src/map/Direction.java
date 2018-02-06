package map;

public enum Direction
{	
	NORTH, EAST, SOUTH, WEST;
	
	public String toString()
	{
		return this.name().toLowerCase();
	}
	
	public static Direction reverseOf(Direction dir)
	{
		switch(dir)
		{
			case NORTH:
				return SOUTH;
				
			case SOUTH:
				return NORTH;
				
			case EAST:
				return WEST;
				
			case WEST:
				return EAST;
				
			default:
				return null;	
		}
	}
	
	public Direction reverse()
	{
		return reverseOf(this);
	}
	
	public static Direction parseDirection(String str)
	{
		try
		{
			return valueOf(str.toUpperCase());
		}
		
		catch (IllegalArgumentException e)
		{
			return null;
		}
	}
}

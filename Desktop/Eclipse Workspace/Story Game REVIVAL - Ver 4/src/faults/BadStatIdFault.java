package faults;

public class BadStatIdFault extends RuntimeException
{
	public BadStatIdFault(String msg)
	{
		super(msg);
	}
}

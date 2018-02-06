package faults;

public class BackgroundProcessFailedFault extends RuntimeException
{
	public BackgroundProcessFailedFault(String msg)
	{
		super(msg);
	}
}

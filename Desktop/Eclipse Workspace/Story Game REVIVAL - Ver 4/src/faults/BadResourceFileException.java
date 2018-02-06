package faults;

public class BadResourceFileException extends RuntimeException
{
	public BadResourceFileException(String msg)
	{
		super(msg);
	}
}

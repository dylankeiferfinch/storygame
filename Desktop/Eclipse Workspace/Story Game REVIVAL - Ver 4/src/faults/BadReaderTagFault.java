package faults;

public class BadReaderTagFault extends RuntimeException
{
	public BadReaderTagFault(String msg)
	{
		super(msg);
	}
}

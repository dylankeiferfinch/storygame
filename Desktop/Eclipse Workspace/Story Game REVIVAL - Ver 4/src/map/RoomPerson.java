package map;

import java.util.ArrayList;

import base.GameBase;
import base.Location;
import faults.BadReaderTagFault;
import faults.BadResourceFilePatternFault;
import readers.Line;
import readers.Reader;

public class RoomPerson extends RoomObject
{
	private String name;
	private String resourceFileStr;
	private String startAt;
	private Reader read;
	
	public RoomPerson(String name, String desc, String resourceFile, String... nicknames)
	{
		super(name, desc + "\n\nYou can speak to this person.", nicknames);
		startAt = "START";
		this.name = name;
		this.resourceFileStr = resourceFile;
		read = new Reader(resourceFileStr);
	}
	
	public String getStartTag()
	{
		return startAt;
	}
	
	public TalkingPoint converse(String next)
	{
		if (next.equals("END"))
		{
			return null;
		}
		
		String output = "";
		ArrayList<ResponseOption> options = new ArrayList<ResponseOption>();
		
		boolean found = read.gotoTag(next);
		
		if (!found)
		{
			throw new BadReaderTagFault("Dialog tree tag \"" + next + "\" could not be found for Person \"" + name + "\".");
		}
		
		if (read.peek().getData().startsWith("*"))
	    {
			output += "*" + read.next().getData().substring(1).trim().replaceAll("[$]", name) + "*\n\n";
		}
		
		Line nextText = read.nextInTag();
		
		if (nextText == null)
		{
			throw new BadResourceFilePatternFault("Expected Dialog line, but found none (under tag \"" + next + "\") for person \"" + name + "\".");
		}
			
		output += name + ": " + nextText.getData();
		
		if (read.peek().getData().startsWith("*"))
	    {
			output += "\n\n*" + read.next().getData().substring(1).trim().replaceAll("[$]", name) + "*\n\n";
		}

		while (!read.peek().getType().equals("") && read.hasNextInTag())
		{
			String text = read.peek().getType();
			
			if (text.equals("start"))
			{
				startAt = read.next().getData();
			}
			
			else if (text.equals("execute"))
			{
				GameBase.runInBackground(read.next().getData());
			}
			
			else if (text.equals("leave"))
			{
				GameBase.setLocation(Location.ROOM);
				read.next();
			}
			
			else if (text.equals("desc"))
			{
				this.desc = read.next().getData();
			}
			
			else
			{
				throw new BadResourceFilePatternFault("(File: " + read.getFile().getAbsolutePath() + ") Unknown !type: " + read.next().getType());
			}
		}

		while(read.hasNextInTag())
		{	
			options.add(new ResponseOption(read.next().getData(), read.next().getData()));
		}
		
		if (options.size() == 0)
		{
			options.add(new ResponseOption("(End conversation.)", "END"));
		}
		
		return new TalkingPoint(output, options.toArray(new ResponseOption[0]));
	}
	
	public String getName()
	{
		return name;
	}
	
	public String toString()
	{
		return name;
	}
}

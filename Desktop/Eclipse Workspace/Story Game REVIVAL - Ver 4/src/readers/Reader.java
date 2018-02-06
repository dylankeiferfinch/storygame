package readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import faults.BadResourceFilePatternFault;
import util.Dfl;

public class Reader
{
	private static final String DEFAULT_FILE_TYPE = ".resource";
	private static final String END_TAG = "END";
	private File f;
	private Scanner s;
	private ArrayList<Line> lines;
	private int position;
	private Line nextLine;

	/**
	 * Creates a new Reader that will read a standardly formatted resource file.
	 * 
	 * @param fileToRead
	 *            The location of the file to read. (It is assume this file is in the
	 *            "resources" folder.)
	 * @throws FileNotFoundException
	 */
	public Reader(String fileToRead)
	{
		if (!fileToRead.contains("."))
		{
			fileToRead += DEFAULT_FILE_TYPE;
		}

		f = new File(new File("").getAbsolutePath() + "/src/resources/" + fileToRead);
		FileReader fr = null;
		
		try {
			fr = new FileReader(f);} 
		catch (FileNotFoundException e) {
			e.printStackTrace();}
		
		BufferedReader br = new BufferedReader(fr);
		lines = new ArrayList<Line>(100);
		
		String line = null;
		
		try {line = cleanLine(br.readLine());} 
		catch (IOException e) {e.printStackTrace();} 
		
		while(line != null)
		{
			if (!line.equals(""))
			lines.add(new Line(line));
			
			try {
				line = cleanLine(br.readLine());} 
			catch (IOException e) {
				e.printStackTrace();
				break;}
		}

		nextLine = getNextLine();
		position = 0;
	}
	
	private Line getNextLine()
	{
		if (position < lines.size())
		{
			return lines.get(position);
		}
		
		return null;
	}
	
	public String readAllUnderTag(String tag)
	{
		boolean found = this.gotoTag(tag);
		
		if (!found)
			throw new BadResourceFilePatternFault("Could not find tag: " + tag);
		
		String out = "";
		
		while (this.hasNextInTag())
		{
			out += this.next().getData() + "\n";
		}
		
		if (out.length() > 0)
			out = Dfl.cutString(out, 1);
		
		return out;
	}

	/**
	 * Searches for a line of the form "# tag".
	 * 
	 * @param tag
	 *            The tag to look for.
	 * @return True if the tag is found and the Reader is ready to read after it.
	 *         False, otherwise.
	 */
	public boolean gotoTag(String tag)
	{
		if (tag.equals(END_TAG))
		{
			return false;
		}

		for (int i = 0; i < lines.size(); i++)
		{
			String curLine = lines.get(i).getRaw();
			
			if (curLine.equals("# " + tag) && i + 1 < lines.size())
			{
				position = i + 1;
				nextLine = getNextLine();
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the next valid line of the resource. A valid line is one that is
	 *  non-empty and one that does not start with a tag declaration.
	 *  
	 * Comments (denoted by a percent sign) and at-tags (denoted with at signs) 
	 *  are stripped when getting the next line.
	 * 
	 * @return The next valid line of the resource, or null if there is no next valid
	 *         line.
	 */
	public Line nextInTag()
	{	
		if (nextLine.isTag())
		{
			return null;
		}
		
		Line out = nextLine;
		position++;
		nextLine = getNextLine();
		return out;
	}
	
	public Line peek()
	{
		return nextLine;
	}
	
	public boolean hasNext()
	{
		return nextLine != null;
	}
	
	public boolean hasNextInTag()
	{
		if (position >= lines.size())
		{
			return false;
		}
		
		if (nextLine.isTag())
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Starts the Reader at the beginning of the resource.
	 */
	public void gotoStart()
	{
		position = 0;
		nextLine = getNextLine();
	}
	
	/**
	 * Gets the next non-empty line of the resource.
	 * 
	 * Comments (denoted by a percent sign) and at-tags (denoted with at signs) 
	 *  are stripped when getting the next line.
	 * 
	 * @return The next non-empty line of the resource, or null, if there are 
	 *  no non-empty line remaining.
	 */
	public Line next()
	{
		Line out = nextLine;
		position++;
		nextLine = getNextLine();
		return out;
	}

	public String cleanLine(String line)
	{
		if (line == null)
			return null;
		
		int firstPercent = line.indexOf('%');

		if (firstPercent >= 0)
		{
			line = line.substring(0, firstPercent);
		}

		for (int firstAt = line.indexOf('@'); firstAt >= 0; firstAt = line.indexOf('@'))
		{
			String beforeAt = line.substring(0, firstAt);
			String rest = line.substring(firstAt);

			int firstSpace = rest.indexOf(' ');

			if (firstSpace < 0)
			{
				line = beforeAt;
			}

			else
			{
				line = beforeAt + rest.substring(firstSpace);
			}
		}

		return line.trim();
	}

	public File getFile()
	{
		return f;
	}
}

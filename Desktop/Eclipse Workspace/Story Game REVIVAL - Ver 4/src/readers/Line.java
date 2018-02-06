package readers;

public class Line
{
	private String line;
	private String type;
	private String data;
	
	public Line(String line)
	{
		this.line = line;
		this.data = line;
		this.type = "";
		
		if (line.charAt(0) == '!')
		{
			int firstSpace = line.indexOf(' ');
			
			if (firstSpace > 0)
			{
				type = line.substring(1, firstSpace);
				data = line.substring(firstSpace).trim();
			}
		}
		
		else if (line.charAt(0) == '&')
		{
			type = "&";
			data = line.substring(1).trim();
		}
		
		else if (line.charAt(0) == '#')
		{
			type = "#";
			data = line.substring(1).trim();
		}
		
		if (data.equals("~"))
			data = "";
	}
	
	public String getRaw()
	{
		return line;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getData()
	{
		return data;
	}
	
	public boolean isTag()
	{
		return type.equals("#");
	}
	
	public boolean isSubTag()
	{
		return type.equals("&");
	}
	
	public boolean isSubTag(String subTag)
	{
		return isSubTag() && data.equals(subTag);
	}
}

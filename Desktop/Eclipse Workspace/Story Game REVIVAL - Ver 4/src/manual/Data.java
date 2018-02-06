package manual;

public abstract class Data
{
	private String title;
	protected Folder parent;
	
	public Data(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}
	
	public void setParent(Folder f)
	{
		parent = f;
	}
	
	public Folder getParent()
	{
		return parent;
	}
	
	public String path()
	{
		String location = "";
		
		for(Data cur = this; cur != null; cur = cur.getParent())
		{
			location = " -> " + cur.getTitle() + location;
		}
		
		location = location.substring(4);
		
		return location;
	}
	
	public Data getSubData(String title)
	{
		if (title.matches(".*[a-z].*"))
		{
			return this.getSubPage(title);
		}
		
		else
		{
			return this.getSubFolder(title);
		}
	}
	
	public abstract String[] getAllPaths();
	public abstract Folder getSubFolder(String title);
	public abstract Page getSubPage(String title);
	public abstract String[] getSubFolderNames();
	public abstract String[] getSubPageNames();
}

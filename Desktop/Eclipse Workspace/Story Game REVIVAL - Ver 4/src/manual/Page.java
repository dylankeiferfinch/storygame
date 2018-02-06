package manual;

import java.util.ArrayList;

public class Page extends Data
{
	private String contents;
	
	public Page(String title, String contents)
	{
		super(title);
		
		this.contents = contents;
	}
	
	public String getContents()
	{
		return contents;
	}
	
	public String toString()
	{
		return this.path() + "\n\n" + contents + "\n";
	}
	
	public String[] getAllPaths()
	{
		ArrayList<String> ret = new ArrayList<String>();
		
		ret.add("(PARENT)");
		ret.addAll(parent.getAllPaths("(PARENT) -> "));
		
		return ret.toArray(new String[0]);
	}

	@Override
	public Folder getSubFolder(String title)
	{
		return title.equals("(PARENT)") ? this.getParent() : null;
	}

	@Override
	public Page getSubPage(String title)
	{
		return null;
	}

	@Override
	public String[] getSubFolderNames()
	{
		return new String[] {"(PARENT)"};
	}

	@Override
	public String[] getSubPageNames()
	{
		return new String[0];
	}
}

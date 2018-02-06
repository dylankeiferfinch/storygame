package manual;

import java.util.ArrayList;
import java.util.HashMap;

public class Folder extends Data
{
	private HashMap<String, Folder> folders;
	private HashMap<String, Page> pages;
	
	public Folder(String title, Data... subs)
	{
		super(title);
		
		folders = new HashMap<String, Folder>();
		pages = new HashMap<String, Page>();
		
		for (Data d : subs)
		{
			d.setParent(this);
			
			if (d instanceof Folder)
			{
				folders.put(d.getTitle(), (Folder) d);
			}
			
			else if (d instanceof Page)
			{
				pages.put(d.getTitle(), (Page) d);
			}
		}
	}
	
	@Override
	public void setParent(Folder f)
	{
		super.setParent(f);
		folders.put("(PARENT)", f);
	}
	
	public String toString()
	{
		String out = "";
		
		if (!folders.isEmpty())
		{
			out += this.path() + "\n\nFolders:\n";
		
			for (String s : folders.keySet())
				out += s + "\n";
		}
		
		if (!pages.isEmpty())
		{
			out += "\nPages:\n";
			
			for (String s : pages.keySet())
				out += s + "\n";
		}
		
		return out;
	}
	
	public String getTree()
	{
		if (this.getParent() == null)
			return this.getTree(0);
		
		return "(PARENT) - " + this.getParent().getTitle() + "\n|->" + this.getTree(1);
	}
	
	public String getTree(int level)
	{
		String out = this.getTitle();
		
		String spaces = "";
		
		for (int i = 0; i < level; i++)
			spaces += "|  ";
		
		for (Folder f : folders.values())
		{
			if (f != this.getParent())
				out += "\n" + spaces + "|->" + f.getTree(level + 1);
		}
		
		for (Page p : pages.values())
		{
			out += "\n" + spaces + "|->" + p.getTitle();
		}
		
		return out;
	}
	
	public String[] getAllPaths()
	{
		return this.getAllPaths("").toArray(new String[0]);
	}
	
	public ArrayList<String> getAllPaths(String pathSoFar)
	{
		ArrayList<String> ret = new ArrayList<String>();
		
		ret.addAll(this.getAllLowerPaths(pathSoFar));
		ret.addAll(this.getAllHigherPaths(pathSoFar));
		
		return ret;
	}
	
	public ArrayList<String> getAllHigherPaths(String pathSoFar)
	{
		ArrayList<String> ret = new ArrayList<String>();
		
		if (this.getParent() != null)
		{
			ret.add(pathSoFar + "(PARENT)");
			
			for (Folder f : parent.folders.values())
			{
				if (f != this.getParent() && f != this)
				{
					ret.add(pathSoFar + "(PARENT) -> " + f.getTitle());
				}
			}
			
			for (Page p : parent.pages.values())
			{
				ret.add(pathSoFar + "(PARENT) -> " + p.getTitle());
			}
			
			for (Folder f : parent.folders.values())
			{
				if (f != this.getParent() && f != this)
				{
					ret.addAll(f.getAllLowerPaths(pathSoFar + "(PARENT) -> " + f.getTitle() + " -> "));
				}
			}
			
			ret.addAll(parent.getAllHigherPaths(pathSoFar + "(PARENT) -> "));
		}
		
		return ret;
	}
	
	public ArrayList<String> getAllLowerPaths(String pathSoFar)
	{
		ArrayList<String> ret = new ArrayList<String>();
		
		for (Folder f : folders.values())
		{
			if (f != this.getParent())
			{
				ret.add(pathSoFar + f.getTitle());
			}
		}
		
		for (Page p : pages.values())
		{
			ret.add(pathSoFar + p.getTitle());
		}
		
		for (Folder f : folders.values())
		{
			if (f != this.getParent())
			{
				ret.addAll(f.getAllLowerPaths(pathSoFar + f.getTitle() + " -> "));
			}
		}
		
		return ret;
	}
	
	public Folder getSubFolder(String title)
	{
		return folders.get(title);
	}
	
	public Page getSubPage(String title)
	{
		return pages.get(title);
	}

	@Override
	public String[] getSubFolderNames()
	{
		return folders.keySet().toArray(new String[0]);
	}

	@Override
	public String[] getSubPageNames()
	{
		return pages.keySet().toArray(new String[0]);
	}
}

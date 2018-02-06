package zzz_trash;

public class TaggedInputPart extends InputPart
{
	private InputPartTag[] tags;
	private String[] handleTags;
	
	public TaggedInputPart(String string)
	{
		super(string);
		
		this.tags = new InputPartTag[0];
		this.handleTags = new String[0];
	}
	
	public void addTag(String name, InputPart value)
	{
		InputPartTag[] temp = new InputPartTag[tags.length + 1];
		
		for (int i = 0; i < tags.length; i++)
		{
			temp[i] = tags[i];
		}
		
		temp[tags.length] = new InputPartTag(name, value);
		tags = temp;
	}
	
	public void addHandleTag(String name)
	{
		String[] temp = new String[handleTags.length + 1];
		
		for (int i = 0; i < handleTags.length; i++)
		{
			temp[i] = handleTags[i];
		}
		
		temp[handleTags.length] = name;
		handleTags = temp;
	}
	
	public InputPartTag[] getTags()
	{
		return tags;
	}
	
	public InputPartTag getTag(String id)
	{
		for (InputPartTag tag : tags)
			if (tag.getName().equals(id))
				return tag;
		
		return null;
	}
	
	public boolean hasTag(String id)
	{
		for (InputPartTag tag : tags)
			if (tag.getName().equals(id))
				return true;
		
		return false;
	}
	
	public String toString()
	{
		String out = string + "[";
		
		for (String s : handleTags)
		{
			out += "~" + s + "; ";
		}
		
		for (int i = 0; i < tags.length; i++)
		{
			InputPartTag t = tags[i];
			
			InputPart part = t.getValue();
			
			out += t.getName() + " = " + (part instanceof NakedInputPart ? "\"" + part + "\"; " : part + "; ");
			
			if (i == tags.length - 1)
				out = out.substring(0, out.length() - 1);
		}
		
		return out + "]";
	}
}

package base;

import java.awt.Color;

public class OutputPart
{
	public static int NONE = 0, ITALICS = 1, BOLD = 2;
	
	private String text;
	private Color color;
	private int formatting;
	private int numNewLines;
	
	public OutputPart(String text, Color color, int formatting)
	{
		this(text, color, formatting, 0);
	}
	
	public OutputPart(String text, Color color, int formatting, int numNewLinesAfter)
	{
		this.text = text;
		this.color = color;
		this.formatting = formatting;
		this.numNewLines = numNewLinesAfter;
	}
	
	public String getText()
	{
		return text;
	}
	
	public int getNumNewLines()
	{
		return numNewLines;
	}
	
	public String getTextAndNewLines()
	{
		String out = text;
		
		for (int i = 0; i < numNewLines; i++)
			out += "\n";
		
		return out;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public boolean isItalics()
	{
		return formatting == 1;
	}
	
	public boolean isBold()
	{
		return formatting == 2;
	}
}

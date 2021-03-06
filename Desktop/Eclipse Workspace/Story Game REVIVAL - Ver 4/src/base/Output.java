package base;

import java.awt.Color;
import java.util.Arrays;
import java.util.Iterator;

import input.Input;
import param_stuff.ObjParam;
import param_stuff.Param;
import param_stuff.StaticParam;
import process_stuff.Function;
import readers.Reader;
import util.Dfl;

public class Output implements Iterable<OutputPart>
{
	public static int NONE = 0, ITALICS = 1, BOLD = 2;
	
	private OutputPart[] parts;
	private boolean containsInput;
	
	public Output(OutputPart... parts)
	{
		this.parts = Arrays.copyOf(parts, parts.length);
		this.containsInput = false;
	}
	
	public Output(String text, Color color, int formatting, int newLines)
	{
		this(new OutputPart(text, color, formatting, newLines));
	}
	
	public Output(String text, Color color, int formatting)
	{
		this(text, color, formatting, 0);
	}
	
	public void addParts(OutputPart... partsToAdd)
	{
		OutputPart[] newParts = new OutputPart[parts.length + partsToAdd.length];
		
		for (int i = 0; i < parts.length; i++)
			newParts[i] = parts[i];
		
		for (int i = 0; i < partsToAdd.length; i++)
			newParts[i + parts.length] = partsToAdd[i];
		
		parts = newParts;
	}
	
	public void addRawPart(String text, Color color, int formatting, int newLines)
	{
		this.addParts(new OutputPart(text, color, formatting, newLines));
	}
	
	public void addRawPart(String text, Color color, int formatting)
	{
		this.addRawPart(text, color, formatting, 0);
	}
	
	public void addOutput(Output output)
	{
		addParts(output.getParts());
	}
	
	public void setConatinsInput(boolean b)
	{
		containsInput = b;
	}
	
	public boolean containsInput()
	{
		return containsInput;
	}
	
	public OutputPart[] getParts()
	{
		return Arrays.copyOf(parts, parts.length);
	}
	
	public static Output genFullOutput(String lastInput, String output)
	{
		return new Output(new OutputPart("> " + lastInput, Color.BLACK, OutputPart.NONE, 2),
						  new OutputPart(output, Color.GRAY, OutputPart.ITALICS));
	}
	
	public static Output genGenericOutput(String output)
	{
		return new Output(new OutputPart(output, Color.GRAY, OutputPart.ITALICS));
	}
	
	public static Output genGenericError(String output)
	{
		return new Output(new OutputPart(output, Color.RED, OutputPart.ITALICS));
	}
	
	public static Output genBadTypeError(Function func, String[] args, int firstWrongArg, Param correctType, String correctUsage)
	{	
		Output out = new Output();
		out.addParts(new OutputPart("> "  + func.getName(), Color.BLACK, OutputPart.NONE));
		
		for (int i = 0; i < args.length; i++)
		{
			String curArg = args[i];
			
			if (curArg.split(" ").length > 1)
				curArg = "\"" + curArg + "\"";
			
			if (i != firstWrongArg)
				out.addParts(new OutputPart(" " + curArg, Color.BLACK, OutputPart.NONE));
			
			else
				out.addParts(new OutputPart(" " + curArg, Color.ORANGE, OutputPart.BOLD));
		}
		
		String correctTypeInfo = "";
		
		if (correctType instanceof StaticParam)
			correctTypeInfo = "The highlighted argument is not variable and should read exactly: \"" + correctType.getName() + "\".";
		
		else
		{
			ObjParam type = (ObjParam) correctType;
			
			correctTypeInfo = "The highlighted argument must be of type <" + correctType.getName() + ">.\nThis type of argument is " + type.getDesc() + ".";
		}
		
		out.addParts(new OutputPart("\n\nINPUT ERROR: Bad argument type.\n" + correctTypeInfo + "\n\n" + correctUsage, Color.RED, OutputPart.ITALICS));
		
		out.setConatinsInput(true);
		
		return out;
	}
	
	public static Output genCommandOutput(String output)
	{
		return new Output(new OutputPart(output, Color.BLUE, OutputPart.ITALICS));
	}
	
	public static Output genCommandError(String output)
	{
		return new Output(new OutputPart(output, Color.ORANGE, OutputPart.ITALICS));
	}
	
	public static Output combineWithInput(String lastInput, Output restOfOutput)
	{
		OutputPart[] newParts = new OutputPart[restOfOutput.parts.length + 1];
		
		newParts[0] = new OutputPart("> " + lastInput, Color.BLACK, OutputPart.NONE, 2);
		
		for (int i = 1; i < newParts.length; i++)
		{
			newParts[i] = restOfOutput.parts[i - 1];
		}
		
		return new Output(newParts);
	}
	
	public static Output getStartingOutput()
	{
		Output startingOutput = new Output(new OutputPart(" > (During the game, your last input will appear here.)", Color.BLACK, OutputPart.NONE, 2),
				  new OutputPart("Down here, you'll see the output that was generated by your last input. You may find additional changes in the prompt section, above where you can input text, based on your input.", Color.GRAY, OutputPart.ITALICS, 2));

		Reader r = new Reader("on_start");
		
		r.gotoTag("ON START");
		
		String firstInput = "";
		
		while(r.hasNextInTag())
			firstInput += r.next().getData() + " & ";
		
		if (firstInput.length() > 3)
		{
			Input i = new Input(Dfl.cutString(firstInput, 3));
			
			startingOutput.addParts(new OutputPart("The following processes were run becuase they were listed in the ON START resource file:", Color.BLUE, Output.BOLD, 2));
			startingOutput.addOutput(i.getOutput());
		}
		
		return startingOutput;	
	}

	@Override
	public Iterator<OutputPart> iterator()
	{
		return new OutputIterator();
	}
	
	private class OutputIterator implements Iterator<OutputPart>
	{
		int index;
		
		public OutputIterator()
		{
			index = 0;
		}
		
		@Override
		public boolean hasNext()
		{
			return index < parts.length;
		}

		@Override
		public OutputPart next()
		{
			index++;
			return parts[index - 1];
		}
	}
}

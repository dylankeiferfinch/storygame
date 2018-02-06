package input;

import java.util.Stack;

import util.Dfl;

public class Log
{
	private static int in = 0;
	private static Stack<Long> times = new Stack<Long>();
	
	public static void log(String str)
	{
		Dfl.out(getIn() + str);
	}
	
	public static void begin(String str)
	{
		log((in == 0 ? "\n" : "") +"BEGIN " + str);
		incIn();
		
		times.push(System.nanoTime());
	}
	
	public static void end(String str)
	{
		double runTime = (System.nanoTime() - times.pop()) / 1000000.0;
		
		decIn();
		log("END " + str);
		log("> Completed in " + runTime + "ms.");
	}
	
	private static String getIn()
	{
		String out = "";
		
		if (in > 0)
			for (int i = 0; i < in * 3; i++)
				out += " ";
		
		return out;
	}
	
	public static String toString(String[] strs)
	{
		if (strs == null)
			return "<null set>";
		 
		String out = "{";
		
		for (String str : strs)
			out += str + "} {";
		
		if (!out.equals("{"))
			out = out.substring(0, out.length() - 2);
		
		else out += "}";
		
		return out;
	}
	
	public static String toString(Object[] objs)
	{
		if (objs == null)
			return "<null set>";
		
		String out = "{";
		
		for (Object obj : objs)
			if (obj == null)
				out += "NULL} {";
			else
				out += obj.toString() + "} {";
		
		if (!out.equals("{"))
			out = out.substring(0, out.length() - 2);
		
		else 
			out += "}";
		
		return out;
	}
	
	public static void incIn()
	{
		in++;
	}
	
	public static void decIn()
	{
		in--;
	}
}

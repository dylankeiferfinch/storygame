package param_stuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import input.Arg;
import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.Log;
import input.StaticArg;
import input.VariableArg;

public class ParamSetEval
{
	private double matches;
	private InputError firstError;
	private ParamType[] finalParams;
	private ParamSet set;
	private Arg[] parts;
	
	public ParamSetEval(ParamSet set, Arg[] parts)
	{
		Log.begin("testing argset: " + set.toString());
		
		this.firstError = null;
		this.set = set;
		matches = 0;
		Arg badPart = null;
		
		if (parts.length > set.getArgs().length)
		{
			ArrayList<Arg> list = new ArrayList<Arg>();
			
			for (Arg a : parts)
				list.add(a);
			
			int lastJ = 0;
			
			for (int i = 0; i < set.getArgs().length; i++)
			{
				if (set.getArgs()[i] instanceof StaticParam)
				{
					String arg = set.getArgs()[i].getId();
					
					for (int j = lastJ; j < list.size(); j++)
					{
						if (list.get(j).getFocus().equals(arg) && (list.get(j) instanceof StaticArg || list.get(j) instanceof VariableArg))
						{
							int k = j - 1;
							while (k > 0 && list.get(k) instanceof VariableArg && list.get(k - 1) instanceof VariableArg && k + 1 > i)
							{
								list.set(k, new VariableArg(list.get(k - 1).getFocus() + " " + list.get(k)));
								list.remove(k - 1);
								k--;
							}

							lastJ = k + 1;
							break;
						}
					}
				}
			}
			
			if (matches == 0)
				matches = -0.05;
			
			parts = list.toArray(new Arg[0]);
		}
		
		while (parts.length > set.getArgs().length && parts.length > 1 && parts[parts.length - 1] instanceof VariableArg && parts[parts.length - 2] instanceof VariableArg)
		{
			Arg[] temp = new Arg[parts.length - 1];
			
			for (int i = 0; i < temp.length - 1; i++)
				temp[i] = parts[i];
			
			temp[temp.length - 1] = new VariableArg(parts[temp.length - 1].getFocus() + " " + parts[temp.length].getFocus());
			
			parts = temp;
			
			if (matches == 0)
				matches = -0.05;
		}
		
		if (matches == -0.05)
			Log.log("Shortended args to: " + Log.toString(parts));
		
		this.parts = parts;
		this.finalParams = new ParamType[parts.length];
				
		for (int i = 0; i < parts.length; i++)
		{
			if (i < set.getArgs().length)
			{
				Log.begin("testing arg: " + (i + 1) + " - "+ set.getArgs()[i]);
				
				ParamType type = set.getArgs()[i].parse(parts[i]);
				
				if (parts[i] instanceof HardTypedArg)
				{
					if (parts[i].getFocus().equals(set.getArgs()[i].getId()))
					{
						matches++;
						Log.log("+1 match becuase of correct HardTypedArg type.");
					}
					
					else
					{
						badPart = parts[i];
						Log.log("+0 matches becuase of wrong HardTypedArg type.");
					}
				}
				
				else
				{
					if (type != null)
					{
						matches++;
						Log.log("+1 match becuase of correct VariableArg type.");
					}
					
					else
					{
						badPart = parts[i];
						Log.log("+0 matches becuase of wrong VariableArg type.");
					}
				}
				
				InputError curErr = Input.getError();
				
				if (curErr != null)
				{
					if (firstError == null)
						firstError = curErr;
				}
				
				Log.log(type == null? "Did not find a value." : "Found valid value: " + type.toString());
				Log.log(firstError == null ? "There was no error for this argument." : "An error was found with this argument.");
				Log.end("testing arg: " + (i + 1) + " - " + set.getArgs()[i]);
				
				finalParams[i] = type;
			}
			
			else
				break;
		}
		
		if (set.getArgs().length != parts.length)
		{
			if (badPart == null && parts.length > set.getArgs().length)
				badPart = parts[set.getArgs().length];
			
			Input.setError("BAD INPUT: Incorrect number of arguments.", "Try removing the highlighted part. It did not fit the assumed correct argument set.", badPart);
			firstError = Input.getError();
		}

		double takeAwayAmt = 1.5 * Math.abs(set.getArgs().length - parts.length);
		
		matches -= takeAwayAmt;
		Log.log("-" + takeAwayAmt + " match(es) for wrong argument lengths.");
		
		Log.log("Final matches: " + matches + " of " + set.getArgs().length);
		Log.end("testing argset: " + set.toString());
		
		this.parts = parts;
	}
	
	public boolean hadError()
	{
		return firstError != null;
	}
	
	public InputError getFirstError()
	{
		return firstError;
	}
	
	public double getOffBy()
	{
		return set.getArgs().length - matches;
	}
	
	public ParamType[] getFinalParams()
	{
		return finalParams;
	}
	
	public ParamSet getArgSet()
	{
		return set;
	}
	
	public Arg[] getArgs()
	{
		return parts;
	}
}

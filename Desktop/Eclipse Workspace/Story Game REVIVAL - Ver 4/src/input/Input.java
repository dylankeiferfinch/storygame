package input;

import java.awt.Color;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import base.GameBase;
import base.GameRule;
import base.Output;
import base.OutputPart;
import param_stuff.ObjParam;
import param_stuff.ParamSet;
import param_stuff.ParamSetEval;
import param_stuff.ParamType;
import process_stuff.Process;

public class Input
{
	private String rawInput;
	private String procName;
	private String rawRest;
	private Output output;
	private Object[] results;
	private static InputError error;
	private ParamSet bestSet;
	private ParamSetEval[] evals;
	private InputTag[] tags;
	private Arg procArg;
	private Arg tagsArg;
	private Arg[] args;
	public static boolean doInput = true;
	private InputError errorWhenRan;
	public String old;
	public boolean corrected;
	
	public Input(String input)
	{
		rawInput = input;
		tags = new InputTag[0];
		
		Log.begin("input: " + input);
		
		input = input.trim();
		
		String[] inputs = splitProcs(input);
		
		if (inputs.length > 1)
		{
			Log.log("Found " + inputs.length + " seperate processes: " + Log.toString(inputs));
			
			output = new Output();
			output.addParts(new OutputPart("BEGIN PROCESS CHAINING\nEach seperate input (sperated by the \"&\") is shown below.", Color.CYAN, Output.BOLD, 2));
			
			int amt = 0;
			
			for (String s : inputs)
			{
				amt++;
				
				Input i = new Input(s);
				
				output.addOutput(i.output);
				output.addParts(new OutputPart("", Color.BLACK, Output.NONE, 2));
				
				if (i.errorWhenRan != null)
				{
					break;
				}
			}
			
			output.addParts(new OutputPart("END PROCESS CHAINING\nRan " + amt + " of " + inputs.length + " processes." + (amt != inputs.length ? "\nAn error occured so execution stopped." : ""), Color.CYAN, Output.BOLD));
			
			Log.end("input " + input);
			
			return;
		}
		
		old = input;
		
		input = InputAutocorrect.getReplacement(input);
		
		if (!old.equals(input))
			corrected = true;
		
		input = this.addTags(input);
		
		Process proc = Process.startsWithProcess(input);
		procName = proc == null ? "nope" : proc.getName();
		rawRest = proc == null ? null : input.replaceFirst(procName, "");
	
		if (proc == null)
		{
			int firstSpace = input.indexOf(' ');
			
			if (firstSpace == -1)
			{
				procName = input.trim();
				rawRest = null;
			}
			
			else
			{
				procName = input.substring(0, firstSpace).trim();
				rawRest = input.substring(firstSpace + 1).trim();
			}
		}

		procArg = new VariableArg(procName);
		
		args = Arg.inputToArgs(rawRest);
		Log.log("Found arguments: " + Log.toString(args));
		
		
		ParamSetEval bestMatchSet = null;
		bestSet = null;
		
		if (proc == null)
			setError("INVALID PROCESS: That process does not exist.", "Use \"help\" for a list of valid processes.", procArg);
		
		Log.log(proc == null ? "No valid process found." : "Found valid process: " + proc.getName());
		
		if (proc != null && proc.canUseInLoc(GameBase.getLocation()))
		{
			Log.log("Found argument sets: " + Log.toString(proc.getArgSets()));
			
			evals = new ParamSetEval[proc.getArgSets().length];
			
			double bestOffBy = 100;
			
			for (int i = 0; i < proc.getArgSets().length; i++)
			{
				evals[i] = new ParamSetEval(proc.getArgSets()[i], args);
				
				if (evals[i].getOffBy() < bestOffBy)
				{
					bestOffBy = evals[i].getOffBy();
					bestMatchSet = evals[i];
				}
			}
			
			Log.log("Best ArgSet: " + bestMatchSet.getArgSet().toString() + " (off by " + bestMatchSet.getOffBy() + ")");
			
			bestSet = bestMatchSet.getArgSet();
			ParamType[] params = bestMatchSet.getFinalParams();
			
			Log.log("Processed arguments to: " + Log.toString(params));
			Log.log(bestMatchSet.hadError() ? "There was an error." : "There were no errors.");
			
			if (!bestMatchSet.hadError() && doInput)
			{
				Log.log("Running process.");
				this.args = bestMatchSet.getArgs();
				
				if (!this.hasTag("SuppressInputRepeat"))
				{
					output = new Output(new OutputPart("> " + procName + " ", Color.BLACK, OutputPart.NONE));
					output.addOutput(buildInput(null));
					output.addParts(new OutputPart("", Color.BLACK, OutputPart.NONE, 2));
					output.addOutput(proc.beginExecution(params));
				}
				
				else
				{
					output = proc.beginExecution(params);
				}
				
				Log.log("Sent output to screen.");
				Log.end("input: " + input);
				
				return;
			}
			
			else if (!doInput)
			{
				Log.log("Didn't run process.");
				Log.end("input: " + input);
				return;
			}
		}
		
		Log.log("Sent error to screen.");
		Log.end("input: " + input);
		
		output = buildError(bestMatchSet);
	}
	
	public static String[] splitProcs(String input)
	{
		if (input == null || input.equals(""))
			return new String[] {""};
		
		input = input.trim();
		
		ArrayList<String> procs = new ArrayList<String>();
		String curProcRaw = "";
		boolean inQuotes = false;
		int brackets = 0;
		
		for (int i = 0; i < input.length(); i++)
		{
			char curChar = input.charAt(i);
			char charAfter = ' ';
			
			if (i + 1 < input.length())
				charAfter = input.charAt(i + 1);
			
			if (inQuotes && brackets == 0)
			{
				if (curChar == '\'' && charAfter == '\'')
				{
					curProcRaw += "'";
					i++;
				}
				
				else
				{
					curProcRaw += curChar;
					
					if (curChar == '\'')
					{	
						inQuotes = false;
					}
				}
			}
			
			else if (inQuotes)
			{
				curProcRaw += curChar;
				
				if (curChar == '\'')
				{	
					if (charAfter == '\'')
					{
						curProcRaw += '\'';
						i++;
					}
					
					else
						inQuotes = false;
				}
				
				
			}
			
			else
			{
				if (curChar == '&' && brackets == 0)
				{
					procs.add(curProcRaw);
					curProcRaw = "";
					continue;
				}
				
				curProcRaw += curChar;
				
				if (curChar == '\'')
				{
					inQuotes = true;
				}
				
				else if (curChar == '[')
				{
					brackets++;
				}
				
				else if (curChar == ']')
				{
					brackets--;
				}
			}
		}
		
		if (!curProcRaw.equals(""))
			procs.add(curProcRaw);
		
		return procs.toArray(new String[0]);
	}
	
	public ParamSet getBestSet()
	{
		return bestSet;
	}
	
	public Arg[] getParts()
	{
		return args;
	}
	
	public ParamSetEval[] getEvals()
	{
		return evals;
	}
	
	private Output buildError(ParamSetEval bestSetEval)
	{
		InputError firstError = null;
		Arg failedPart = null;
		ParamSet bestSet = null;
		
		if (bestSetEval != null)
		{
			firstError = bestSetEval.getFirstError();
			failedPart = firstError.getFailedPart();
			bestSet = bestSetEval.getArgSet();
		}
		
		if (firstError == null)
		{
			if (Process.getProcess(procName) == null)
				firstError = new InputError("INVALID FUNCTION NAME: Bad function name.");
			
			else
				firstError = new InputError("BAD FUNCTION USE LOCATION: That function can't be used in the current location.");
		}
		
		this.errorWhenRan = firstError;
		
		Process func = Process.getProcess(procName);
		
		Output out = null;
		
		out = new Output("> ", Color.BLACK, Output.NONE);
		
		if (func == null || !func.canUseInLoc(GameBase.getLocation()))
			out.addRawPart(procName + " ", Color.ORANGE, Output.BOLD);
		
		else
			out.addRawPart(procName + " ", Color.BLACK, Output.NONE);
		
		out.addOutput(buildInput(failedPart));
		
		if (GameRule.getBoolRule("SoftErrorMessages").getValue())
			out.addRawPart("Soft error.", Color.CYAN, Output.NONE);
		
		if (func == null || !func.canUseInLoc(GameBase.getLocation()))
			out.addRawPart("\n\nAn error occured at the Function. It is highlighted.", Color.RED, Output.ITALICS);
		
		else
		{
			String failedPartTrace = findFailedPart(failedPart);
			out.addRawPart("\n\nAn error occured" + (failedPartTrace == null ? "." : " at " + failedPartTrace + ". It is highlighted."), Color.RED, Output.ITALICS);
		}
		
		out.addRawPart("\n\n" + firstError.getErrorCode() + ": " + firstError.getHardErrorMsg() + "\n" + firstError.getSoftErrorMsg(), Color.RED, Output.ITALICS);
		
		if (func != null && func.canUseInLoc(GameBase.getLocation()))
		{
			if (func.getArgSets().length == 1)
			{
				String bestGuessLine = "\n\nCorrect usage of \"" + procName + "\":\n" + procName + " " + bestSet.getCorrectUsage();
				out.addRawPart(bestGuessLine, Color.RED, Output.ITALICS);
			}
			
			else
			{
				String bestGuessLine = "\n\nBest guess for correct usage of \"" + procName + "\":\n" + procName + " " + bestSet.getCorrectUsage();
				out.addRawPart(bestGuessLine, Color.RED, Output.ITALICS);
			
				String otherPossUses = "\n\nOther possible uses of \"" + procName + "\":";
				
				ParamSet[] sets = func.getArgSets();
				
				for (ParamSet set : sets)
				{
					if (!set.equals(bestSet))
						otherPossUses += "\n" + procName + " " + set.getCorrectUsage();
				}
				
				out.addRawPart(otherPossUses, Color.RED, Output.ITALICS);
			}
		}
		
		return out;
	}
	
	private Output buildInput(Arg failedPart)
	{
		Output out = new Output(new OutputPart("", Color.BLACK, OutputPart.NONE));
		
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equals(failedPart))
			{
				out.addParts(new OutputPart(args[i].toString() + " ", Color.ORANGE, OutputPart.BOLD));
			}
			
			else
			{
				if (args[i] instanceof HardTypedArg)
				{
					HardTypedArg taggedPart = (HardTypedArg) args[i];
					
					out.addParts(new OutputPart(taggedPart.getFocus() + "[", Color.BLACK, OutputPart.NONE));
					out.addOutput(buildInput(taggedPart, failedPart));
					out.addParts(new OutputPart("] ", Color.BLACK, OutputPart.NONE));
				}
				
				else
				{
					out.addParts(new OutputPart(args[i].toString() + " ", Color.BLACK, OutputPart.NONE));
				}
			}
		}
		
		if (corrected)
			out.addParts(new OutputPart("(Autocorrected from: " + old + ")", Color.BLACK, Output.ITALICS));
		
		return out;
	}
	
	private Output buildInput(Arg curPart, Arg failedPart)
	{
		Output out = new Output(new OutputPart("", Color.BLACK, OutputPart.NONE));
		
		if (curPart instanceof HardTypedArg)
		{
			HardTypedArg curTaggedPart = (HardTypedArg) curPart;
			ArgTag[] tags = curTaggedPart.getTags();
			
			for (int i = 0; i < tags.length; i++)
			{
				out.addParts(new OutputPart(tags[i].getName() + " = ", Color.BLACK, OutputPart.NONE));
				
				if (tags[i].getValue() == failedPart)
				{
					out.addParts(new OutputPart(failedPart.toString(), Color.ORANGE, OutputPart.BOLD));
				}
				
				else
				{
					if (tags[i].getValue() instanceof HardTypedArg)
					{
						HardTypedArg taggedPart = (HardTypedArg) tags[i].getValue();
						
						out.addParts(new OutputPart(taggedPart.getFocus() + "[", Color.BLACK, OutputPart.NONE));
						out.addOutput(buildInput(taggedPart, failedPart));
						out.addParts(new OutputPart("]", Color.BLACK, OutputPart.NONE));
					}
					
					else
					{
						out.addParts(new OutputPart(tags[i].toString(), Color.BLACK, OutputPart.NONE));
					}
				}
				
				out.addParts(new OutputPart(";", Color.BLACK, OutputPart.NONE));
			}
		}
		
		return out;
	}
	
	private String findFailedPart(Arg failedPart)
	{
		if (failedPart == null)
			return null;
		
		else
		{
			for (int i = 0; i < args.length; i++)
			{
				if (args[i] == failedPart)
					return "Argument " + (i + 1);
				
				if (findFailedPart(args[i], failedPart) != null)
					return "Argument " + (i + 1) + ", " + findFailedPart(args[i], failedPart);
			}
		}
		
		return null;
	}
	
	private String findFailedPart(Arg curPart, Arg failedPart)
	{
		if (curPart instanceof HardTypedArg)
		{
			ArgTag[] tags = ((HardTypedArg) curPart).getTags();
			
			for (int i = 0; i < tags.length; i++)
			{
				if (tags[i].getValue() == failedPart)
					return "Tag " + (i + 1) + " (" + tags[i].getName() + ")";
				
				String failedText = findFailedPart(tags[i].getValue(), failedPart);
				
				if (failedText != null)
					return "Tag " + (i + 1) + " (" + tags[i].getName() + ")"+ ", " + failedText;
					
			}
		}
		
		return null;
	}
	
	private String addTags(String input)
	{
		if (input.charAt(0) != '[')
		{
			tagsArg = new VariableArg("");
			Log.log("Found no input tags.");
			return input;
		}
		
		if (!input.contains("]"))
		{
			tagsArg = new VariableArg(input);
			setError("INPUT PARSING ERROR: No end to input tags found.", "Make sure to end the input tegs with a \"]\".", tagsArg);
			Log.log("Found unending input tag section.");
			return "";
		}
		
		String tagsStr = input.substring(1, input.indexOf("]"));
		String[] tagsAsStrs = tagsStr.split(";");
		
		for (String s : tagsAsStrs)
			if (!s.trim().equals(""))
				addTag(new InputTag(s.trim()));
		
		Log.log("Added input tags: " + Log.toString(tags));
		
		if (input.indexOf("]") + 1 >= input.length())
			return "";
		
		return input.substring(input.indexOf("]") + 1).trim();
	} 
	
	private void addTag(InputTag t)
	{
		InputTag[] temp = new InputTag[tags.length + 1];
		
		for (int i = 0; i < tags.length; i++)
			temp[i] = tags[i];
		
		temp[temp.length - 1] = t;
		
		tags = temp;
	}
	
	public boolean hasTag(String tag)
	{
		for (InputTag t : tags)
			if (t.getName().equals(tag))
				return true;
		
		return false;
	}
	
	public String getFunctionName()
	{
		return procName;
	}
	
	public Output getOutput()
	{
		return output;
	}
	
	public Object[] getResults()
	{
		return results;
	}
	
	public static void setError(String hardErrorMsg, String softErrorMsg, Arg failedPart)
	{
		if (error == null)
		{
			Log.log("Set error to: \"" + hardErrorMsg + "\"");
			error = new InputError(hardErrorMsg, softErrorMsg, failedPart);
		}
		
		else
			Log.log("Failed to set error to: \"" + hardErrorMsg + "\"");
	}
	
	public static void setError(String hardErrorMsg, Arg failedPart)
	{
		setError(hardErrorMsg, null, failedPart);
	}
	
	public static void setError(String hardErrorMsg, String softErrorMsg)
	{
		setError(hardErrorMsg, softErrorMsg, null);
	}
	
	public static void setError(String hardErrorMsg)
	{
		setError(hardErrorMsg, null, null);
	}
	
	public static void setError(String errorCode, String hardErrorMsg, String softErrorMsg, String tip, Arg failedPart)
	{
		setError(new InputError(errorCode, hardErrorMsg, softErrorMsg, tip, failedPart));
	}
	
	public static void setError(InputError err)
	{
		if (error == null)
		{
			Log.log("Set error to: \"" + err.getHardErrorMsg() + "\"");
			error = err;
		}
		
		else
			Log.log("Failed to set error to: \"" + err.getHardErrorMsg() + "\"");
	}
	
	public static InputError getError()
	{
		InputError out = error;
		error = null;
		return out;
	}
}

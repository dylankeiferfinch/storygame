package input;

import param_stuff.ObjParam;

public class InputError
{	
	private String errorCode;
	private String hardErrorMsg;
	private String softErrorMsg;
	private String tip;
	private Arg failedPart;
	
	/**
	 * Creates a new Error generated by an invalid argument in input.
	 * 
	 * @param errorCode The error code, like "NO DEFAULT SOFT TYPE".
	 * @param hardErrorMsg A detailed debug error message.
	 * @param softErrorMsg A friendlier, more concise error message.
	 * @param tip An optional tip for how the user can prevent this type of error in the future.
	 * @param failedPart The argument or part of the argument that caused the error.
	 */
	public InputError(String errorCode, String hardErrorMsg, String softErrorMsg, String tip, Arg failedPart)
	{
		this.errorCode = errorCode;
		this.hardErrorMsg = hardErrorMsg;
		this.softErrorMsg = softErrorMsg;
		this.tip = tip;
		this.failedPart = failedPart;
		
		if (this.softErrorMsg == null)
			this.softErrorMsg = hardErrorMsg;
	}
	
	public InputError(String hardErrorMsg, String softErrorMsg, Arg failedPart)
	{
		this(hardErrorMsg.split(": ")[0], hardErrorMsg.split(": ")[1], softErrorMsg, null, failedPart);
	}
	
	public InputError(String hardErrorMsg, Arg failedPart)
	{
		this(hardErrorMsg, null, failedPart);
	}
	
	public InputError(String hardErrorMsg, String softErrorMsg)
	{
		this(hardErrorMsg, softErrorMsg, null);
	}
	
	public InputError(String hardErrorMsg)
	{
		this(hardErrorMsg, null, null);
	}
	
	public String getErrorCode()
	{
		return errorCode;
	}
	
	public String getHardErrorMsg()
	{
		return hardErrorMsg;
	}
	
	public String getSoftErrorMsg()
	{
		return softErrorMsg;
	}
	
	public String getTip()
	{
		return tip;
	}
	
	public Arg getFailedPart()
	{
		return failedPart;
	}
	
	public static InputError genNoDefaultHardTypeError(ObjParam obj, Arg failedPart)
	{
		String type = obj.getId();
		return new InputError("NO DEFAULT HARD TYPE VALUE", "You cannot declare the \"" + type + "\" type with empty brackets. There is no default value.", null, "You can use \"this =\" in the brackets to specifiy a value.", failedPart);
	}
}
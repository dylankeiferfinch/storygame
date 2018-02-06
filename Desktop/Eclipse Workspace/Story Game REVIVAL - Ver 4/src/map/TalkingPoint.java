package map;

public class TalkingPoint
{
	private String response;
	private ResponseOption[] options;
	
	public TalkingPoint(String response, ResponseOption[] options)
	{
		this.response = response;
		this.options = options;
	}
	
	public String getResponse()
	{
		return response;
	}
	
	public ResponseOption[] getOptions()
	{
		return options;
	}
}

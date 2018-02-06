package map;

public class ResponseOption
{
	private String text;
	private String responseTag;
	
	public ResponseOption(String text, String responseTag)
	{
		this.text = text;
		this.responseTag = responseTag;
	}
	
	public String getResponseTag()
	{
		return responseTag;
	}
	
	public String toString()
	{
		return text;
	}
}

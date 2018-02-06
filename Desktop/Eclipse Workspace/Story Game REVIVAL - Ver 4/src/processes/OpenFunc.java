package processes;

import base.Output;
import manual.Data;
import manual.Folder;
import manual.Manual;
import params.PathObj;
import process_stuff.Function;

public class OpenFunc extends Function
{
	public OpenFunc(String name, String desc, String... followWith)
	{
		super(name, desc, followWith);
	}

	public Output execute(PathObj.Type path)
	{
		Data d = path.getValue();
		
		return Output.genGenericOutput("Moving to " + (d instanceof Folder ? "folder" : "page") + ": " + Manual.moveTo(d));
	}
}

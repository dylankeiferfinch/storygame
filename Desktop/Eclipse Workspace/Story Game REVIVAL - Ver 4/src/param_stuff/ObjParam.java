package param_stuff;

import input.Arg;
import input.ArgTag;
import input.HardTypedArg;
import input.Input;
import input.InputError;
import input.SeperatorArg;
import input.SoftTypedArg;
import input.StaticArg;
import input.VariableArg;
import params.*;

public abstract class ObjParam extends Param
{
	private static ObjParam[] argTypes;
	
	private String desc;
	private ParamTag[] tags;
	
	public ObjParam(String id, String name, String desc, ParamTag... tags)
	{	
		super(name, id);
		
		this.desc = desc;
		
		this.tags = new ParamTag[tags.length + 1];
		
		for (int i = 0; i < tags.length; i++)
		{
			this.tags[i]  = tags[i];
		}
		
		this.tags[this.tags.length - 1] = new ParamTag("this", this);
	}
	
	public static void initObjParamList()
	{
		addObjParam(new AmtObj("amt", "amount", "any non-negative integer"));
		addObjParam(new StrObj("str", "string", "any string of characters"));
		addObjParam(new NewItemNameObj("new_item_name", "new item name", "a name that has not been used as an Item's name before"));
		addObjParam(new NewItemIdObj("new_item_id", "new item id", "a string of charcters that does not contain any spaces and has not been used as an Item's ID before"));
		addObjParam(new ItemIdObj("item_id", "item ID", "the Item ID for an existing Item"));
		addObjParam(new InvtItemObj("invt_item", "item in inventory", "either teh name or ID of an item that you currently have in your inventory"));
		addObjParam(new EnemyIdObj("enemy_id", "enemy ID", "the ID of an enemy"));
		addObjParam(new LocObj("loc", "location", "a game area, like \"INVENTORY\" or \"ROOM\""));
		addObjParam(new TypeObj("type", "type", "an argument type, like \"str\""));
		addObjParam(new PersonInRoomObj("person", "person", "a person who is in the current room"));
		addObjParam(new DialogOptionObj("dialog", "dialog option", "a choice in a dialog tree"));
		addObjParam(new DirObj("dir", "direction", "the direction of the room you want to move to"));
		addObjParam(new ExaminableObj("examinable", "examinable", "a thign in the current room that can be examined"));
		addObjParam(new InteractableObj("inter", "interactable", "something in the current room that you can use something on"));
		addObjParam(new BoolObj("bool", "boolean value", "either \"TRUE\" or \"FALSE\""));
		addObjParam(new BooleanGameRuleObj("bool_rule", "boolean-valued gamerule", "a GameRule that can be set to either \"TRUE\" or \"FALSE\""));
		addObjParam(new NewItemObj("new:item", "new item", "a new item that has not been defined previpusly by the user or the game", new ParamTag("name", type("new_item_name")), new ParamTag("id", type("new_item_id")), new ParamTag("desc", type("str"))));
		addObjParam(new ItemObj("item", "item", "the name or ID of an item that has been previously defined by the user or the game"));
		addObjParam(new FuncObj("func", "function name", "the name of any function, like \"invt\" or \"go\"."));
		addObjParam(new ComObj("com", "command name", "the name of any command, with or without the slash (\"/\")"));
		addObjParam(new FolderObj("folder", "folder name", "the name of a folder in the manual that is accessible from the current folder"));
		addObjParam(new PageObj("page", "page name", "the name of a page in the manual that is accessible from the current folder"));
		addObjParam(new InvtWeaponObj("invt_weapon", "weapon in inventory", "a wepaon in your inventory"));
		addObjParam(new PathObj("path", "manaul page/folder path", "the Manual Path from the current Page/Folder to a new Page/Folder"));
	}
	
	public static ObjParam[] getObjParamList()
	{
		return argTypes;
	}
	
	public static void addObjParam(ObjParam t)
	{
		if (argTypes == null)
			argTypes = new ObjParam[] {t};
		
		else
		{
			ObjParam[] temp = new ObjParam[argTypes.length + 1];
			
			for (int i = 0; i < argTypes.length; i++)
				temp[i] = argTypes[i];
			
			temp[temp.length - 1] = t;
			argTypes = temp;
		}
	}
	
	protected Object tryTag(HardTypedArg part, String tag, Object defaultValue)
	{	
		if (part.hasTag(tag))
		{
			ObjParam typeOfTag = this.getTag(part.getTag(tag).getName()).getType();
			
			ParamType value = typeOfTag.parse(part.getTag(tag).getValue());
			
			return value == null ? null : value.getValue();
		}
		
		else
		{
			if (defaultValue == null)
			{
				ObjParam typeOfTag = this.getTag(tag).getType();
				HardTypedArg newPart = new HardTypedArg(typeOfTag.getId() + "[]");
				
				ParamType value = typeOfTag.parse(newPart);
				
				return value == null ? null : value.getValue();
			}
			
			return defaultValue;
		}
	}
	
	protected Object tryTag(HardTypedArg taggedPart, String tag)
	{	
		return this.tryTag(taggedPart, tag, null);
	}
	
	public static ObjParam getObjParam(String id)
	{
		for (int i = 0; i < argTypes.length; i++)
		{
			if (argTypes[i].id.equals(id))
				return argTypes[i];
		}
		
		return null;
	}

	public String getDesc()
	{
		return desc;
	}
	
	public ParamTag getTag(String tag)
	{
		for (ParamTag t : tags)
		{
			if (t.getName().equals(tag))
				return t;
		}
		
		return null;
	}
	
	public ParamTag[] getTags()
	{
		return tags;
	}
	
	public String toString()
	{
		return id;
	}
	
	//-----------------------------------------------------------------
	// PRIVATE HELPER METHODS
	//-----------------------------------------------------------------
	
	private static ObjParam type(String id)
	{
		return getObjParam(id);
	}
	
	//-----------------------------------------------------------------
	// ARGTYPE TEST METHODS
	//-----------------------------------------------------------------
	
	@Override
	protected ParamType reallyParse(Arg part)
	{
		if (part == null)
		{
			Input.setError("UNKNOWN ERROR: A null argument was passed to ObjParam parsing.", part);
			return null;
		}
		
		if (part instanceof HardTypedArg)
		{
			HardTypedArg taggedPart = (HardTypedArg) part;
			
			if (!taggedPart.getFocus().equals(this.id))
			{
				ObjParam attemptedType = ObjParam.getObjParam(taggedPart.getFocus());
				
				if (attemptedType == null)
				{
					Input.setError("INVALID HARD TYPE: The type \"" + taggedPart.getFocus() + "\" is not a valid type.", part);
					return null;
				}
				
				Input.setError("HARD TYPE MISMATCH: Expected the type \"" + this.name + "\" (" + this.id + ") but found the type \"" + attemptedType.name + "\" (" + attemptedType.id + ").", part);
				return null;
			}
			
			if (taggedPart.getTag("this") != null && taggedPart.getTags().length > 1)
			{
				Input.setError("INVALID TAG COMBINATION: You cnanot combine the \"this\" tag with any other tags.", part);
				return null;
			}
			
			else if (taggedPart.getTag("this") != null)
			{
				return this.parse(taggedPart.getTag("this").getValue());
			}
			
			ArgTag[] tags = taggedPart.getTags();
			
			for (ArgTag tag : tags)
			{
				if (this.getTag(tag.getName()) == null)
				{
					Input.setError("INVALID TAG: The type \"" + this.name + "\" does not support the tag \"" + tag.getName() + "\".", part);
					return null;
				}
			}
			
			return this.value(taggedPart);
		}
		
		else if (part instanceof SoftTypedArg)
		{
			SoftTypedArg nakedPart = (SoftTypedArg) part;
			
			if (part.getFocus().equals(""))
			{
				Input.setError("EMPTY ARGUMENTS: Expected argumnets following the function, but found none.", part);
				return null;
			}
			
			return this.value(nakedPart);
		}
		
		else if (part instanceof StaticArg)
		{
			return this.value((StaticArg) part);
		}
		
		else if (part instanceof VariableArg)
		{
			return this.value(new SoftTypedArg("'" + part.getFocus() + "'"));
		}
		
		else if (part instanceof SeperatorArg)
		{
			return this.value((SeperatorArg) part);
		}
		
		Input.setError("UNKNOWN ERROR: An unknwon error occured druing ObjParam parsing.", part);
		return null;
	}
	
	@Override
	protected ParamType value(StaticArg arg)
	{
		Input.setError("ARGUMENT MISMATCH: Expected a Hard Typed Arg or a Soft Typed Arg, but got a Static Arg.", arg);
		return null;
	}
	
	@Override
	protected ParamType value(SeperatorArg arg)
	{
		Input.setError("ARGUMENT MISMATCH: Expected a Hard Typed Arg or a Soft Typed Arg, but got a Seperator Arg.", arg);
		return null;
	}
	
	@Override
	protected ParamType value(HardTypedArg arg)
	{
		Input.setError(InputError.genNoDefaultHardTypeError(this, arg));
		return null;
	}
	
	public String[] defaultValues()
	{
		return new String[] {this.id + "[]"};
	}
}

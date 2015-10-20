package economy;

public class ResourceBook
{
	//RESOURCE IDS
	public static final int WOOD = 0;
	public static final int FOOD = 1;
	
	private static final int RESOURCES_LENGHT = 2;
	
	
	private final String[] RESOURCES;
	
	public ResourceBook()
	{
		RESOURCES = new String[RESOURCES_LENGHT];
		initBook();
	}
	private void initBook() 
	{
		RESOURCES[WOOD] = "wood";
		RESOURCES[FOOD] = "food";
		
	}
	public boolean checkValid()
	{
		for(String res : RESOURCES)
			if(res == null)
				return false;
		return true;
	}
	
	public int size()
	{
		return RESOURCES.length;
	}

}

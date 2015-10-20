package agent;

import economy.ResourceBook;

public class Inventory
{
	//InventoryData
	public final int[] stuff;
	public final BaseAgent owner;
	//MetaData
	
	public Inventory(BaseAgent owner, ResourceBook resBook)
	{
		this.owner= owner;
		stuff = new int[resBook.size()];
	}
	public int getAmount(int good_id)
	{
		return stuff[good_id];
	}
	public boolean hasAmount(int good_id, int amount)
	{
		if(getAmount(good_id) >= amount)
			return true;
		return false;
	}
	public boolean remove(int good_id, int amount)
	{
		if(hasAmount(good_id, amount) == false)
			return false;
		stuff[good_id] -= amount;
		return true;
	}
	public void store(int good_id, int amount)
	{
		stuff[good_id] += amount;
	}
	
}

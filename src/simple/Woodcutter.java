package simple;

import economy.Market;
import economy.ResourceBook;
import agent.BaseAgent;

public class Woodcutter extends BaseAgent
{
	private final int START_FOOD = 0;
	
	//PROD VALUES
	private int FOOD_4_WOOD = 1;
	private int WOOD_PRODUCTION = 5;
	//
	private final int FOOD_STOCK = 10;
	
	public Woodcutter(float money, ResourceBook resBook)
	{
		super(money, resBook);
		inventory.store(ResourceBook.FOOD, START_FOOD);
	}

	@Override
	public boolean production() 
	{
		if(inventory.remove(ResourceBook.FOOD, FOOD_4_WOOD))
		{
			inventory.store(ResourceBook.WOOD, WOOD_PRODUCTION);
			return true;
		}
		return false;
	}

	@Override
	public void generateTrades(Market m)
	{
		int surplus = inventory.getAmount(ResourceBook.FOOD) - FOOD_STOCK;
		if(surplus < 0)
			m.addBuyOffer(createBuy(ResourceBook.FOOD, surplus * -1, m));
		
		if(inventory.getAmount(ResourceBook.WOOD) > 0)
			m.addSellOffer(createSell(ResourceBook.WOOD, inventory.getAmount(ResourceBook.WOOD), m));
	}

}

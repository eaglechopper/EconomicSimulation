package simple;

import economy.Market;
import economy.ResourceBook;
import agent.BaseAgent;

public class Farmer extends BaseAgent
{
	private final int START_WOOD = 10;
	
	private final int WOOD_4_FOOD = 5;
	private final int FOOD_PRODUCTION = 2;
	//SURPLUS VALUES
	private final int WOOD_STOCK = 10;

	
	public Farmer(float money, ResourceBook resBook)
	{
		super(money, resBook);
		inventory.store(ResourceBook.WOOD, START_WOOD);
	}

	@Override
	public boolean production()
	{
		if(inventory.remove(ResourceBook.WOOD, WOOD_4_FOOD))
		{
			inventory.store(ResourceBook.FOOD, FOOD_PRODUCTION);
			return true;
		}
		return false;
	}

	@Override
	public void generateTrades(Market m)
	{
		int surplus = inventory.getAmount(ResourceBook.WOOD) - WOOD_STOCK;
		if(surplus < 0)
			m.addBuyOffer(createBuy(ResourceBook.WOOD, surplus * -1, m));
		
		if(inventory.getAmount(ResourceBook.FOOD) > 0)
			m.addSellOffer(createSell(ResourceBook.FOOD, inventory.getAmount(ResourceBook.FOOD), m));
		
	}

}

package agent;

import economy.Market;
import economy.ResourceBook;

public abstract class BaseAgent 
{
	//AGENT VARS
	protected final int WINDOW = 15;
	protected float money;
	protected final Inventory inventory;
	protected final PriceModel[] priceModel;
	
	
	public BaseAgent(float money, ResourceBook resBook)
	{
		this.money = money;
		this.inventory = new Inventory(this, resBook);
		this.priceModel = new PriceModel[resBook.size()];
		//
		for(int good_id=0; good_id < priceModel.length; good_id++)
		{
			priceModel[good_id] = new PriceModel(good_id);
		}
	}
	
	public abstract boolean production();
	public abstract void generateTrades(Market m);
	
	public BidOffer createBuy(int good_id, int amount, Market market)
	{
		return new BidOffer(this, good_id, amount, determineBidPrice(good_id), market);
	}
	public AskOffer createSell(int good_id, int amount, Market market)
	{
		return new AskOffer(this, good_id, amount, determineAskPrice(good_id), market);
	}
	
	public float determineAskPrice(int good_id)
	{
		return priceModel[good_id].determineSellsPrice();
	}
	public float determineBidPrice(int good_id)
	{
		return priceModel[good_id].determineBuyPrice();
	}
	
	public void onUpdateAskPrice(int good_id, boolean success,  Market market, float unitPrice)
	{
		priceModel[good_id].updateAskPrice(success, market, unitPrice);
	}
	public void onUpdateBidPrice(boolean success, int good_id, Market market, float unitPrice)
	{
		priceModel[good_id].updateBidPrice(success, market, unitPrice);
	}
	public void simulate(Market market)
	{
		production();
		generateTrades(market);
	}
	//Method to transfer inventory
	public boolean transferResource(int good_id, int amount, BaseAgent owner)
	{
		if(inventory.remove(good_id, amount) == true)
		{
			owner.inventory.store(good_id, amount);
			return true;
		}
		return false;
	}

	public float money() {
		// TODO Auto-generated method stub
		return money;
	}

	public boolean pullMoney(float amount) 
	{
		if(money >= amount)
		{
			money -= amount;
			return true;
		}
		return false;
	}
	public Inventory getInventory()
	{
		return inventory;
	}

	public void putMoney(float amount)
	{
		money += amount;
	}
}

package agent;

import economy.Market;

public class SellOffer implements Comparable<SellOffer>
{
	//OFFER INFO
	private final BaseAgent seller;
	private final int totalAmountOfferd;
	private final int good_id;
	private final float price;
	//AMOUNT RECIEVED
	private int amountSold;
	private final Market market;
	
	public SellOffer(BaseAgent seller, int good_id, int amount, float price, Market market)
	{
		this.market = market;
		this.price = price;
		this.seller = seller;
		this.good_id = good_id;
		this.totalAmountOfferd = amount;
	}
	public BaseAgent getSeller() {
		return seller;
	}

	public int getTotalOfferedAmount()
	{
		return totalAmountOfferd;
	}
	public float getPrice()
	{
		return price;
	}

	public int getGoodID() {
		return good_id;
	}
	public int amountRemaing()
	{
		return totalAmountOfferd - amountSold;
	}

	@Override
	public int compareTo(SellOffer o)
	{
		if(totalAmountOfferd > o.totalAmountOfferd)
			return 1;
		else if(totalAmountOfferd < o.totalAmountOfferd)
			return -1;
		return 0;
	}
	public boolean isSelling()
	{
		if(amountSold >= totalAmountOfferd)
			return false;
		return true;
	}
	public void addRecieved(int i) {
		amountSold += i;
		
	}
	public void reject()
	{
		seller.onUpdateSalePrice(good_id, false, market, price);
	}
}

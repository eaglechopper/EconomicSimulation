package agent;

import economy.Market;

public class BidOffer implements Comparable<BidOffer>
{
	private final BaseAgent buyer;
	private final int good_id;
	private final int totalAmount;
	private final float price;
	//
	private int amountRecieved;
	private final Market market;
	
	public BidOffer(BaseAgent buyer, int good_id, int amount, float price, Market market)
	{
		this.market = market;
		this.price = price;
		this.buyer = buyer;
		this.good_id = good_id;
		this.totalAmount = amount;
	}
	public BaseAgent getBuyer() 
	{
		return buyer;
	}

	public int getGoodID() {
		return good_id;
	}
	public int amountRemaining()
	{
		return totalAmount - amountRecieved;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	@Override
	public int compareTo(BidOffer o)
	{
		if(totalAmount > o.totalAmount)
			return -1;
		else if(totalAmount < o.totalAmount)
			return 1;
		return 0;
	}
	public boolean isBuying()
	{
		if(amountRecieved >= totalAmount)
			return false;
		return true;
	}
	public float getPrice() {
		// TODO Auto-generated method stub
		return price;
	}
	public void addRecieved(int i) {
		amountRecieved += i;
		
	}
	public void reject()
	{
		buyer.onUpdateBidPrice(false, good_id, market, price);
	}
	
}

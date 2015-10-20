package economy;

import agent.BidOffer;
import agent.AskOffer;

public class Market 
{
	
	private final TradingSession[] tradingBook;
	private final History[] tradingHistory;
	
	
	private boolean isOpen;
	
	public Market(ResourceBook resBook)
	{
		this.tradingBook = new TradingSession[resBook.size()];
		this.tradingHistory = new History[resBook.size()];
		//init trading books
		for(int i=0; i < resBook.size(); i++)
		{
			tradingBook[i] = new TradingSession(this, i);
			tradingHistory[i] = new History(this, i);
		}
	}
	
	public void closeMarket()
	{
		isOpen = false;
	}
	public void openMarket()
	{
		isOpen = true;
	}
	public boolean isMarketOpen()
	{
		return isOpen;
	}

	public void resolveMarket()
	{
		for(TradingSession session : tradingBook)
			session.resolveTrades();
	}

	public void addBuyOffer(BidOffer createBuy) 
	{
		tradingBook[createBuy.getGoodID()].addBuy(createBuy);
	}

	public void addSellOffer(AskOffer createSell)
	{
		tradingBook[createSell.getGoodID()].addSell(createSell);
	}
	//
	public History getHistory(int good_id)
	{
		return tradingHistory[good_id];
	}
	
}

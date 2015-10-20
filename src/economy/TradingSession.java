package economy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.BuyOffer;
import agent.SellOffer;

public class TradingSession
{
	//BUYING AND SELLING LIST
	private final List<SellOffer> selling;
	private final List<BuyOffer> buyers;
	private final Market market;
	//INFO
	private final int good_id;
	//DATA ABOUT LAST TRADING SESSION
	private int bidVolume;
	private int askVolume;
	//AFTER RESOLVE TRADES
	private int currentIndexSeller = 0;
	private int currentIndexBuyer = 0;
	
	public TradingSession(Market market, int good_id)
	{
		this.market = market;
		this.good_id = good_id;
		this.selling = new ArrayList<SellOffer>();
		this.buyers = new ArrayList<BuyOffer>();
	}
	public void addSell(SellOffer sellOffer)
	{
		this.selling.add(sellOffer);
		bidVolume += sellOffer.getTotalOfferedAmount();
	}
	public void addBuy(BuyOffer buyOffer)
	{
		askVolume += buyOffer.getTotalAmount();
		this.buyers.add(buyOffer);
	}
	public void resolveTrades()
	{
		Collections.shuffle(selling);
		Collections.shuffle(buyers);
		Collections.sort(selling);
		Collections.sort(buyers);
		//Meta variables
		//int vomlumeBuying = selling.size();
		//int volumeSelling = buyers.size();
		
		
		BuyOffer currentBuyer;
		SellOffer currentSeller;
		//Using indexs to keep track of buyers/seller to avoid resizing array after every remove
		while(currentIndexSeller < selling.size() && currentIndexBuyer < buyers.size())
		{
			currentBuyer = buyers.get(currentIndexBuyer);
			currentSeller = selling.get(currentIndexSeller);
				
			if(currentBuyer.isBuying() && currentSeller.isSelling())
				resolveTrade(currentBuyer, currentSeller);
			
			//IF SELLER OR BUYER NOT SELLING INCREMENT INDEX
			if(!currentBuyer.isBuying())
				++currentIndexBuyer;
			if(!currentSeller.isSelling())
				++currentIndexSeller;
		}
		rejectRemainingTrades();
		
	}
	private void rejectRemainingTrades()
	{
		for(int i=currentIndexBuyer; i < buyers.size(); i++)
		{
			buyers.get(i).reject();
		}
		//
		for(int i=currentIndexSeller; i < buyers.size(); i++)
		{
			selling.get(i).reject();
		}
	}
	//ResolvesTradeDisputeBetweenThem
	private void resolveTrade(BuyOffer buyer, SellOffer seller)
	{
		
		int quanityTraded = Math.min(buyer.remainingAmount(), seller.amountRemaing());
		float clearingPrice = (buyer.getPrice() + seller.getPrice()) / 2;
		//Tranfer funds until done or buyer can afford anymore
		int totalTraded = 0;
		while(buyer.getBuyer().money()  >= clearingPrice && totalTraded < quanityTraded && seller.getSeller().getInventory().hasAmount(good_id, 1))
		{
			//Transfer to byer
			buyer.getBuyer().removeMoney(clearingPrice);
			buyer.getBuyer().getInventory().store(good_id, 1);
			buyer.addRecieved(1);
			//transfer to seller
			seller.getSeller().putMoney(clearingPrice);
			seller.getSeller().getInventory().remove(good_id, 1);
			seller.addRecieved(1);
		
			++totalTraded;
		}
		buyer.getBuyer().onUpdateBuyPrice(true, good_id,market , clearingPrice);
		seller.getSeller().onUpdateSalePrice(totalTraded, true, market, clearingPrice);
	}
	
}

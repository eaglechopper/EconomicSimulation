package economy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.BidOffer;
import agent.AskOffer;

public class TradingSession
{
	//BUYING AND SELLING LIST
	private final List<AskOffer> selling;
	private final List<BidOffer> buyers;
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
		this.selling = new ArrayList<AskOffer>();
		this.buyers = new ArrayList<BidOffer>();
	}
	public void addSell(AskOffer sellOffer)
	{
		this.selling.add(sellOffer);
		bidVolume += sellOffer.getTotalOfferedAmount();
	}
	public void addBuy(BidOffer buyOffer)
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
		
		
		BidOffer currentBuyer;
		AskOffer currentSeller;
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
	private void resolveTrade(BidOffer buyer, AskOffer seller)
	{
		
		int quanityTraded = Math.min(buyer.amountRemaining(), seller.amountRemaing());
		float clearingPrice = (buyer.getPrice() + seller.getPrice()) / 2;
		//Tranfer funds until done or buyer can afford anymore
		int totalTraded = 0;
		while(buyer.getBuyer().money()  >= clearingPrice && totalTraded < quanityTraded && seller.getSeller().getInventory().hasAmount(good_id, 1))
		{
			//Transfer to byer
			buyer.getBuyer().pullMoney(clearingPrice);
			buyer.getBuyer().getInventory().store(good_id, 1);
			buyer.addRecieved(1);
			//transfer to seller
			seller.getSeller().putMoney(clearingPrice);
			seller.getSeller().getInventory().remove(good_id, 1);
			seller.addRecieved(1);
		
			++totalTraded;
		}
		buyer.getBuyer().onUpdateBidPrice(true, good_id,market , clearingPrice);
		seller.getSeller().onUpdateAskPrice(totalTraded, true, market, clearingPrice);
	}
	
}

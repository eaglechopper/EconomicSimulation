package agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.w3c.dom.ranges.RangeException;

import economy.Market;

public class PriceModel
{
	//What good this price model is for
	private final int good_id;
	//Belief range
	private Range askBelief;
	private Range bidBelief;
	//history
	private final List<Float> askHisotry;
	private final List<Float> bidHistory;
	private Random rand = new Random();
	//totalObservable tradingRange
	private Range bidRange;
	private Range askRange;
	
	public PriceModel(int good_id)
	{
		this.good_id = good_id;
		this.askBelief = new Range(0.01f, 1f);
		this.bidBelief = new Range(0.01f, 1f);
		this.askHisotry = new ArrayList<Float>();
		this.bidHistory = new ArrayList<Float>();
	}

	public float determineBuyPrice()
	{
		return askBelief.low + rand.nextFloat() * (askBelief.high - askBelief.low);
	}

	public float determineSellsPrice() 
	{
		return bidBelief.low + rand.nextFloat() * (bidBelief.high - bidBelief.low);
	}

	public void updateAskPrice(boolean success, Market market, float unitPrice)
	{
		if(success)
		{
			
		}
		else
		{
			
		}
	}

	public void updateBidPrice(boolean success, Market market, float unitPrice)
	{
		if(success)
		{
			
		}
		else
		{
			
		}
	}
	public int getGoodID()
	{
		return good_id;
	}
	//PRIVATE RANGE CLASS
	private class Range
	{
		private float low;
		private float high;
		
		private Range(float high, float low)
		{
			this.low = low;
			this.high = high;
		}
	}
	public float getAskFavorability()
	{
		return 0;
	}
	public float getBidFavorability()
	{
		return 0;
	}

}

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
	//
	private Range buyBelief;
	private Range sellBelief;
	//history
	private final List<Float> saleHisotry;
	private final List<Float> buyHistory;
	private Random rand = new Random();
	
	public PriceModel(int good_id)
	{
		this.good_id = good_id;
		this.buyBelief = new Range(0.01f, 1f);
		this.sellBelief = new Range(0.01f, 1f);
		this.saleHisotry = new ArrayList<Float>();
		this.buyHistory = new ArrayList<Float>();
	}

	public float determineBuyPrice()
	{
		return buyBelief.low + rand.nextFloat() * (buyBelief.high - buyBelief.low);
	}

	public float determineSellsPrice() 
	{
		return sellBelief.low + rand.nextFloat() * (sellBelief.high - sellBelief.low);
	}

	public void updateSalePrice(boolean success, Market market, float unitPrice)
	{
		if(success)
		{
			
		}
		else
		{
			
		}
	}

	public void updateBuyPrice(boolean success, Market market, float unitPrice)
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

}

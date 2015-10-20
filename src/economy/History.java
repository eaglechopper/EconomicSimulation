package economy;

import java.util.ArrayList;
import java.util.List;


public class History
{
	private final List<Float> historyValues;
	private final int good_id;
	private final Market market;
	
	public History(Market market, int good_id)
	{
		this.market = market;
		this.good_id = good_id;
		this.historyValues = new ArrayList<Float>();
		this.historyValues.add(1f);
	}
	
	public void addHistory(float value) 
	{
		historyValues.add(value);
	}

	public float getAverageHistoricalPrice(int window)
	{
		if(window > historyValues.size())
			window = historyValues.size();
		float sum = 0;
		for(int i=0, index=historyValues.size() - 1; i < window; i++,  index--)
		{
			sum += historyValues.get(index);
		}
		return sum / window;
	}
	public int getGoodID()
	{
		return good_id;
	}

}

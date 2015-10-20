package economy;

import java.util.HashSet;
import java.util.Set;

import agent.BaseAgent;

public class Economy implements OnAgentBankrupt
{
	private Set<BaseAgent> economicAgents;
	private Market market;
	
	public Economy(ResourceBook resBook)
	{
		this.market = new Market(resBook);
		this.economicAgents = new HashSet<BaseAgent>();
	}
	public void addAgent(BaseAgent agent)
	{
		economicAgents.add(agent);
	}
	public void simulate(int rounds)
	{
		for(int i=0; i < rounds; i++)
		{
			for(BaseAgent agent : economicAgents)
			{
				agent.simulate(market);
			}
			market.resolveMarket();
		}
	}
	@Override
	public void onAgentBankrupt(BaseAgent agent)
	{
		economicAgents.remove(agent);
	}
	
}

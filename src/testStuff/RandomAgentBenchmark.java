package testStuff;

import java.util.Random;
/* this class is a demonstation of how to get fast random actions
 * to be performed on agents in a simulation we can achieve about 50% faster cycle by
 * not generating a random value for each agent as they perform the agent, but rather
 * calulate and store a random index on initalization and only generate 1 random value
 * each simualtion cycle and use the storedRandomIndex together to get a randomIndex*/
public class RandomAgentBenchmark 
{
	private static final Random random = new Random();
	
	public static void main(String[] args)
	{
		final int AGENT_NUMBERS = 10000000;
		final int[] actionIDS = {22, 7, 8, 9, 10};
		final FastAgent[] fastAgents = spawnFastAgents(actionIDS, AGENT_NUMBERS);
		final SlowAgent[] slowAgents = spawnSlowAgents(AGENT_NUMBERS);
		
		final int PRINT_AGENTS = 20;
		
		long before = 0;	
		//SLOW
		before = System.currentTimeMillis();
		slowerMethod(slowAgents, actionIDS);
		System.out.println("Millis Slow: " + (System.currentTimeMillis() - before));
		//FAST
		before = System.currentTimeMillis();
		fasterMethod(fastAgents, actionIDS);
		System.out.println("Millis Slow: " + (System.currentTimeMillis() - before));
		
		for(int i=0; i < PRINT_AGENTS; i++)
		{
			System.out.printf("Fast: %d AND Slow: %d\n", fastAgents[i].lastAction, slowAgents[i].lastAction);
		}
	}
	private static void slowerMethod(SlowAgent[] slowAgents, int[] actionIDs)
	{
		for(int i=0; i < slowAgents.length; i++)
			slowAgents[i].act(actionIDs);
	}
	private static void fasterMethod(FastAgent[] fastAgents, int[] actioIDS)
	{
		int randomForAll = randInt(0, actioIDS.length - 1);
		for(int i=0; i < fastAgents.length; i++)
			fastAgents[i].act(actioIDS, randomForAll);
	}
	//Agent spawn
	private static SlowAgent[] spawnSlowAgents(int num)
	{
		SlowAgent[] slowAgents = new SlowAgent[num];
		for(int i=0; i < slowAgents.length; i++)
			slowAgents[i] = new SlowAgent();
		return slowAgents;
	}
	private static FastAgent[] spawnFastAgents(int[] actionIDs, int num)
	{
		FastAgent[] fastAgents = new FastAgent[num];
		for(int i=0; i < num; i++)
		{
			fastAgents[i] = new FastAgent(actionIDs);
		}
		return fastAgents;
	}
	
	public static class SlowAgent
	{
		private int lastAction;
		
		public SlowAgent()
		{
			
		}
		public void act(int[] actionIDs)
		{
			lastAction = actionIDs[randInt(0, actionIDs.length -1)];
		}
		
	}
	public static class FastAgent
	{
		private int randomSeed;
		private int lastAction;
		
		public FastAgent(int[] action)
		{
			this.randomSeed = randInt(0, action.length - 1);
		}
		public void act(int[] actionIDs, int randomForAll)
		{
			int index = randomSeed + randomForAll;
			if(index > actionIDs.length - 1)
				index -= actionIDs.length;
			lastAction = actionIDs[index];
		}
	}
	
	public static int randInt(int min, int max)
	{
		return random.nextInt((max - min) + 1) + min;
	}
	
	


}

package utils;

public abstract class FunctionFloatX
{
	private float lower;
	private float upper;
	
	private boolean bounded;
	
	public FunctionFloatX(float lower, float upper)
	{
		this.lower = lower;
		this.upper = upper;
		this.bounded = true;
	}
	public FunctionFloatX()
	{
		this.bounded = false;
	}
	
	protected abstract float doFunc(float x);
	
	public float input(float x)
	{
		float result = doFunc(x);
		if(bounded)
		{
			if(result < lower)
				result = lower;
			else if(result > upper)
				result = upper;
		}
		return result;
	}
}

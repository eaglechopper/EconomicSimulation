package mathfunc;

import utils.FunctionFloatX;

public class LinearFloatFunction extends FunctionFloatX
{
	private float scale;
	private float xTranslate;
	private float yTranslate;
	
	public LinearFloatFunction(float lower, float upper) 
	{
		super(lower, upper);
	}
	public LinearFloatFunction(float scale, float xTranslate, float yTranslate) 
	{
		this.scale = scale;
		this.xTranslate = xTranslate;
		this.yTranslate = yTranslate;
	}
	
	@Override
	protected float doFunc(float x) 
	{
		//2, 1, 4
		return (scale * (x + xTranslate)) + yTranslate;
	}
	

}

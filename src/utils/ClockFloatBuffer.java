package utils;

import java.lang.reflect.Array;

import utils.ClockBuffer.ClockBufferListener;

/*This class can maintain a set of ordered
 * interval values using a "clock" style pattern
 * therefore there is no resize cost at any point
 * to add new elements, it achieves this by maintaining 
 * a needle pointer to the current value and ordering 
 * involves back tracking through the buffer the clock can also maintain
 * min and max values without traversing the entire buffer array, it achived this
 * by maintaining min, max value as the count of max and min, when a new value is added
 * if it is the new minimum, set it and reset max count, if new max set it and reset max count
 * if it is the same as max, or min adjust the count, when an item is evicted if = min, or max
 * reduce the count, if the min, max counter = 0, then recalucate the value by going through array
 * */
public class ClockFloatBuffer
{
	//Clock buffer values
	private float[] buffer;
	private int needle;
	private long totalCount;
	//Meta values to maintain min, max
	private float min;
	private int countMin;
	
	private float max;
	private int countMax;
	//Running average
	private float totalSum;

	private ClockFloatBufferListener listener;
	
	public interface ClockFloatBufferListener
	{
		public void onElementEvicted(float item, ClockFloatBuffer caller);
		public void onClockBufferResize(int newSize, ClockFloatBuffer caller);
	}

	public ClockFloatBuffer(final int size)
	{
		this.buffer = new float[size];
	}
	private void onLocalElementEvicted(float evictedItem)
	{
		//Check min, max
		if(evictedItem == min)
		{
			--countMin;
			if(countMin <=0)
				recalcMin();
		}
		else if(evictedItem == max)
		{
			--countMax;
			if(countMax <= 0)
				recalcMax();
		}
		//minus from total sum
		totalSum -= evictedItem;
		
	}
	private void recalcMax()
	{
		float currentMax = buffer[0];
		int currentCountMax  = 0;
		
		for(int i=0; i < buffer.length; i++)
		{
			if(buffer[i] == currentMax)
				++currentCountMax;
			else if(buffer[i] > currentMax)
			{
				currentMax = buffer[i];
				currentCountMax = 1;
			}
		}
		countMax = currentCountMax;
		max = currentMax;
	}
	private void recalcMin() 
	{
		float currentMin = buffer[0];
		int currentCountMin  = 0;
		
		for(int i=0; i < buffer.length; i++)
		{
			if(buffer[i] == currentMin)
				++currentCountMin;
			else if(buffer[i] < currentMin)
			{
				currentMin = buffer[i];
				currentCountMin = 1;
			}
		}
		countMin = currentCountMin;
		min = currentMin;
	}
	public boolean add(float value)
	{
		if(needle >= buffer.length)
			needle = 0;
		if(isFilled() && listener != null)
		{
			listener.onElementEvicted(buffer[needle], this);
			onLocalElementEvicted(buffer[needle]);
		}
		buffer[needle++] = value;
		//min count
		if(value < min)
			min = value;
		else if(value == min)
			++countMin;
		//max count
		if(value > max)
			max = value;
		else if(value == max)
			++countMax;
		//add total sum
		totalSum += value;
		
		++totalCount;
		return isFilled();
	}
	public float[] getOrderedBuffer()
	{
		float[] array = new float[buffer.length];
		int reverseNeedle = needle - 1;
		for(int i=0; i < array.length; i++)
		{
			if(reverseNeedle < 0)
				reverseNeedle = buffer.length - 1;
			array[i] = buffer[reverseNeedle--];
		}
		return array;
	}
	public void setListener(ClockFloatBufferListener listener)
	{
		this.listener = listener;
	}
	public boolean resize(final int newSize)
	{
		if(isFilled())
		{
			//get old ordered buffer ref, create new buffer and add to clock buffer
			float[] oldOrderedBuffer = getOrderedBuffer();
			buffer = new float[newSize];
			//loop through and add new new buffer clock
			needle = 0;
			totalCount = 0;
			for(int i=oldOrderedBuffer.length - 1; i >= 0; i--)
				add(oldOrderedBuffer[i]);
			if(listener != null)
				listener.onClockBufferResize(newSize, this);
			return true;
		}
		return false;
	}
	public boolean isFilled()
	{
		//if total element churn is greater then current buffer size it has been filled once
		if(totalCount >= buffer.length)
			return true;
		return false;
	}
	public float getNewest()
	{
		return buffer[needle -1];
	}
	public float getOldest()
	{
		return buffer[needle];
	}
	public float[] getUnorderBuffer()
	{
		return buffer;
	}
}

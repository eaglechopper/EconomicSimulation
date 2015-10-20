package utils;

import java.lang.reflect.Array;

public class ClockBuffer<T>
{
	private T[] buffer;
	private int needle;
	private long totalCount;
	private Class<T> clazz;
	private ClockBufferListener<T> listener;
	
	public interface ClockBufferListener<T>
	{
		public void onElementEvicted(T item, ClockBuffer<T> caller);
		public void onClockBufferResize(int newSize, ClockBuffer<T> caller);
	}
	
	@SuppressWarnings("unchecked")
	public ClockBuffer(final Class<T> clazz, final int size)
	{
		this.buffer = (T[])Array.newInstance(clazz, size);
		this.clazz = clazz;
	}
	public boolean add(T value)
	{
		if(needle >= buffer.length)
			needle = 0;
		if(isFilled() && listener != null)
			listener.onElementEvicted(buffer[needle], this);
		buffer[needle++] = value;
		++totalCount;
		return isFilled();
	}
	@SuppressWarnings("unchecked")
	public T[] getOrderedBuffer()
	{
		T[] array = (T[])Array.newInstance(clazz, buffer.length);
		int reverseNeedle = needle - 1;
		for(int i=0; i < array.length; i++)
		{
			if(reverseNeedle < 0)
				reverseNeedle = buffer.length - 1;
			array[i] = buffer[reverseNeedle--];
		}
		return array;
	}
	public void setListener(ClockBufferListener<T> listener)
	{
		this.listener = listener;
	}
	@SuppressWarnings("unchecked")
	public boolean resize(final int newSize)
	{
		if(isFilled())
		{
			//get old ordered buffer ref, create new buffer and add to clock buffer
			T[] oldOrderedBuffer = getOrderedBuffer();
			buffer = (T[]) Array.newInstance(clazz, newSize);
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
	public T getNewest()
	{
		return buffer[needle -1];
	}
	public T getOldest()
	{
		if(totalCount >= buffer.length)
			return buffer[needle];
		else
			return null;
	}
	public T[] getUnorderBuffer()
	{
		return buffer;
	}
}

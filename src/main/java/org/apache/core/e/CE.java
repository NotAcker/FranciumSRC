package org.apache.core.e;

public abstract class CE<T extends L> extends E<T>
{
	private boolean isCancelled = false;

	public boolean isCancelled()
	{
		return isCancelled;
	}

	public void cancel()
	{
		isCancelled = true;
	}
}
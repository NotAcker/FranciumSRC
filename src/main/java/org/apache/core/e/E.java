package org.apache.core.e;

import java.util.ArrayList;

public abstract class E<T extends L>
{
	public abstract void fire(ArrayList<T> listeners);

	public abstract Class<T> getListenerType();
}

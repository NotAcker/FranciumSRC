package org.apache.core.e.e;

import org.apache.core.e.CE;
import org.apache.core.e.E;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface TickListener extends L
{
	void onTick();

	class TickEvent extends E<TickListener>
	{

		@Override
		public void fire(ArrayList<TickListener> listeners)
		{
			listeners.forEach(e -> e.onTick());
		}

		@Override
		public Class<TickListener> getListenerType()
		{
			return TickListener.class;
		}
	}
}

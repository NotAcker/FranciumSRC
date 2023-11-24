package org.apache.core.e.e;

import org.apache.core.e.CE;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface MouseUpdateListener extends L
{
	void onMouseUpdate(MouseUpdateEvent event);

	class MouseUpdateEvent extends CE<MouseUpdateListener>
	{

		@Override
		public void fire(ArrayList<MouseUpdateListener> listeners)
		{
			listeners.forEach(e -> e.onMouseUpdate(this));
		}

		@Override
		public Class<MouseUpdateListener> getListenerType()
		{
			return MouseUpdateListener.class;
		}
	}
}

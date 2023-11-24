package org.apache.core.e.e;

import org.apache.core.e.E;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface PlayerTickListener extends L
{
	void onPlayerTick();

	class PlayerTickEvent extends E<PlayerTickListener>
	{

		@Override
		public void fire(ArrayList<PlayerTickListener> listeners)
		{
			listeners.forEach(PlayerTickListener::onPlayerTick);
		}

		@Override
		public Class<PlayerTickListener> getListenerType()
		{
			return PlayerTickListener.class;
		}
	}
}

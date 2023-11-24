package org.apache.core.e.e;

import org.apache.core.e.CE;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface ItemPickListener extends L
{
	void onItemPick(ItemPickEvent event);

	class ItemPickEvent extends CE<ItemPickListener>
	{

		@Override
		public void fire(ArrayList<ItemPickListener> listeners)
		{
			listeners.forEach(e -> e.onItemPick(this));
		}

		@Override
		public Class<ItemPickListener> getListenerType()
		{
			return ItemPickListener.class;
		}
	}
}

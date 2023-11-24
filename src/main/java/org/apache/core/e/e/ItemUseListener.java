package org.apache.core.e.e;

import org.apache.core.e.CE;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface ItemUseListener extends L
{
	void onItemUse(ItemUseEvent event);

	class ItemUseEvent extends CE<ItemUseListener>
	{

		@Override
		public void fire(ArrayList<ItemUseListener> listeners)
		{
			listeners.forEach(e -> e.onItemUse(this));
		}

		@Override
		public Class<ItemUseListener> getListenerType()
		{
			return ItemUseListener.class;
		}
	}
}

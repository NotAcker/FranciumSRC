package org.apache.core.e.e;

import org.apache.core.e.E;
import net.minecraft.entity.Entity;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface EntityDespawnListener extends L
{
	void onEntityDespawn(Entity entity);

	class EntityDespawnEvent extends E<EntityDespawnListener>
	{

		private Entity entity;

		public EntityDespawnEvent(Entity entity)
		{
			this.entity = entity;
		}

		@Override
		public void fire(ArrayList<EntityDespawnListener> listeners)
		{
			listeners.forEach(e -> e.onEntityDespawn(entity));
		}

		@Override
		public Class<EntityDespawnListener> getListenerType()
		{
			return EntityDespawnListener.class;
		}
	}
}

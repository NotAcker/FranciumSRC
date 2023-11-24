package org.apache.core.e.e;

import net.minecraft.entity.Entity;
import org.apache.core.e.E;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface EntitySpawnListener extends L
{
	void onEntitySpawn(Entity entity);

	class EntitySpawnEvent extends E<EntitySpawnListener>
	{

		private Entity entity;

		public EntitySpawnEvent(Entity entity)
		{
			this.entity = entity;
		}

		@Override
		public void fire(ArrayList<EntitySpawnListener> listeners)
		{
			listeners.forEach(e -> e.onEntitySpawn(entity));
		}

		@Override
		public Class<EntitySpawnListener> getListenerType()
		{
			return EntitySpawnListener.class;
		}
	}
}

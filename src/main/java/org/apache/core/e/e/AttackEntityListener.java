package org.apache.core.e.e;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.core.e.CE;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface AttackEntityListener extends L
{
	void onAttackEntity(AttackEntityEvent event);

	class AttackEntityEvent extends CE<AttackEntityListener>
	{
		private final PlayerEntity player;
		private final Entity target;

		public AttackEntityEvent(PlayerEntity player, Entity target)
		{
			this.player = player;
			this.target = target;
		}

		public PlayerEntity getPlayer()
		{
			return player;
		}

		public Entity getTarget()
		{
			return target;
		}

		@Override
		public void fire(ArrayList<AttackEntityListener> listeners)
		{
			for (AttackEntityListener listener : listeners)
			{
				listener.onAttackEntity(this);
			}
		}

		@Override
		public Class<AttackEntityListener> getListenerType()
		{
			return AttackEntityListener.class;
		}
	}
}

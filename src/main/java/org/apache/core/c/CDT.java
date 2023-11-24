package org.apache.core.c;

import org.apache.core.Client;
import org.apache.core.e.EM;
import org.apache.core.e.e.EntityDespawnListener;
import org.apache.core.e.e.EntitySpawnListener;
import org.apache.core.e.e.PlayerTickListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;

import java.util.ArrayList;
import java.util.HashMap;

public class CDT implements PlayerTickListener, EntitySpawnListener, EntityDespawnListener
{

	private final ArrayList<Entity> crystalsInWorld = new ArrayList<>();
	private final HashMap<Entity, Integer> crystalAges = new HashMap<>();
	private final HashMap<Entity, Boolean> crystalAttacked = new HashMap<>();
	private final HashMap<Entity, Integer> crystalSinceLastAttack = new HashMap<>();

	public void recordAttack(Entity entity)
	{
		if (!(entity instanceof EndCrystalEntity))
			return;

		crystalAttacked.remove(entity);
		crystalAttacked.put(entity, true);
	}

	public int getCrystalAge(Entity crystal)
	{
		return crystalAges.get(crystal);
	}

	public boolean isCrystalAttacked(Entity crystal)
	{
		return crystalAttacked.get(crystal);
	}

	public int sinceLastAttack(Entity crystal)
	{
		return crystalSinceLastAttack.get(crystal);
	}

	public CDT()
	{
		EM eventManager = Client.INSTANCE.eventManager();
		eventManager.add(PlayerTickListener.class, this, Integer.MAX_VALUE);
		eventManager.add(EntitySpawnListener.class, this, Integer.MAX_VALUE);
		eventManager.add(EntityDespawnListener.class, this, Integer.MAX_VALUE);
	}

	@Override
	public void onEntityDespawn(Entity entity)
	{
		if (!(entity instanceof EndCrystalEntity))
			return;

		crystalsInWorld.removeIf(e -> e == entity);
		crystalAges.remove(entity);
		crystalAttacked.remove(entity);
		crystalSinceLastAttack.remove(entity);
	}

	@Override
	public void onEntitySpawn(Entity entity)
	{
		if (!(entity instanceof EndCrystalEntity))
			return;

		crystalsInWorld.add(entity);
		crystalAges.put(entity, 0);
		crystalAttacked.put(entity, false);
		crystalSinceLastAttack.put(entity, 0);
	}

	@Override
	public void onPlayerTick()
	{
		crystalsInWorld.forEach(entity ->
		{
			crystalAges.merge(entity, 1, Integer::sum);
			crystalSinceLastAttack.merge(entity, 1, (v1, v2) ->
			{
				if (crystalAttacked.get(entity))
					return v1 + v2;
				return 0;
			});
//			if (unmarkBrokenCrystalDelay.getValue() != 0)
//				crystalAttacked.merge(entity, false, (v1, v2) ->
//				{
//					if (crystalSinceLastAttack.get(entity) > unmarkBrokenCrystalDelay.getValue())
//						return false;
//					return v1;
//				});
		});
	}
}

package org.apache.core.u;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;

import static org.apache.core.Client.MC;

public enum PU
{
	;
	public static GameMode getGameMode(PlayerEntity player)
	{
		PlayerListEntry playerListEntry = MC.getNetworkHandler().getPlayerListEntry(player.getUuid());
		if (playerListEntry == null) return GameMode.SPECTATOR;
		return playerListEntry.getGameMode();
	}
}

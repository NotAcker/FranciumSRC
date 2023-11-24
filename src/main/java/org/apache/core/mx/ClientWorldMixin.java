package org.apache.core.mx;

import org.apache.core.e.EM;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.apache.core.e.e.EntityDespawnListener;
import org.apache.core.e.e.EntitySpawnListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin
{

	@Inject(method = "addEntityPrivate", at = @At("TAIL"))
	private void onAddEntityPrivate(int id, Entity entity, CallbackInfo info) {
		if (entity != null)
			EM.fire(new EntitySpawnListener.EntitySpawnEvent(entity));
	}

	@Inject(method = "removeEntity", at = @At("TAIL"))
	private void onFinishRemovingEntity(int entityId, Entity.RemovalReason removalReason, CallbackInfo info) {
		Entity entity = MinecraftClient.getInstance().world.getEntityById(entityId);
		if (entity != null)
			EM.fire(new EntityDespawnListener.EntityDespawnEvent(entity));
	}

}

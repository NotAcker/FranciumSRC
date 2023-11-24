package org.apache.core.mx;

import org.apache.core.e.EM;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.core.e.e.AttackEntityListener.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@Inject(at = @At("HEAD"), method = "attackEntity", cancellable = true)
	private void onAttackEntity(PlayerEntity player, Entity target, CallbackInfo ci)
	{
        AttackEntityEvent event = new AttackEntityEvent(player, target);
        EM.fire(event);
        if (event.isCancelled() && ci.isCancellable())
            ci.cancel();
	}
}

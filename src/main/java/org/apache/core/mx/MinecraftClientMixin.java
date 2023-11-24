package org.apache.core.mx;

import org.apache.core.e.EM;
import net.minecraft.client.MinecraftClient;
import org.apache.core.e.e.ItemPickListener;
import org.apache.core.e.e.ItemUseListener;
import org.apache.core.e.e.TickListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin
{

	@Inject(at = @At("TAIL"), method = "tick")
	private void onTick(CallbackInfo ci)
	{
		TickListener.TickEvent event = new TickListener.TickEvent();
		EM.fire(event);
	}

	@Inject(at = @At("HEAD"), method = "doItemUse", cancellable = true)
	private void onDoItemUse(CallbackInfo ci)
	{
		ItemUseListener.ItemUseEvent event = new ItemUseListener.ItemUseEvent();
		EM.fire(event);
		if (event.isCancelled())
			ci.cancel();
	}

	@Inject(at = @At("HEAD"), method = "doItemPick", cancellable = true)
	private void onDoItemPick(CallbackInfo ci)
	{
		ItemPickListener.ItemPickEvent event = new ItemPickListener.ItemPickEvent();
		EM.fire(event);
		if (event.isCancelled())
			ci.cancel();
	}
}

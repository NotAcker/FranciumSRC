package org.apache.core.mx;

import org.apache.core.e.EM;
import org.apache.core.e.e.ItemRenderListener;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V"), method = "renderFirstPersonItem", cancellable = true)
    private void onItemRender(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        ItemRenderListener.ItemRenderEvent event = new ItemRenderListener.ItemRenderEvent(player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light);
        EM.fire(event);
        if (event.isCancelled())
            ci.cancel();
    }

}

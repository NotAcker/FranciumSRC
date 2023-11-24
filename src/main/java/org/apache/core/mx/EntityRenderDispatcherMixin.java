package org.apache.core.mx;

import org.apache.core.Client;
import org.apache.core.mi.IB;
import org.apache.core.m.ms.c.SD;
import org.apache.core.m.ms.co.H;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "renderHitbox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;drawBox(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/util/math/Box;FFFF)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void onRenderHitbox(MatrixStack matrices, VertexConsumer vertices, Entity entity, float tickDelta, CallbackInfo info, Box box) {
        if (!SD.destruct) {
            float size = H.class.cast(Client.INSTANCE.moduleManager().getModule(H.class)).getHitboxSize(entity);
            boolean shouldRender = H.class.cast(Client.INSTANCE.moduleManager().getModule(H.class)).shouldHitboxRender();

            if (size != 0 && shouldRender)
                ((IB) box).expand(size);
        }

    }

}

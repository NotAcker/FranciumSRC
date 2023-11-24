package org.apache.core.mx;

import org.apache.core.Client;
import org.apache.core.m.ms.c.SD;
import org.apache.core.m.ms.co.H;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getTargetingMargin", at = @At("HEAD"), cancellable = true)
    private void onGetTargetingMargin(CallbackInfoReturnable<Float> info) {

        if (!SD.destruct) {
            float size = H.class.cast(Client.INSTANCE.moduleManager().getModule(H.class)).getHitboxSize((Entity) (Object) this);

            if (size != 0) {
                info.setReturnValue(size);
            }
        }

    }

}

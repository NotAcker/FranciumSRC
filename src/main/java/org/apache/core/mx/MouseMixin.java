package org.apache.core.mx;

import net.minecraft.client.Mouse;
import org.apache.core.e.EM;
import org.apache.core.e.e.MouseUpdateListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "updateMouse", at = @At(value = "TAIL"), cancellable = true)
    private void onMouseUpdate(CallbackInfo ci) {
        MouseUpdateListener.MouseUpdateEvent event = new MouseUpdateListener.MouseUpdateEvent();
        EM.fire(event);
    }

}

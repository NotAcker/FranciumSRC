package org.apache.core.mx;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.apache.core.m.ms.co.WACR.mc;

@Mixin({ItemStack.class})
public abstract class ItemStackMixin {

    /**
     * Stops crystals / general items from having an animation when picked up
     */
    @Inject(method = {"getBobbingAnimationTime"}, at = {@At("HEAD")}, cancellable = true)
    private void reducePlaceDelay(CallbackInfoReturnable<Integer> info) {
        if (mc.player== null) return;
        ItemStack mainHandStack = mc.player.getMainHandStack();
        if (mainHandStack.isOf(Items.END_CRYSTAL)) {
            info.setReturnValue(0);
        }
    }
}

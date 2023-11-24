package org.apache.core.mx;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {

    @Accessor
    Session getSession();

    @Invoker("doAttack")
    public boolean leftClick();

    @Invoker("doItemPick")
    public void middleClick();

    @Invoker("doItemUse")
    public void rightClick();

}

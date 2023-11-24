package org.apache.core.m.ms.m;

import org.apache.core.m.s.BS;
import org.apache.core.m.s.DS;
import org.apache.core.Client;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class AX extends M
        implements PlayerTickListener {
    private int DropClock = 0;
    private final BS ActivateOnRightClick = BS.Builder.newInstance().setName("Activate On Right Click").setDescription("When deactivated, XP will also splash in Inventory Screen").setModule(this).setValue(true).setAvailability(() -> true).build();
    private final BS OnlyMainScreen = BS.Builder.newInstance().setName("MainList Screen Only").setDescription("When deactivated, XP will also splash in Inventory Screen").setModule(this).setValue(true).setAvailability(() -> true).build();
    private final DS speed = DS.Builder.newInstance().setName("Speed").setDescription("Dropping Speed").setModule(this).setValue(3.0).setMin(1.0).setMax(10.0).setStep(1.0).setAvailability(() -> true).build();

    public AX() {
        super("Auto XP", "automatically splashes XP When you hold them", true, C.MISC);
    }

    @Override
    public void onEnable() {
        this.DropClock = 0;
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);
    }

    @Override
    public void onPlayerTick() {
        if (Client.MC.currentScreen != null && this.OnlyMainScreen.get()) {
            return;
        }

        if (this.ActivateOnRightClick.get() && GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS) {
            return;
        }

        if (!MC.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
            return;
        }

        ++this.DropClock;

        if (this.DropClock != this.speed.get() + 1) {
            return;
        }

        this.DropClock = 0;

        MC.interactionManager.interactItem(MC.player, Hand.MAIN_HAND);
        MC.player.swingHand(Hand.MAIN_HAND);
    }
}
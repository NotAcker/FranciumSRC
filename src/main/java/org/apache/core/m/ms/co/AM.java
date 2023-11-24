package org.apache.core.m.ms.co;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.apache.core.u.ABU;
import org.apache.core.u.BU;
import org.apache.core.u.IU;
import org.apache.core.m.s.IS;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class AM extends M implements PlayerTickListener {

    private final IS switchDelay = IS.Builder.newInstance()
            .setName("Switch Delay")
            .setDescription("")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final IS glowstoneDelay = IS.Builder.newInstance()
            .setName("Glowstone Delay")
            .setDescription("")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final IS explodeDelay = IS.Builder.newInstance()
            .setName("Explode Delay")
            .setDescription("")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final IS slotExplode = IS.Builder.newInstance()
            .setName("Slot Explode")
            .setDescription("")
            .setModule(this)
            .setValue(1)
            .setMin(1)
            .setMax(9)
            .setAvailability(() -> true)
            .build();


    private int switchClock = 0;
    private int glowstoneClock = 0;
    private int explodeClock = 0;

    public AM() {
        super("Anchor Macro", "", false, C.COMBAT);
        switchClock = 0;
        glowstoneClock = 0;
        explodeClock = 0;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);

        switchClock = 0;
        glowstoneClock = 0;
        explodeClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);

        switchClock = 0;
        glowstoneClock = 0;
        explodeClock = 0;
    }

    private int getSlot() {
        return slotExplode.get() - 1;
    }

    @Override
    public void onPlayerTick() {
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS) {
            if (MC.crosshairTarget instanceof BlockHitResult hit && BU.isBlock(Blocks.RESPAWN_ANCHOR, hit.getBlockPos())) {
                MC.options.useKey.setPressed(false);


                if (!ABU.isAnchorCharged(hit.getBlockPos())) {

                    if (!MC.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
                        if (switchClock != switchDelay.get()) {
                            switchClock++;
                            return;
                        }

                        switchClock = 0;

                        IU.selectItemFromHotbar(Items.GLOWSTONE);
                    }

                    if (MC.player.getMainHandStack().isOf(Items.GLOWSTONE)) {
                        if (glowstoneClock != glowstoneDelay.get()) {
                            glowstoneClock++;
                            return;
                        }

                        glowstoneClock = 0;

                        ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, hit);
                        if (result.isAccepted() && result.shouldSwingHand())
                            MC.player.swingHand(Hand.MAIN_HAND);
                    }

                }

                if (ABU.isAnchorCharged(hit.getBlockPos())) {

                    int slot = getSlot();

                    if (MC.player.getInventory().selectedSlot != slot) {
                        if (switchClock != switchDelay.get()) {
                            switchClock++;
                            return;
                        }

                        switchClock = 0;

                        MC.player.getInventory().selectedSlot = slot;
                    }

                    if (MC.player.getInventory().selectedSlot == slot) {
                        if (explodeClock != explodeDelay.get()) {
                            explodeClock++;
                            return;
                        }

                        explodeClock = 0;

                        ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, hit);
                        if (result.isAccepted() && result.shouldSwingHand())
                            MC.player.swingHand(Hand.MAIN_HAND);
                    }

                }


            }
        }
    }

}

package org.apache.core.m.ms.co;

import org.apache.core.u.BU;
import org.apache.core.u.CrU;
import org.apache.core.u.RoU;
import org.apache.core.Client;
import org.apache.core.e.EM;
import org.apache.core.e.e.ItemUseListener;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.IS;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class FC extends M implements ItemUseListener, PlayerTickListener {
    public FC() {
        super(" Fast Crystals", "make crystal placing fast", false, C.COMBAT);
    }

    private final IS placeInterval = IS.Builder.newInstance()
            .setName("Place interval")
            .setDescription("the delay between placing crystals (in tick)")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private int delay = 0;

    @Override
    public void onEnable()
    {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);
        eventManager.add(ItemUseListener.class, this);

        delay = 0;
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        EM eventManager = Client.INSTANCE.eventManager();
        eventManager.remove(PlayerTickListener.class, this);
        eventManager.remove(ItemUseListener.class, this);
    }

    @Override
    public void onItemUse(ItemUseEvent event)
    {
        ItemStack mainHandStack = MC.player.getMainHandStack();
        if (MC.crosshairTarget.getType() == HitResult.Type.BLOCK)
        {
            BlockHitResult hit = (BlockHitResult) MC.crosshairTarget;
            if (mainHandStack.isOf(Items.END_CRYSTAL) &&
                    (BU.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()) ||
                            BU.isBlock(Blocks.BEDROCK, hit.getBlockPos()))) {
                event.cancel();
            }
        }
    }

    @Override
    public void onPlayerTick() {

        if (!MC.player.getMainHandStack().isOf(Items.END_CRYSTAL)) { return; }

        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS) { return; }

        if (delay != placeInterval.get()) {
            delay++;
            return;
        }

        delay = 0;

        Vec3d camPos = MC.player.getEyePos();
        BlockHitResult blockHit = MC.world.raycast(new RaycastContext(camPos, camPos.add(RoU.getClientLookVec().multiply(4.5)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, MC.player));

        if (BU.isBlock(Blocks.OBSIDIAN, blockHit.getBlockPos()) || BU.isBlock(Blocks.BEDROCK, blockHit.getBlockPos())) {
            if (CrU.canPlaceCrystalServer(blockHit.getBlockPos())) {
                ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, blockHit);
                if (result.isAccepted() && result.shouldSwingHand()) { MC.player.swingHand(Hand.MAIN_HAND); }
            }
        }

    }
}

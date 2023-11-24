package org.apache.core.m.ms.co;

import org.apache.core.u.BU;
import org.apache.core.u.CrU;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.apache.core.e.e.ItemUseListener;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static org.apache.core.Client.MC;

public class CC extends M implements PlayerTickListener, ItemUseListener {

	private final IS placeInterval = IS.Builder.newInstance()
			.setName("place speed")
			.setDescription("the speed between placing crystals (in tick)")
			.setModule(this)
			.setValue(4)
			.setMin(0)
			.setMax(20)
			.setAvailability(() -> true)
			.build();

	private final IS breakInterval = IS.Builder.newInstance()
			.setName("break speed")
			.setDescription("the speed between breaking crystals (in tick)")
			.setModule(this)
			.setValue(4)
			.setMin(0)
			.setMax(20)
			.setAvailability(() -> true)
			.build();

	private final BS activateOnRightClick = BS.Builder.newInstance()
			.setName("activate on Right Click")
			.setDescription("will only activate on right click when enabled")
			.setModule(this)
			.setValue(true)
			.setAvailability(() -> true)
			.build();

	private final BS stopOnKill = BS.Builder.newInstance()
			.setName("stop on Kill")
			.setDescription("automatically stops crystalling when someone close to you dies")
			.setModule(this)
			.setValue(false)
			.setAvailability(() -> true)
			.build();

	private final BS simulateClick = BS.Builder.newInstance()
			.setName("Simulate Clicks (WIP)")
			.setDescription("simulates clicks like you clicks by yourself")
			.setModule(this)
			.setValue(false)
			.setAvailability(() -> true)
			.build();

	private int crystalPlaceClock = 0;
	private int crystalBreakClock = 0;
    private boolean crystalling = false;
	private Robot robot;

	public CC()
	{
		super("LTFC Crystal", "elias' special need >:3", true, C.COMBAT);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(PlayerTickListener.class, this);
		eventManager.add(ItemUseListener.class, this);
		crystalPlaceClock = 0;
		crystalBreakClock = 0;
        crystalling = false;
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		eventManager.remove(PlayerTickListener.class, this);
		eventManager.remove(ItemUseListener.class, this);
	}

	private boolean isDeadBodyNearby()
	{
		return MC.world.getPlayers().parallelStream()
				.filter(e -> MC.player != e)
				.filter(e -> e.squaredDistanceTo(MC.player) < 36)
				.anyMatch(LivingEntity::isDead);
	}

	@Override
	public void onPlayerTick()
	{
		boolean dontPlaceCrystal = crystalPlaceClock != 0;
		boolean dontBreakCrystal = crystalBreakClock != 0;
		if (dontPlaceCrystal)
			crystalPlaceClock--;
		if (dontBreakCrystal)
			crystalBreakClock--;
		if (activateOnRightClick.get() && GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS) {
            return;
        }
		ItemStack mainHandStack = MC.player.getMainHandStack();
		if (!mainHandStack.isOf(Items.END_CRYSTAL))
			return;
		if (stopOnKill.get() && isDeadBodyNearby())
			return;

        if (MC.crosshairTarget instanceof BlockHitResult hit) {
            BlockPos block = hit.getBlockPos();
            if (!dontPlaceCrystal && CrU.canPlaceCrystalServer(block)) {
                crystalPlaceClock = placeInterval.get();

                ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, ((BlockHitResult) MC.crosshairTarget));
                if (result.isAccepted() && result.shouldSwingHand()) {
                    MC.player.swingHand(Hand.MAIN_HAND);
                }

            }
        }

		if (MC.crosshairTarget instanceof EntityHitResult hit) {
			if (!dontBreakCrystal && (hit.getEntity() instanceof EndCrystalEntity || hit.getEntity() instanceof SlimeEntity)) {
				crystalBreakClock = breakInterval.get();

                MC.interactionManager.attackEntity(MC.player, ((EntityHitResult) MC.crosshairTarget).getEntity());
                MC.player.swingHand(Hand.MAIN_HAND);
			}
		}

	}

	@Override
	public void onItemUse(ItemUseEvent event)
	{
		ItemStack mainHandStack = MC.player.getMainHandStack();
		if (MC.crosshairTarget.getType() == HitResult.Type.BLOCK) {
			BlockHitResult hit = (BlockHitResult) MC.crosshairTarget;
			if (mainHandStack.isOf(Items.END_CRYSTAL) &&
					(BU.isBlock(Blocks.OBSIDIAN, hit.getBlockPos())
							|| BU.isBlock(Blocks.BEDROCK, hit.getBlockPos())))
				event.cancel();
		}
	}
}

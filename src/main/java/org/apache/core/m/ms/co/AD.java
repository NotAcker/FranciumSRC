package org.apache.core.m.ms.co;

import org.apache.core.u.CrU;
import org.apache.core.Client;
import org.apache.core.e.EM;
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
import org.apache.core.u.BU;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;


public class AD extends M implements PlayerTickListener, ItemUseListener
{

	private final IS placeInterval = IS.Builder.newInstance()
			.setName("Place Interval")
			.setDescription("the interval between placing crystals (in tick)")
			.setModule(this)
			.setValue(3)
			.setMin(0)
			.setMax(3)
			.setAvailability(() -> false)
			.build();
	private final IS MaxCrystals = IS.Builder.newInstance()
			.setName("Max Crystals")
			.setDescription("the interval between breaking crystals (in tick)")
			.setModule(this)
			.setValue(2)
			.setMin(1)
			.setMax(2)
			.setAvailability(() -> false)
			.build();

	private final IS breakInterval = IS.Builder.newInstance()
			.setName("Break Interval")
			.setDescription("the interval between breaking crystals (in tick)")
			.setModule(this)
			.setValue(2)
			.setMin(0)
			.setMax(2)
			.setAvailability(() -> false)
			.build();

	private final BS activateOnRightClick = BS.Builder.newInstance()
			.setName("Activate On RightClick")
			.setDescription("will only activate on right click when enabled")
			.setModule(this)
			.setValue(false)
			.setAvailability(() -> true)
			.build();

	private final BS stopOnKill = BS.Builder.newInstance()
			.setName("Stop On Kill")
			.setDescription("automatically stops crystalling when someone close to you dies")
			.setModule(this)
			.setValue(false)
			.setAvailability(() -> true)
			.build();

	private int crystalPlaceClock = 0;
	private int crystalBreakClock = 0;

	public AD()
	{
		super("Auto Dtap", "Double pop like theo404", false, C.COMBAT);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(PlayerTickListener.class, this);
		eventManager.add(ItemUseListener.class, this);
		crystalPlaceClock = 0;
		crystalBreakClock = 0;
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		EM eventManager = Client.INSTANCE.eventManager();
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
		if (activateOnRightClick.get() && GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS)
			return;
		ItemStack mainHandStack = MC.player.getMainHandStack();
		if (!mainHandStack.isOf(Items.END_CRYSTAL))
			return;
		if (stopOnKill.get() && isDeadBodyNearby())
			return;

		if (MC.crosshairTarget instanceof EntityHitResult hit)
		{
			if (!dontBreakCrystal && (hit.getEntity() instanceof EndCrystalEntity || hit.getEntity() instanceof SlimeEntity))
			{
				crystalBreakClock = breakInterval.get();
				MC.interactionManager.attackEntity(MC.player, hit.getEntity());
				MC.player.swingHand(Hand.MAIN_HAND);
				Client.INSTANCE.crystalDataTracker().recordAttack(hit.getEntity());
			}
		}
		if (MC.crosshairTarget instanceof BlockHitResult hit)
		{
			BlockPos block = hit.getBlockPos();
			if (!dontPlaceCrystal && CrU.canPlaceCrystalServer(block))
			{
				crystalPlaceClock = placeInterval.get();
				ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, hit);
				if (result.isAccepted() && result.shouldSwingHand())
					MC.player.swingHand(Hand.MAIN_HAND);
			}
		}
	}

	@Override
	public void onItemUse(ItemUseEvent event)
	{
		ItemStack mainHandStack = MC.player.getMainHandStack();
		if (MC.crosshairTarget.getType() == HitResult.Type.BLOCK)
		{
			BlockHitResult hit = (BlockHitResult) MC.crosshairTarget;
			if (mainHandStack.isOf(Items.END_CRYSTAL) && BU.isBlock(Blocks.OBSIDIAN, hit.getBlockPos()))
				event.cancel();
		}
	}
}
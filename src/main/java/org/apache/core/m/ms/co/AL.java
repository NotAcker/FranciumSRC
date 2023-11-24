package org.apache.core.m.ms.co;

import org.apache.core.k.K;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import org.apache.core.m.s.KS;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.screen.slot.SlotActionType;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.u.IU;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class AL extends M implements PlayerTickListener
{

	private IS minTotems = new IS.Builder()
			.setName("min totems")
			.setDescription("minimum totems to keep in your inventory")
			.setModule(this)
			.setValue(6)
			.setMin(0)
			.setMax(36)
			.setAvailability(() -> true)
			.build();

	private IS minPearls = new IS.Builder()
			.setName("min pearls")
			.setDescription("minimum pearls to keep in your inventory")
			.setModule(this)
			.setValue(64)
			.setMin(0)
			.setMax(576)
			.setAvailability(() -> true)
			.build();

	private BS totemFirst = new BS.Builder()
			.setName("totem first?")
			.setDescription("whether or not to drop totems first")
			.setModule(this)
			.setValue(false)
			.setAvailability(() -> true)
			.build();

	private IS dropInterval = new IS.Builder()
			.setName("drop speed")
			.setDescription("the speed of dropping items")
			.setModule(this)
			.setValue(0)
			.setMin(0)
			.setMax(10)
			.setAvailability(() -> true)
			.build();

	private K activateKeybind = new K(
			"AutoLootYeeter_activateKeybind",
			GLFW.GLFW_KEY_X,
			false,
			false,
			null
	);

	private KS activateKey = new KS.Builder()
			.setName("activate key")
			.setDescription("the key to activate it")
			.setModule(this)
			.setValue(activateKeybind)
			.build();

	private int dropClock = 0;

	public AL()
	{
		super("Auto Looter", "loots", false, C.COMBAT);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(PlayerTickListener.class, this);
		dropClock = 0;
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		eventManager.remove(PlayerTickListener.class, this);
	}

	@Override
	public void onPlayerTick()
	{
		if (dropClock != 0)
		{
			dropClock--;
			return;
		}
		if (!(mc.currentScreen instanceof InventoryScreen))
			return;
		if (!activateKeybind.isDown())
			return;
		if (!looting())
			return;
		int slot = findSlot();
		if (slot == -1)
			return;
		dropClock = dropInterval.get();
		mc.interactionManager.clickSlot(((InventoryScreen) mc.currentScreen).getScreenHandler().syncId, slot, 1, SlotActionType.THROW, mc.player);
	}

	private boolean looting()
	{
		List<Entity> collidedEntities = mc.world.getOtherEntities(mc.player, mc.player.getBoundingBox().expand(1, 0.5, 1).expand(1.0E-7D));
		for (Entity e : collidedEntities)
		{
			if (e instanceof ItemEntity itemStack)
			{
				Item item = itemStack.getStack().getItem();
				if (item != Items.TOTEM_OF_UNDYING && item != Items.ENDER_PEARL)
				{
					if (item == Items.END_CRYSTAL ||
							item == Items.RESPAWN_ANCHOR ||
							item == Items.GOLDEN_APPLE)
						return true;
					if (item instanceof ToolItem toolItem)
					{
						if (toolItem.getMaterial() == ToolMaterials.NETHERITE ||
								toolItem.getMaterial() == ToolMaterials.DIAMOND)
							return true;
					}
					if (item instanceof ArmorItem armorItem)
					{
						if (armorItem.getMaterial() == ArmorMaterials.NETHERITE ||
								armorItem.getMaterial() == ArmorMaterials.DIAMOND)
							return true;
					}
				}
			}
		}
		return false;
	}

	private int findSlot()
	{
		if (totemFirst.get())
		{
			int totemSlot = findTotemSlot();
			if (totemSlot == -1)
				return findPearlSlot();
			return totemSlot;
		}
		int pearlSlot = findPearlSlot();
		if (pearlSlot == -1)
			return findTotemSlot();
		return pearlSlot;
	}

	private int findPearlSlot()
	{
		PlayerInventory inv = mc.player.getInventory();
		int pearlCount = IU.countItem(Items.ENDER_PEARL);
		int fewestPearlSlot = -1;
		for (int i = 9; i < 36; i++)
		{
			ItemStack itemStack = inv.main.get(i);
			if (itemStack.getItem() == Items.ENDER_PEARL)
			{
				if (fewestPearlSlot == -1 ||
						itemStack.getCount() < inv.main.get(fewestPearlSlot).getCount())
				{
					fewestPearlSlot = i;
				}
			}
		}
		if (fewestPearlSlot == -1)
			return -1;
		if (pearlCount - inv.main.get(fewestPearlSlot).getCount() >= minPearls.get())
		{
			return fewestPearlSlot;
		}
		return -1;
	}

	private int findTotemSlot()
	{
		PlayerInventory inv = mc.player.getInventory();
		int totemCount = IU.countItem(Items.TOTEM_OF_UNDYING);
		if (totemCount <= minTotems.get())
			return -1;
		for (int i = 9; i < 36; i++)
		{
			ItemStack itemStack = inv.main.get(i);
			if (itemStack.getItem() == Items.TOTEM_OF_UNDYING)
			{
				return i;
			}
		}
		return -1;
	}

}

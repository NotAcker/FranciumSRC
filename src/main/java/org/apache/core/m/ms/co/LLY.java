package org.apache.core.m.ms.co;

import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.k.K;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import org.apache.core.m.s.KS;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class LLY extends M implements PlayerTickListener {

    private IS delay = IS.Builder.newInstance()
            .setName("Delay")
            .setDescription("")
            .setModule(this)
            .setAvailability(() -> true)
            .setValue(0)
            .setMin(0)
            .setMax(10)
            .build();

    private IS minTotems = IS.Builder.newInstance()
            .setName("Min Totems")
            .setDescription("")
            .setModule(this)
            .setAvailability(() -> true)
            .setValue(5)
            .setMin(0)
            .setMax(20)
            .build();

    private IS minPearls = IS.Builder.newInstance()
            .setName("Min Pearls Stacks")
            .setDescription("")
            .setModule(this)
            .setAvailability(() -> true)
            .setValue(1)
            .setMin(0)
            .setMax(10)
            .build();

    private IS minCrystals = IS.Builder.newInstance()
            .setName("Min Crystals Stacks")
            .setDescription("")
            .setModule(this)
            .setAvailability(() -> true)
            .setValue(1)
            .setMin(0)
            .setMax(10)
            .build();

    private BS keepElytra = BS.Builder.newInstance()
            .setName("Keep Elytra")
            .setDescription("")
            .setModule(this)
            .setAvailability(() -> true)
            .setValue(true)
            .build();

    private final BS activateOnKey = new BS.Builder()
            .setName("Activate On Key")
            .setDescription("whether or not to activate it only when pressing the selected key")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    public final KS activateKey = new KS.Builder()
            .setName("Keybind")
            .setDescription("the key to activate it")
            .setModule(this)
            .setValue(new K("", GLFW.GLFW_KEY_C,false,false,null))
            .build();

    private int delayClock = 0;

    public LLY() {
        super("Auto Loot Yeeter", "", false, C.COMBAT);
        delayClock = 0;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);

        delayClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);

        delayClock = 0;
    }

    private int countItemInInventory(Item item) {
        PlayerInventory inv = MC.player.getInventory();

        int count = 0;

        for (int i = 9; i < 36; i++) {
            ItemStack itemStack = inv.getStack(i);
            if (itemStack.isOf(item))
                count += itemStack.getCount();
        }

        return count;
    }

    private boolean checkItem(ItemStack itemUnderMouse) {
        if (itemUnderMouse.isOf(Items.TOTEM_OF_UNDYING)) {
            if (countItemInInventory(Items.TOTEM_OF_UNDYING) <= minTotems.get())
                return false;
        } else if (itemUnderMouse.isOf(Items.END_CRYSTAL)) {
            if ((Integer) (countItemInInventory(Items.END_CRYSTAL) / 64) <= minCrystals.get())
                return false;
        } else if (itemUnderMouse.isOf(Items.ENDER_PEARL)) {
            if ((Integer) (countItemInInventory(Items.ENDER_PEARL) / 16) <= minPearls.get())
                return false;
        } else if (itemUnderMouse.isOf(Items.ELYTRA) && !keepElytra.get()) {
            return false;
        } else if (itemUnderMouse.getItem() instanceof ToolItem toolItem) {
            if (toolItem.getMaterial() == ToolMaterials.NETHERITE ||
                    toolItem.getMaterial() == ToolMaterials.DIAMOND)
                return false;
        } else if (itemUnderMouse.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.getMaterial() == ArmorMaterials.NETHERITE ||
                    armorItem.getMaterial() == ArmorMaterials.DIAMOND)
                return false;
        }

        return true;

    }

    @Override
    public void onPlayerTick() {

        if (activateOnKey.get() && GLFW.glfwGetKey(MC.getWindow().getHandle(), activateKey.get().getKey()) != GLFW.GLFW_PRESS)
            return;

        if (MC.currentScreen instanceof InventoryScreen screen) {

            Slot focusedSlot = screen.focusedSlot;

            if (focusedSlot != null) {
                int slot = focusedSlot.getIndex();

                if (slot <= 35) {

                    if (delayClock != delay.get()) {
                        delayClock++;
                        return;
                    }

                    ItemStack itemUnderMouse = MC.player.getInventory().getStack(slot);

                    if (checkItem(itemUnderMouse)) {
                        MC.interactionManager.clickSlot(screen.getScreenHandler().syncId,
                                slot,
                                1,
                                SlotActionType.THROW,
                                MC.player);
                        delayClock = 0;
                    }

                }
            }
        }
    }
}

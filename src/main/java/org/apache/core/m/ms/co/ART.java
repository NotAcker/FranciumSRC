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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

public class ART extends M implements PlayerTickListener {


    private final BS autoSwitch = new BS.Builder()
            .setName("autoSwitch")
            .setDescription("automatically switches to your totem slot")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    private final IS delay = new IS.Builder()
            .setName("delay")
            .setDescription("the delay for auto switch after opening inventory")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(autoSwitch::get)
            .build();

    private final IS totemSlot = new IS.Builder()
            .setName("totemSlot")
            .setDescription("your totem slot")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(8)
            .setAvailability(autoSwitch::get)
            .build();

    private final BS forceTotem = new BS.Builder()
            .setName("forceTotem")
            .setDescription("replace your main hand item (if there is one)")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    private final BS activateOnKey = new BS.Builder()
            .setName("Activate On Key")
            .setDescription("whether or not to activate it only when pressing the selected key")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    private final K activateKeybind = new K(
            "AutoInventoryTotem_activateKeybind",
            GLFW.GLFW_KEY_C,
            false,
            false,
            null
    );

    private final KS activateKey = new KS.Builder()
            .setName("Activate Key")
            .setDescription("the key to activate it")
            .setModule(this)
            .setValue(activateKeybind)
            .build();

    public ART() {
        super(" Retotem", "Automatically puts on totems for you when you are in inventory", false, C.COMBAT);
    }

    private int invClock = -1;

    @Override
    public void onEnable() {
        super.onEnable();
        invClock = -1;
        eventManager.add(PlayerTickListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);
    }

    @Override
    public void onPlayerTick() {
        if (!(mc.currentScreen instanceof InventoryScreen)) {
            invClock = -1;
            return;
        }
        if (invClock == -1)
            invClock = delay.get();
        if (invClock > 0) {
            invClock--;
            return;
        }
        PlayerInventory inv = mc.player.getInventory();
        if (autoSwitch.get())
            inv.selectedSlot = totemSlot.get();
        if (activateOnKey.get() && !activateKeybind.isDown())
            return;
        if (inv.offHand.get(0).getItem() != Items.TOTEM_OF_UNDYING) {
            int slot = findTotemSlot();
            if (slot != -1) {
                mc.interactionManager.clickSlot(((InventoryScreen) mc.currentScreen).getScreenHandler().syncId, slot, 40, SlotActionType.SWAP, mc.player);
                return;
            }
        }
        ItemStack mainHand = inv.main.get(inv.selectedSlot);
        if (mainHand.isEmpty() ||
                forceTotem.get() && mainHand.getItem() != Items.TOTEM_OF_UNDYING) {
            int slot = findTotemSlot();
            if (slot != -1) {
                mc.interactionManager.clickSlot(((InventoryScreen) mc.currentScreen).getScreenHandler().syncId, slot, inv.selectedSlot, SlotActionType.SWAP, mc.player);
            }
        }
    }

    private int findTotemSlot() {
        PlayerInventory inv = mc.player.getInventory();
        for (int i = 9; i < 36; i++) {
            if (inv.main.get(i).getItem() == Items.TOTEM_OF_UNDYING)
                return i;
        }
        return -1;
    }

}

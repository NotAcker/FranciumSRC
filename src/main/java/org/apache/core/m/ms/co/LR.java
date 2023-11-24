package org.apache.core.m.ms.co;

import org.apache.core.k.K;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.KS;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class LR extends M implements PlayerTickListener {

    private final IS delay = new IS.Builder()
            .setName("Delay")
            .setDescription("the delay for auto switch after opening inventory")
            .setModule(this)
            .setValue(1)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final IS totemSlot = new IS.Builder()
            .setName("Totem Slot")
            .setDescription("your totem slot")
            .setModule(this)
            .setValue(6)
            .setMin(1)
            .setMax(9)
            .setAvailability(() -> true)
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
            .setValue(new K("", GLFW.GLFW_KEY_V,false,false,null))
            .build();

    public LR() {
        super("Legit Retotem", "Automatically puts on totems for you when you are in inventory", false, C.COMBAT);
    }

    private int totemClock = 0;

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);

        totemClock = 0;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);
    }

    private boolean check() {
        return      (activateOnKey.get()
                && (GLFW.glfwGetKey(MC.getWindow().getHandle(),
                activateKey.get().getKey()) == GLFW.GLFW_PRESS))
                || (!activateOnKey.get());
    }

    @Override
    public void onPlayerTick() {

        if (MC.currentScreen instanceof InventoryScreen) {

            InventoryScreen invScreen = (InventoryScreen) MC.currentScreen;

            if (getFocusedSlot(invScreen) != null && check()) {

                int slot = getFocusedSlot(invScreen).getIndex();

                if (slot <= 35) {
                    if (!isTotem(totemSlot.get() - 1) && isTotem(slot)) {

                        if (totemClock != delay.get()) {
                            totemClock++;
                            return;
                        }

                        MC.interactionManager.clickSlot(
                                invScreen.getScreenHandler().syncId,
                                slot,
                                totemSlot.get() - 1,
                                SlotActionType.SWAP,
                                MC.player);
                        totemClock = 0;
                    }

                    if (!MC.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)
                            && isTotem(slot)) {

                        if (totemClock != delay.get()) {
                            totemClock++;
                            return;
                        }

                        MC.interactionManager.clickSlot(
                                invScreen.getScreenHandler().syncId,
                                slot,
                                40,
                                SlotActionType.SWAP,
                                MC.player);
                        totemClock = 0;
                    }
                }

            }
        } else {
            totemClock = 0;
        }

    }

    private Slot getFocusedSlot(InventoryScreen screen) {
        return screen.focusedSlot;
    }

    private boolean isTotem(int slot) {
        return MC.player.getInventory().main.get(slot).isOf(Items.TOTEM_OF_UNDYING);
    }
}

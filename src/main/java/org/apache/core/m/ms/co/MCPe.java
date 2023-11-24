package org.apache.core.m.ms.co;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.apache.core.e.e.*;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.DS;
import org.apache.core.m.s.IS;
import org.apache.core.mx.MinecraftClientAccessor;
import org.apache.core.u.IU;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class MCPe extends M implements PlayerTickListener, ItemPickListener, MouseUpdateListener {

    public MCPe() {
        super(" Click Pearl", "auto pearl when you press middle click", false, C.MISC);
    }

    private IS switchDelay = IS.Builder.newInstance()
            .setName("Switch Delay")
            .setDescription("")
            .setAvailability(() -> true)
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(10)
            .build();

    private IS pearlDelay = IS.Builder.newInstance()
            .setName("Pearl Delay")
            .setDescription("")
            .setAvailability(() -> true)
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(10)
            .build();

    private BS goToPrevSlot = BS.Builder.newInstance()
            .setName("Go To Prev Slot")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private BS lookDownLegit = BS.Builder.newInstance()
            .setName("Look Down Legit")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private IS untilPitch = IS.Builder.newInstance()
            .setName("Until Pitch")
            .setDescription("")
            .setModule(this)
            .setValue(70)
            .setMin(0)
            .setMax(80)
            .setAvailability(lookDownLegit::get)
            .build();

    private DS pitchAdd = DS.Builder.newInstance()
            .setName("Speed")
            .setDescription("")
            .setModule(this)
            .setValue(0.8f)
            .setMin(0.1f)
            .setMax(2.0f)
            .setStep(0.1f)
            .setAvailability(lookDownLegit::get)
            .build();

    private DS pitchAccel = DS.Builder.newInstance()
            .setName("Acceleration")
            .setDescription("")
            .setModule(this)
            .setValue(0.01f)
            .setMin(0.01f)
            .setMax(0.2f)
            .setStep(0.01f)
            .setAvailability(lookDownLegit::get)
            .build();



    enum PearlStatus {

        IDLE,
        PEARLED,
        PREV_SLOT

    }

    enum RotatorStatus {

        IDLE,
        DOWN,
        BACK

    }


    private int switchClock = 0;
    private int pearlClock = 0;
    private PearlStatus pearlStatus = PearlStatus.IDLE;
    private RotatorStatus rotatorStatus = RotatorStatus.IDLE;
    private int prevSlot = 0;
    private float prevPitch = 0;
    private float pitch = 0;

    public void resetValues() {
        switchClock = 0;
        pearlClock = 0;
        pearlStatus = PearlStatus.IDLE;
        rotatorStatus = RotatorStatus.IDLE;
        prevSlot = 0;
        prevPitch = 0;
        pitch = 0;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        eventManager.add(PlayerTickListener.class, this);
        eventManager.add(ItemPickListener.class, this);
        eventManager.add(MouseUpdateListener.class, this);

        resetValues();
    }

    @Override
    public void onDisable() {
        super.onDisable();

        eventManager.remove(PlayerTickListener.class, this);
        eventManager.remove(ItemPickListener.class, this);
        eventManager.remove(MouseUpdateListener.class, this);


        resetValues();
    }

    @Override
    public void onPlayerTick() {

        if (MC.currentScreen == null) {
            if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_MIDDLE) != GLFW.GLFW_PRESS)
                resetValues();

            if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS) {

                if (!MC.player.getMainHandStack().isOf(Items.ENDER_PEARL) && pearlStatus == PearlStatus.IDLE) {
                    if (switchClock != switchDelay.get()) {
                        switchClock++;
                        return;
                    }
 
                    switchClock = 0;
                    prevSlot = MC.player.getInventory().selectedSlot;

                    IU.selectItemFromHotbar(Items.ENDER_PEARL);
                }

                if (MC.player.getMainHandStack().isOf(Items.ENDER_PEARL)) {

                    if ((MC.player.getPitch() >= untilPitch.get() && lookDownLegit.get()) || (!lookDownLegit.get())) {
                        if (pearlStatus == PearlStatus.IDLE && rotatorStatus == RotatorStatus.DOWN) {
                            if (pearlClock != pearlDelay.get()) {
                                pearlClock++;
                                return;
                            }

                            pearlClock = 0;

                            ((MinecraftClientAccessor) MC).rightClick();
                            pearlStatus = PearlStatus.PEARLED;
                            pitch = 0;
                        }
                    }

                    if (pearlStatus == PearlStatus.PEARLED && goToPrevSlot.get()) {
                        if (switchClock != switchDelay.get()) {
                            switchClock++;
                            return;
                        }

                        switchClock = 0;

                        MC.player.getInventory().selectedSlot = prevSlot;

                        pearlStatus = PearlStatus.PREV_SLOT;
                    }

                }

            }
        }
    }

    @Override
    public void onItemPick(ItemPickEvent event) {
        event.cancel();
    }

    @Override
    public void onMouseUpdate(MouseUpdateEvent event) {
        if (MC.player != null) {
            if (MC.currentScreen == null) {
                if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS) {
                    if (MC.player.getMainHandStack().isOf(Items.ENDER_PEARL)) {

                        if (MC.player.getPitch() <= untilPitch.get()) {

                            if (pitch == 0)
                                pitch = pitchAdd.get().floatValue();

                            if (rotatorStatus == RotatorStatus.IDLE) {
                                pitch *= ((pitchAccel.get().floatValue() * MC.options.getMouseSensitivity().getValue()) + 1.0f);
                                MC.player.setPitch(MC.player.getPitch() + pitch);
                            }

                        } else {
                            pitch = 0;
                            rotatorStatus = RotatorStatus.DOWN;
                        }

                    }
                }
            }
        }
    }
}

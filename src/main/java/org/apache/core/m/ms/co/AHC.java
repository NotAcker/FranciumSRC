package org.apache.core.m.ms.co;

import org.apache.core.u.BU;
import org.apache.core.u.HU;
import org.apache.core.u.IU;
import org.apache.core.Client;
import org.apache.core.k.K;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import org.apache.core.m.s.KS;
import net.minecraft.block.Blocks;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class AHC extends M implements PlayerTickListener {

    public AHC() {
        super("Auto Hit Crystal", "Credits to pycat", false, C.COMBAT);
    }


    private IS firstCrystalInterval = IS.Builder.newInstance()
            .setName("First Crystal Interval")
            .setDescription("interval of first crystal place after placing obsidian (in tick)")
            .setModule(this)
            .setValue(5)
            .setMin(0)
            .setMax(10)
            .setAvailability(() -> true)
            .build();

    private final IS placeInterval = IS.Builder.newInstance()
            .setName("Place Interval")
            .setDescription("the interval between placing crystals (in tick)")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final IS breakInterval = IS.Builder.newInstance()
            .setName("Break Interval")
            .setDescription("the interval between breaking crystals (in tick)")
            .setModule(this)
            .setValue(0)
            .setMin(0)
            .setMax(20)
            .setAvailability(() -> true)
            .build();

    private final BS stopOnKill = BS.Builder.newInstance()
            .setName("Stop On Kill")
            .setDescription("automatically stops crystalling when someone close to you dies")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    private final BS workOnKeybind = BS.Builder.newInstance()
            .setName("Work On Keybind?")
            .setDescription("work on keybind")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final BS workWithTotem = BS.Builder.newInstance()
            .setName("Work With Totem")
            .setDescription("work with totem")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();

    public final KS activateKey = new KS.Builder()
            .setName("Keybind")
            .setDescription("the key to activate it")
            .setModule(this)
            .setValue(new K("", GLFW.GLFW_KEY_R,false,false,null))
            .build();

    enum HitCrystalStatus {
        IDLE,
        PLACED_OBBY,
        CRYSTALLING
    }

    private int crystalPlaceClock = 0;
    private int crystalBreakClock = 0;
    private int firstCrystalDelay = 0;

    private HitCrystalStatus hitCrystalStatus = HitCrystalStatus.IDLE;

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);

        crystalPlaceClock = 0;
        crystalBreakClock = 0;
        firstCrystalDelay = 0;

        hitCrystalStatus = HitCrystalStatus.IDLE;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);
    }

    private boolean check() {
        ItemStack mainHand = MC.player.getMainHandStack();

        return  !workOnKeybind.get()
                && (mainHand.getItem() instanceof SwordItem ||
                    (workWithTotem.get() && mainHand.isOf(Items.TOTEM_OF_UNDYING)));
    }


    private boolean isDeadBodyNearby()
    {
        return MC.world.getPlayers().parallelStream()
                .filter(e -> MC.player != e)
                .filter(e -> e.squaredDistanceTo(MC.player.getPos()) < 50)
                .anyMatch(PlayerEntity::isDead);

    }


    @Override
    public void onPlayerTick() {

        if (MC.currentScreen != null)
            return;

        if ((GLFW.glfwGetKey(MC.getWindow().getHandle(), activateKey.get().getKey()) != GLFW.GLFW_PRESS && workOnKeybind.get())
        || (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) != GLFW.GLFW_PRESS && !workOnKeybind.get())) {
            firstCrystalDelay = 0;
            hitCrystalStatus = HitCrystalStatus.IDLE;
            return;
        }

        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS
                && !workOnKeybind.get()
                && !(hitCrystalStatus == HitCrystalStatus.CRYSTALLING)
                && !check())
            return;

        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_2) == GLFW.GLFW_PRESS && check())
            MC.options.useKey.setPressed(false);


        if (hitCrystalStatus == HitCrystalStatus.IDLE
                && MC.crosshairTarget instanceof BlockHitResult hit
                && !(BU.isBlock(Blocks.OBSIDIAN, hit.getBlockPos())
                    || BU.isBlock(Blocks.AIR, hit.getBlockPos()))) {


            if (IU.selectItemFromHotbar(Items.OBSIDIAN)) {
                ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, hit);
                if (result.isAccepted() && result.shouldSwingHand()) {
                    MC.player.swingHand(Hand.MAIN_HAND);
                    hitCrystalStatus = HitCrystalStatus.PLACED_OBBY;
                }
            }

        }

        if (firstCrystalDelay != firstCrystalInterval.get() && hitCrystalStatus == HitCrystalStatus.PLACED_OBBY) {
            firstCrystalDelay++;
            hitCrystalStatus = HitCrystalStatus.CRYSTALLING;
            return;
        }

        if (hitCrystalStatus == HitCrystalStatus.CRYSTALLING
                && ((MC.crosshairTarget instanceof EntityHitResult hitEntity
                && (hitEntity.getEntity() instanceof EndCrystalEntity || hitEntity.getEntity() instanceof SlimeEntity))
            || (MC.crosshairTarget instanceof BlockHitResult hit
                && BU.isBlock(Blocks.OBSIDIAN, hit.getBlockPos())))) {
            boolean dontPlaceCrystal = crystalPlaceClock != 0;
            boolean dontBreakCrystal = crystalBreakClock != 0;


            if (dontPlaceCrystal)
                crystalPlaceClock--;
            if (dontBreakCrystal)
                crystalBreakClock--;

            if (stopOnKill.get() && isDeadBodyNearby()) {
                return;
            }

            IU.selectItemFromHotbar(Items.END_CRYSTAL);

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
                if (!dontPlaceCrystal && HU.canPlaceCrystalServer(block))
                {
                    crystalPlaceClock = placeInterval.get();
                    ActionResult result = MC.interactionManager.interactBlock(MC.player, Hand.MAIN_HAND, hit);
                    if (result.isAccepted() && result.shouldSwingHand())
                        MC.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}

package org.apache.core.m.ms.m;

import org.apache.core.m.s.BS;
import org.apache.core.u.CU;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class MCP extends M implements PlayerTickListener {
    private final BS includePrefix = BS.Builder.newInstance()
            .setName("Include Prefix")
            .setDescription("whether or not to include the prefix in the ping message")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();
    private boolean isMiddleClicking = false;

    public MCP() {
        super("Mid Click Ping", "Middle Click a player to get their ping.", false, C.MISC);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        eventManager.add(PlayerTickListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        eventManager.remove(PlayerTickListener.class, this);
    }

    @Override
    public void onPlayerTick() {


        HitResult hit = MC.crosshairTarget;
        if (hit.getType() != HitResult.Type.ENTITY)
            return;
        Entity target = ((EntityHitResult) hit).getEntity();
        if (!(target instanceof PlayerEntity))
            return;
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_3) == GLFW.GLFW_PRESS && !isMiddleClicking) {
            isMiddleClicking = true;
            if (includePrefix.get()) {
                CU.plainMessageWithPrefix(target.getEntityName() + "'s ping is " + getPing(target));
            } else {
                CU.sendPlainMessage(target.getEntityName() + "'s ping is " + getPing(target));
            }
        }
        if (GLFW.glfwGetMouseButton(MC.getWindow().getHandle(), GLFW.GLFW_MOUSE_BUTTON_3) == GLFW.GLFW_RELEASE && isMiddleClicking) {
            isMiddleClicking = false;
        }
    }

    public static int getPing(Entity player) {
        if (mc.getNetworkHandler() == null) return 0;

        PlayerListEntry playerListEntry = mc.getNetworkHandler().getPlayerListEntry(player.getUuid());
        if (playerListEntry == null) return 0;
        return playerListEntry.getLatency();
    }
}

package org.apache.core.m.ms.co;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.core.m.C;
import org.apache.core.m.M;

public class WACR extends M implements ClientModInitializer {

    /**
     * Walksy is wishing he had this when DrDonutt banned him. D:
     */

    public static MinecraftClient mc;

    public WACR(String name, String description, boolean enabled, C category) {
        super(name, description, enabled, category);
    }

    @Override
    public void onInitializeClient() {
        mc = MinecraftClient.getInstance();
    }
}

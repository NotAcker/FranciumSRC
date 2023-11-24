package org.apache.core;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        Client.INSTANCE.load();
    }
}

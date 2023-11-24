package org.apache.core.m.ms.c;

import org.apache.core.Client;
import org.apache.core.m.C;
import org.apache.core.m.M;

public class SD extends M {

    public SD() {
        super("Self Destruct", "Self Destruct", false, C.CLIENT);
    }

    public static boolean destruct = false;


    @Override
    public void onEnable() {
        this.onDestruct();
    }

    public void onDestruct() {
        if (Client.MC.currentScreen != null && Client.MC.player != null) {
            Client.MC.player.closeScreen();
        }

        destruct = true;

        for (int k = 0; k < Client.INSTANCE.moduleManager().getModules().size(); k++) {
            M module = Client.INSTANCE.moduleManager().getModules().get(k);
            Client.INSTANCE.moduleManager().getModule(module.getClass()).onDisable();
        }

        Client.INSTANCE.keybindManager().removeAll();
        Client.INSTANCE.moduleManager().removeModules();
        Client.INSTANCE.panic();

        System.gc();
        System.runFinalization();
        System.gc();
        System.runFinalization();
        System.gc();
        System.runFinalization();
        System.gc();
        System.runFinalization();
        System.gc();
        System.runFinalization();
        System.gc();
        System.runFinalization();
    }

}

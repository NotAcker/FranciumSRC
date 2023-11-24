package org.apache.core.m.ms.r;

import org.apache.core.m.C;
import org.apache.core.m.M;

public class NHC extends M {

    public NHC() {
        super("No Hurt Cam", "No Hurt Cam", false, C.RENDER);
    }

    public static boolean doHurtCam = true;

    @Override
    public void onEnable() {
        super.onEnable();
        doHurtCam = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        doHurtCam = true;
    }

}

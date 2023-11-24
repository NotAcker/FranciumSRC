package org.apache.core.m.ms.c;

import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import org.apache.core.m.s.KS;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.k.K;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class CG extends M implements PlayerTickListener {
    private final IS hudColorRed = IS.Builder.newInstance()
            .setName("red")
            .setDescription("hud color red")
            .setModule(this)
            .setValue(255)
            .setMin(0)
            .setMax(255)
            .setAvailability(() -> true)
            .build();
    private final IS hudColorGreen = IS.Builder.newInstance()
            .setName("green")
            .setDescription("hud color green")
            .setModule(this)
            .setValue(90)
            .setMin(0)
            .setMax(255)
            .setAvailability(() -> true)
            .build();
    private final IS hudColorBlue = IS.Builder.newInstance()
            .setName("blue")
            .setDescription("hud color blue")
            .setModule(this)
            .setValue(180)
            .setMin(0)
            .setMax(255)
            .setAvailability(() -> true)
            .build();
    public final BS customFont = BS.Builder.newInstance()
            .setName("Custom Font")
            .setDescription("custom font")
            .setModule(this)
            .setValue(false)
            .setAvailability(() -> true)
            .build();
    private final BS rgbEffect = BS.Builder.newInstance()
            .setName("Breathing")
            .setDescription("Setting to make funny gayming rgb")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();
    public final KS activateKey = new KS.Builder()
            .setName("Keybind")
            .setDescription("the key to activate it")
            .setModule(this)
            .setValue(new K("", GLFW.GLFW_KEY_RIGHT_CONTROL,false,false,null))
            .build();

    public double h = 360;
    public double s = 1;
    public double v = 1;

    public CG(){
        super(" Gui", "modify the gui", true, C.CLIENT);
        eventManager.add(PlayerTickListener.class,this);
    }

    @Override
    public void onEnable() {
        super.onEnable();

    }

    @Override
    public void onDisable() {
        this.setEnabled(true);
    }

    public int getRed() {
        return hudColorRed.get();
    }

    public int getGreen() {
        return hudColorGreen.get();
    }

    public int getBlue() {
        return hudColorBlue.get();
    }

    public double getHudColorBlue() {
        if(rgbEffect.get()) {
            Color rgb = new Color(Color.HSBtoRGB((float) h/360.0f, (float) s, (float) v));
            return rgb.getBlue()/255.0;
        }
        return hudColorBlue.get()/255.0;
    }

    public double getHudColorGreen() {
        if(rgbEffect.get()) {
            int rgb = (Color.HSBtoRGB((float) h/360.0f, (float) s, (float) v));
            return new Color(rgb).getGreen()/255.0;
        }
        return hudColorGreen.get()/255.0;
    }

    public double getHudColorRed() {
        if(rgbEffect.get()) {
            Color rgb = new Color(Color.HSBtoRGB((float) h / 360.0f, (float) s, (float) v));
            return rgb.getRed() / 255.0;
        }
        return hudColorRed.get() / 255.0;
    }

    @Override
    public void onPlayerTick() {

        if (h < 360) {
            h++;
        } else {
            h = 0;
        }

    }
}

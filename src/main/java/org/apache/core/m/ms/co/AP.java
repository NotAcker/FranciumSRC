package org.apache.core.m.ms.co;

import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import org.apache.core.u.IU;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class AP extends M {

    public AP() {
        super("Auto Pot", "auto pot", false, C.COMBAT);
    }
}
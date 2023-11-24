package org.apache.core.m.ms.co;

import net.minecraft.client.MinecraftClient;
import org.apache.core.e.e.ItemUseListener;
import org.apache.core.e.e.PlayerTickListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.IS;
import net.minecraft.block.BedBlock;
import net.minecraft.client.render.DimensionEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BedItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.apache.core.mx.MinecraftClientAccessor;

import static org.apache.core.Client.MC;

public class BM extends M {

    public BM() {
        super(" Macro", "auto bed explode", false, C.COMBAT);
    }

    }
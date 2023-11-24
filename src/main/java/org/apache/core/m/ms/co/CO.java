package org.apache.core.m.ms.co;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.core.m.C;
import org.apache.core.m.M;

public class CO extends M {

    public CO() {
        super("Zoops Optimizer", "haram client crystal remover", true, C.COMBAT);
    }

    public static boolean removeCrystal;

    @Override
    public void onEnable() {
        super.onEnable();
        removeCrystal = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        removeCrystal = false;
    }

}

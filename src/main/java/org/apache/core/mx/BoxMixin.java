package org.apache.core.mx;

import org.apache.core.mi.IB;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Box.class)
public class BoxMixin implements IB {

    @Shadow @Final @Mutable public double maxX;
    @Shadow @Final @Mutable public double maxY;
    @Shadow @Final @Mutable public double maxZ;
    @Shadow @Final @Mutable public double minX;
    @Shadow @Final @Mutable public double minY;
    @Shadow @Final @Mutable public double minZ;


    @Override
    public void expand(float n) {
        this.maxX += n;
        this.maxY += n;
        this.maxZ += n;

        this.minX -= n;
        this.minY -= n;
        this.minZ -= n;
    }
}

package org.apache.core.m.ms.co;

import org.apache.core.m.C;
import org.apache.core.m.M;
import org.apache.core.m.s.BS;
import org.apache.core.m.s.DS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class H extends M {

    private DS hitboxSize = DS.Builder.newInstance()
            .setName("Expand")
            .setDescription("size of hitbox")
            .setModule(this)
            .setValue(0.5)
            .setMin(0.1)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private BS renderHitboxes = BS.Builder.newInstance()
            .setName("Render hitboxes")
            .setDescription("render expanded hitboxes")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    public H() {
        super(" Hitboxes", "expand players hitbox", false, C.COMBAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public float getHitboxSize(Entity entity) {
        if (this.isEnabled() && entity.getType() == EntityType.PLAYER) {
            return hitboxSize.get().floatValue();
        }

        return 0.0f;
    }

    public boolean shouldHitboxRender() {
        return renderHitboxes.get();
    }


}

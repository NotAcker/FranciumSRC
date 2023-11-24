package org.apache.core.m.ms.r;

import org.apache.core.m.s.BS;
import org.apache.core.m.s.DS;
import org.apache.core.e.e.ItemRenderListener;
import org.apache.core.m.C;
import org.apache.core.m.M;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3f;

public class VM extends M implements ItemRenderListener {
    public VM() {
        super("ViewModel", "", false, C.RENDER);
    }



    private final BS mScale = BS.Builder.newInstance()
            .setName("MainHand Scale")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final DS mScaleX = DS.Builder.newInstance()
            .setName("Scale X")
            .setDescription("")
            .setModule(this)
            .setValue(1.0)
            .setMin(-5.0)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private final DS mScaleY = DS.Builder.newInstance()
            .setName("Scale Y")
            .setDescription("")
            .setModule(this)
            .setValue(1.0)
            .setMin(-5.0)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private final DS mScaleZ = DS.Builder.newInstance()
            .setName("Scale Z")
            .setDescription("")
            .setModule(this)
            .setValue(1.0)
            .setMin(-5.0)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private final BS mPos = BS.Builder.newInstance()
            .setName("MainHand Position")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final DS mPosX = DS.Builder.newInstance()
            .setName("Position X")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-10.0)
            .setMax(10.0)
            .setAvailability(() -> true)
            .build();

    private final DS mPosY = DS.Builder.newInstance()
            .setName("Position Y")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-10.0)
            .setMax(10.0)
            .setAvailability(() -> true)
            .build();

    private final DS mPosZ = DS.Builder.newInstance()
            .setName("Position Z")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-10.0)
            .setMax(10.0)
            .setAvailability(() -> true)
            .build();

    private final BS mRot = BS.Builder.newInstance()
            .setName("MainHand Rotation")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final DS mRotX = DS.Builder.newInstance()
            .setName("Rotation X")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-180.0)
            .setMax(180.0)
            .setAvailability(() -> true)
            .build();

    private final DS mRotY = DS.Builder.newInstance()
            .setName("Rotation Y")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-180.0)
            .setMax(180.0)
            .setAvailability(() -> true)
            .build();

    private final DS mRotZ = DS.Builder.newInstance()
            .setName("Rotation Z")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-180.0)
            .setMax(180.0)
            .setAvailability(() -> true)
            .build();

    private final BS oScale = BS.Builder.newInstance()
            .setName("OffHand Scale")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final DS oScaleX = DS.Builder.newInstance()
            .setName("Scale X")
            .setDescription("")
            .setModule(this)
            .setValue(1.0)
            .setMin(-5.0)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private final DS oScaleY = DS.Builder.newInstance()
            .setName("Scale Y")
            .setDescription("")
            .setModule(this)
            .setValue(1.0)
            .setMin(-5.0)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private final DS oScaleZ = DS.Builder.newInstance()
            .setName("Scale Z")
            .setDescription("")
            .setModule(this)
            .setValue(1.0)
            .setMin(-5.0)
            .setMax(5.0)
            .setAvailability(() -> true)
            .build();

    private final BS oPos = BS.Builder.newInstance()
            .setName("Offhand Position")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final DS oPosX = DS.Builder.newInstance()
            .setName("Position X")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-10.0)
            .setMax(10.0)
            .setAvailability(() -> true)
            .build();

    private final DS oPosY = DS.Builder.newInstance()
            .setName("Position Y")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-10.0)
            .setMax(10.0)
            .setAvailability(() -> true)
            .build();

    private final DS oPosZ = DS.Builder.newInstance()
            .setName("Position Z")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-10.0)
            .setMax(10.0)
            .setAvailability(() -> true)
            .build();

    private final BS oRot = BS.Builder.newInstance()
            .setName("OffHand Rotation")
            .setDescription("")
            .setModule(this)
            .setValue(true)
            .setAvailability(() -> true)
            .build();

    private final DS oRotX = DS.Builder.newInstance()
            .setName("Rotation X")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-180.0)
            .setMax(180.0)
            .setAvailability(() -> true)
            .build();

    private final DS oRotY = DS.Builder.newInstance()
            .setName("Rotation Y")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-180.0)
            .setMax(180.0)
            .setAvailability(() -> true)
            .build();

    private final DS oRotZ = DS.Builder.newInstance()
            .setName("Rotation Z")
            .setDescription("")
            .setModule(this)
            .setValue(0.0)
            .setMin(-180.0)
            .setMax(180.0)
            .setAvailability(() -> true)
            .build();





    @Override
    public void onEnable() {
        super.onEnable();

        eventManager.add(ItemRenderListener.class, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        eventManager.remove(ItemRenderListener.class, this);
    }

    @Override
    public void onItemRender(ItemRenderEvent event) {
        MatrixStack matrices = event.getMatrices();

        if (event.getHand() == Hand.MAIN_HAND) {
            if (mPos.get()) {
                matrices.translate(mPosX.get() * 0.1, mPosY.get() * 0.1, mPosZ.get() * 0.1);
            }
            if (mRot.get()) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(mRotX.get().floatValue()));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(mRotY.get().floatValue()));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(mRotZ.get().floatValue()));
            }
            if (mScale.get()) {
                matrices.scale(1 - (1 - mScaleX.get().floatValue()) * 0.1F, 1 - (1 - mScaleY.get().floatValue()) * 0.1F, 1 - (1 - mScaleZ.get().floatValue()) * 0.1F);
            }
        }

        if (event.getHand() == Hand.OFF_HAND) {
            if (oPos.get()) {
                matrices.translate(oPosX.get() * 0.1, oPosY.get() * 0.1, oPosZ.get() * 0.1);
            }
            if (oRot.get()) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(oRotX.get().floatValue()));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(oRotY.get().floatValue()));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(oRotZ.get().floatValue()));
            }
            if (oScale.get()) {
                matrices.scale(1 - (1 - oScaleX.get().floatValue()) * 0.1F, 1 - (1 - oScaleY.get().floatValue()) * 0.1F, 1 - (1 - oScaleZ.get().floatValue()) * 0.1F);
            }
        }

    }
}

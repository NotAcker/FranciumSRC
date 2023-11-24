package org.apache.core.mx;

import org.apache.core.e.EM;
import org.apache.core.m.ms.r.NHC;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import org.apache.core.e.e.GameRenderListener.GameRenderEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(GameRenderer.class)
public class GameRendererMixin
{
//	@Inject(
//			at = {@At(value = "FIELD",
//					target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
//					opcode = Opcodes.GETFIELD,
//					ordinal = 0)},
//			method = {
//					"renderWorld(FJLnet/minecraft/client/util/math/MatrixStack;)V"},
//			locals = LocalCapture.CAPTURE_FAILSOFT)
	private void onRenderWorld(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci, boolean bl, Camera camera, MatrixStack matrixStack, double d, float f, float g, Matrix4f matrix4f, Matrix3f matrix3f) {


        GameRenderEvent event = new GameRenderEvent(matrices, tickDelta);
		EM.fire(event);

	}

	@Inject(at = @At("HEAD"), method = "bobViewWhenHurt", cancellable = true)
	private void bobViewWhenHurt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
		if (!NHC.doHurtCam) {
			ci.cancel();
		}
	}

}

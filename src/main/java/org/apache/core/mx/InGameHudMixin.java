package org.apache.core.mx;

import org.apache.core.e.EM;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.core.e.e.RenderHudListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin
{
	@Inject(method = "render", at = @At("TAIL"))
	private void onRender(MatrixStack matrixStack, float tickDelta, CallbackInfo ci) {

		RenderHudListener.RenderHudEvent event = new RenderHudListener.RenderHudEvent(matrixStack, tickDelta);
		EM.fire(event);

	}
}

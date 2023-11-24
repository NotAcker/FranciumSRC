package org.apache.core.mx;

import net.minecraft.client.Keyboard;
import org.apache.core.e.EM;
import org.apache.core.e.e.KeyPressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin
{
	@Inject(at = @At("HEAD"), method = "onKey(JIIII)V", cancellable = true)
	private void onOnKey(long windowHandle, int keyCode, int scanCode,
	                     int action, int modifiers, CallbackInfo ci)
	{
		KeyPressListener.KeyPressEvent event = new KeyPressListener.KeyPressEvent(keyCode, scanCode, action, modifiers);
		EM.fire(event);
		if (event.isCancelled())
			ci.cancel();
	}
}
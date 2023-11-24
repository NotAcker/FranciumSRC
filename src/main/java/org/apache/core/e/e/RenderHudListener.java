package org.apache.core.e.e;

import org.apache.core.e.E;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface RenderHudListener extends L
{
	void onRenderHud(MatrixStack matrices, float tickDelta);

	class RenderHudEvent extends E<RenderHudListener>
	{

		private final MatrixStack matrices;
		private final float tickDelta;

		public RenderHudEvent(MatrixStack matrices, float tickDelta)
		{
			this.matrices = matrices;
			this.tickDelta = tickDelta;
		}

		@Override
		public void fire(ArrayList<RenderHudListener> listeners)
		{
			listeners.forEach(e -> e.onRenderHud(matrices, tickDelta));
		}

		@Override
		public Class<RenderHudListener> getListenerType()
		{
			return RenderHudListener.class;
		}
	}
}

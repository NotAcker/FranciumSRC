package org.apache.core.e.e;

import org.apache.core.e.E;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.core.e.L;

import java.util.ArrayList;

public interface GameRenderListener extends L
{
	void onGameRender(MatrixStack matrixStack, float tickDelta);

	class GameRenderEvent extends E<GameRenderListener>
	{

		private MatrixStack matrixStack;
		private float tickDelta;

		public GameRenderEvent(MatrixStack matrixStack, float tickDelta)
		{
			this.matrixStack = matrixStack;
			this.tickDelta = tickDelta;
		}

		@Override
		public void fire(ArrayList<GameRenderListener> listeners)
		{
			listeners.forEach(e -> e.onGameRender(matrixStack, tickDelta));
		}

		@Override
		public Class<GameRenderListener> getListenerType()
		{
			return GameRenderListener.class;
		}
	}
}

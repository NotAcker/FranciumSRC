package org.apache.core.m.s;

import org.apache.core.u.RU;
import org.apache.core.Client;
import org.apache.core.k.K;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.apache.core.g.c.BC;
import org.apache.core.g.c.C;
import org.apache.core.g.w.W;
import org.apache.core.m.M;
import org.lwjgl.glfw.GLFW;

import static org.apache.core.Client.MC;

public class KS extends S<K>
{

	private K value;

	private KS(Builder builder)
	{
		super(builder.name, builder.description, builder.module);
		value = builder.value;
	}

	@Override
	public K get()
	{
		return value;
	}

	@Override
	public void set(K value)
	{
		this.value = value;
	}

	@Override
	public C makeComponent(W parent)
	{
		return new BC(parent, 0, 0, 40, getName(), () ->
				MC.setScreen(new Screen(Text.literal(""))
				{

					private final Screen prev = MC.currentScreen;

					@Override
					public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
						if (keyCode == GLFW.GLFW_KEY_ESCAPE)
							return false;

						value.setKey(keyCode);
						MC.setScreen(prev);
						Client.INSTANCE.keybindManager().removeAll();
						Client.INSTANCE.keybindManager().addDefaultKeybinds();

						return false;
					}

					@Override
					public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
					{
						renderBackground(matrices);
						drawCenteredText(matrices, MC.textRenderer, "Please input your key...", width / 2, height / 2, 0xFFFFFF);
					}
				}), () -> RU.getKeyName(value.getKey()));
	}

	public static class Builder
	{
		private String name;
		private String description;
		private M module;
		private K value;

		public static Builder newInstance()
		{
			return new Builder();
		}

		public KS build()
		{
			return new KS(this);
		}

		public Builder setName(String name)
		{
			this.name = name;
			return this;
		}

		public Builder setDescription(String description)
		{
			this.description = description;
			return this;
		}

		public Builder setModule(M module)
		{
			this.module = module;
			return this;
		}

		public Builder setValue(K value)
		{
			this.value = value;
			return this;
		}
	}
}

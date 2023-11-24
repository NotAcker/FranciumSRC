package org.apache.core.m;

import org.apache.core.m.s.KS;
import org.apache.core.m.s.S;
import org.apache.core.Client;
import org.apache.core.e.EM;
import net.minecraft.client.MinecraftClient;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class M implements Serializable
{

	protected final static MinecraftClient mc = MinecraftClient.getInstance();
	protected final static EM eventManager = Client.INSTANCE.eventManager();

	private String name;
	private String description;
	private boolean enabled;
	private final ArrayList<S<?>> settings = new ArrayList<>();
	private final C category;

	public M(String name, String description, boolean enabled, C category)
	{
		this.name = name;
		this.description = description;
		this.enabled = enabled;
		this.category = category;
		if (enabled)
			onEnable();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public C getCategory()
	{
		return category;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		if (this.enabled != enabled)
		{
			this.enabled = enabled;
			if (enabled)
				onEnable();
			else
				onDisable();
		}
	}

	public void toggle()
	{
		setEnabled(!enabled);
	}

	public void onEnable()
	{
		for (S<?> setting : settings)
		{
			if (setting instanceof KS keybindSetting)
			{
				Client.INSTANCE.keybindManager().addKeybind(keybindSetting.get());
			}
		}
	}

	public void onDisable()
	{
		for (S<?> setting : settings)
		{
			if (setting instanceof KS keybindSetting)
			{
				Client.INSTANCE.keybindManager().removeKeybind(keybindSetting.get());
			}
		}
	}

	public void cleanup() {
		for (S<?> setting : settings) {
			setting.setName(null);
			setting.setDescription(null);
		}
		this.name = null;
		this.description = null;
	}

	public void addSetting(S<?> setting)
	{
		settings.add(setting);
	}

	public ArrayList<S<?>> getSettings()
	{
		return (ArrayList<S<?>>) settings.clone();
	}

}

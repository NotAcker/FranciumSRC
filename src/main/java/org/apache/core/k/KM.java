package org.apache.core.k;

import org.apache.core.g.GS;
import org.apache.core.m.ms.c.SD;
import org.apache.core.m.ms.c.CG;
import org.apache.core.Client;
import org.apache.core.e.e.KeyPressListener;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static org.apache.core.Client.MC;

public class KM implements KeyPressListener
{

	private final ArrayList<K> keybinds = new ArrayList<>();

	public KM()
	{
		Client.INSTANCE.eventManager().add(KeyPressListener.class, this);
		addDefaultKeybinds();
	}

	public ArrayList<K> getAllKeybinds()
	{
		return (ArrayList<K>) keybinds.clone();
	}

	public void removeAll()
	{
		keybinds.clear();
	}

	public void addKeybind(K keybind)
	{
		keybinds.add(keybind);
	}

	public void removeKeybind(K keybind)
	{
		keybinds.remove(keybind);
	}

	public void removeKeybind(String name)
	{
		keybinds.removeIf(e -> e.getName().equals(name));
	}

	@Override
	public void onKeyPress(KeyPressListener.KeyPressEvent event)
	{
		for (K keybind : keybinds)
		{
			if (event.getKeyCode() == keybind.getKey())
			{
				if (event.getAction() == GLFW.GLFW_PRESS)
					keybind.press();
				if (event.getAction() == GLFW.GLFW_RELEASE)
					keybind.release();
			}
		}
		//event.cancel();
	}

	public void addDefaultKeybinds()
	{
		addKeybind(new K("", CG.class.cast(Client.INSTANCE.moduleManager().getModule(CG.class)).activateKey.get().getKey(), true, false, () ->
		{
			if (SD.destruct) return;
			if (MC.currentScreen != null) return;
			MC.setScreen(new GS());
		}));
	}
}

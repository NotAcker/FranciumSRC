package org.apache.core.m.ms.m;

import org.apache.core.e.e.SendChatMessageListener;
import org.apache.core.m.C;
import org.apache.core.m.M;

public class AC extends M implements SendChatMessageListener
{
	public AC()
	{
		super("Auto Cringe", "cringe", false, C.MISC);
	}

	@Override
	public void onEnable()
	{
		super.onEnable();
		eventManager.add(SendChatMessageListener.class, this);
	}

	@Override
	public void onDisable()
	{
		super.onDisable();
		eventManager.remove(SendChatMessageListener.class, this);
	}

	@Override
	public void sendChatMessage(SendChatMessageEvent event)
	{
		char[] chars = event.getMessage().toCharArray();
		boolean bl = false;
		for (int i = 0; i < chars.length; i++)
		{
			if (bl)
				chars[i] = Character.toUpperCase(chars[i]);
			else
				chars[i] = Character.toLowerCase(chars[i]);
			bl = !bl;
		}
		event.setMessage(new String(chars));
	}
}

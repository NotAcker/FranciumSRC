package org.apache.core.u;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;

import static org.apache.core.Client.MC;

public enum CU
{
	;
	private static String prefix = "§f[§2NameTag Ping§f] ";

	public static void cleanString() {
		prefix = null;
	}

	public static void log(String message)
	{
		LogManager.getLogger().info("[ConfigManager] {}", message.replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
	}
	public static void info(String message)
	{
		String string = prefix + "Info: " + message;
		sendPlainMessage(string);
	}
	public static void error(String message)
	{
		String string = prefix + "§4Error: §f" + message;
		sendPlainMessage(string);
	}
	public static void sendPlainMessage(String message)
	{
		InGameHud hud = MC.inGameHud;
		if (hud != null)
			hud.getChatHud().addMessage(Text.literal(message));
	}

	public static void plainMessageWithPrefix(String message) {
		String string = prefix + message;
		sendPlainMessage(string);
	}
}

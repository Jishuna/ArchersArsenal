package me.jishuna.archersarsenal.api;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public class Util {
	public static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder().character('&')
			.hexCharacter('#').hexColors().useUnusualXRepeatedCharacterHexFormat().build();

	public static String colorString(String string) {
		return ChatColor.translateAlternateColorCodes('&',
				SERIALIZER.serialize(MiniMessage.miniMessage().parse(string)));
	}
}
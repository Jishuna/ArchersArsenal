package me.jishuna.archersarsenal.api;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.archersarsenal.ArchersArsenal;

public enum PluginKeys {
	ARROW_TYPE("arrow_type");

	private final NamespacedKey key;

	private PluginKeys(String name) {
		this.key = new NamespacedKey(JavaPlugin.getPlugin(ArchersArsenal.class), name);
	}

	public NamespacedKey getKey() {
		return this.key;
	}

}

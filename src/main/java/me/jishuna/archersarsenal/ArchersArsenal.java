package me.jishuna.archersarsenal;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.archersarsenal.api.arrows.CustomArrowManager;
import me.jishuna.archersarsenal.api.arrows.CustomArrowRegistry;
import me.jishuna.archersarsenal.commands.ArchersArsenalCommandHandler;
import me.jishuna.archersarsenal.listeners.ArrowListeners;
import me.jishuna.archersarsenal.listeners.FletchingTableListener;
import me.jishuna.commonlib.inventory.CustomInventoryManager;
import me.jishuna.commonlib.language.MessageConfig;
import me.jishuna.commonlib.utils.FileUtils;

public class ArchersArsenal extends JavaPlugin {
	
	private CustomArrowRegistry arrowRegistry;
	private CustomArrowManager arrowManager;
	private CustomInventoryManager inventoryManager;
	private MessageConfig messageConfig;

	@Override
	public void onEnable() {
		this.loadConfiguration();
		
		this.arrowRegistry = new CustomArrowRegistry(this);
		this.arrowManager = new CustomArrowManager();
		this.inventoryManager = new CustomInventoryManager();
		
		this.arrowManager.runTaskTimer(this, 0, 1);
		
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ArrowListeners(this.arrowManager, this.arrowRegistry), this);
		pluginManager.registerEvents(new FletchingTableListener(this), this);
		
		pluginManager.registerEvents(this.inventoryManager, this);
		
		getCommand("archersarsenal").setExecutor(new ArchersArsenalCommandHandler(this));
	}
	
	public void loadConfiguration() {
		if (!this.getDataFolder().exists())
			this.getDataFolder().mkdirs();

		FileUtils.loadResourceFile(this, "messages.yml")
				.ifPresent(file -> this.messageConfig = new MessageConfig(file));
	}

	public CustomArrowRegistry getArrowRegistry() {
		return arrowRegistry;
	}

	public CustomArrowManager getArrowManager() {
		return arrowManager;
	}

	public CustomInventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public MessageConfig getMessageConfig() {
		return messageConfig;
	}
}

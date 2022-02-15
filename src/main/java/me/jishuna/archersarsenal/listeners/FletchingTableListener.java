package me.jishuna.archersarsenal.listeners;

import org.bukkit.event.Event.Result;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.inventory.FletchingInventory;

public class FletchingTableListener implements Listener {

	private final ArchersArsenal plugin;

	public FletchingTableListener(ArchersArsenal plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.useInteractedBlock() == Result.DENY || event.getAction() != Action.RIGHT_CLICK_BLOCK
				|| event.getClickedBlock().getType() != Material.FLETCHING_TABLE) {
			return;
		}
		plugin.getInventoryManager().openGui(event.getPlayer(), new FletchingInventory(this.plugin));
		event.setUseItemInHand(Result.DENY);
	}

}

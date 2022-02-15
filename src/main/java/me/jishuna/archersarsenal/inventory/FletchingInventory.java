package me.jishuna.archersarsenal.inventory;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.commonlib.inventory.CustomInventory;
import me.jishuna.commonlib.items.ItemBuilder;

public class FletchingInventory extends CustomInventory {

	private final ArchersArsenal plugin;

	public FletchingInventory(ArchersArsenal plugin) {
		super(null, 27, plugin.getMessageConfig().getString("fletching-inventory-name"));
		this.plugin = plugin;

		ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).withName(" ").build();
		for (int i = 0; i < 27; i++) {
			if (i == 10 || i == 12 || i == 16) {
				continue;
			}

			setItem(i, filler);
		}
	}

	@Override
	public void consumeClickEvent(InventoryClickEvent event) {
		Inventory inventory = event.getView().getTopInventory();

		int slot = event.getRawSlot();

		if (slot != 10 && slot != 12 && slot != 16 && slot < 27) {
			event.setCancelled(true);
			return;
		}

		if (slot >= 27 && inventory.firstEmpty() == 16 && event.isShiftClick()) {
			event.setCancelled(true);
			return;
		}

		if (event.getClick() == ClickType.DOUBLE_CLICK && event.getCurrentItem() != null
				&& event.getCurrentItem().isSimilar(inventory.getItem(16))) {
			event.setCancelled(true);
			return;
		}

		if (slot == 16) {
			handleResult(event, inventory);
		}

		updateInventory(inventory);
	}

	@Override
	public void consumeCloseEvent(InventoryCloseEvent event) {
		List<HumanEntity> viewers = event.getViewers();
		if (viewers.isEmpty())
			return;

		Inventory inventory = event.getView().getTopInventory();
		Player player = (Player) viewers.get(0);

		handleItemOnClose(inventory.getItem(10), player);
		handleItemOnClose(inventory.getItem(12), player);
	}

	private void handleItemOnClose(ItemStack item, Player player) {
		if (item == null)
			return;

		Map<Integer, ItemStack> failed = player.getInventory().addItem(item);

		failed.forEach((index, itemstack) -> player.getWorld().dropItem(player.getLocation(), itemstack));
	}

	private void handleResult(InventoryClickEvent event, Inventory inventory) {
		if (event.getCursor() != null && !event.getCursor().getType().isAir()) {
			event.setCancelled(true);
			return;
		}
		ItemStack result = event.getCurrentItem();
		if (result == null) {
			return;
		}

		this.plugin.getArrowRegistry().getCustomArrow(result).ifPresent(customArrow -> {
			ItemStack arrows = inventory.getItem(10);
			ItemStack catalyst = inventory.getItem(12);

			if (arrows == null || catalyst == null || arrows.getType() != Material.ARROW) {
				inventory.setItem(16, null);
				event.setCancelled(true);
				return;
			}

			arrows.setAmount(arrows.getAmount() - customArrow.getCraftingCount());
			catalyst.setAmount(catalyst.getAmount() - 1);
		});
	}

	private void updateInventory(Inventory inventory) {
		Bukkit.getScheduler().runTask(this.plugin, () -> {
			ItemStack arrows = inventory.getItem(10);
			ItemStack catalyst = inventory.getItem(12);

			if (arrows == null || catalyst == null || arrows.getType() != Material.ARROW) {
				inventory.setItem(16, null);
				return;
			}

			this.plugin.getArrowRegistry().getByCatalyst(catalyst.getType()).ifPresent(customArrow -> {
				int arrowCount = customArrow.getCraftingCount();

				if (arrowCount > arrows.getAmount()) {
					inventory.setItem(16, null);
					return;
				}

				ItemStack result = customArrow.getItem();
				result.setAmount(arrowCount);
				inventory.setItem(16, result);
			});
		});
	}
}

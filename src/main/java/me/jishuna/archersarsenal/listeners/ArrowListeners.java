package me.jishuna.archersarsenal.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.archersarsenal.api.PluginKeys;
import me.jishuna.archersarsenal.api.arrows.CustomArrowManager;
import me.jishuna.archersarsenal.api.arrows.CustomArrowRegistry;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.archersarsenal.api.arrows.ShootEffectArrow;

public class ArrowListeners implements Listener {

	private final CustomArrowRegistry registry;
	private final CustomArrowManager manager;

	public ArrowListeners(CustomArrowManager manager, CustomArrowRegistry registry) {
		this.manager = manager;
		this.registry = registry;
	}

	@EventHandler
	public void onShoot(EntityShootBowEvent event) {
		if (!(event.getProjectile() instanceof Arrow arrow)) {
			return;
		}
		ItemStack arrowItem = event.getConsumable();

		this.registry.getCustomArrow(arrowItem).ifPresent(customArrow -> {
			arrow.getPersistentDataContainer().set(PluginKeys.ARROW_TYPE.getKey(), PersistentDataType.STRING,
					customArrow.getName());
			this.manager.addTrackedArrow(customArrow.createArrow(arrow));

			if (customArrow instanceof ShootEffectArrow shootArrow) {
				shootArrow.onShoot(event, arrow);
			}
		});
	}

	@EventHandler
	public void onHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow arrow)) {
			return;
		}

		this.registry.getCustomArrow(arrow).ifPresent(customArrow -> {
			if (customArrow instanceof HitEffectArrow hitArrow) {
				hitArrow.onHit(event, arrow);
			}
		});

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.registry.getCustomArrows().forEach(arrow -> event.getPlayer().getInventory().addItem(arrow.getItem()));
	}
}

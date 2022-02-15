package me.jishuna.archersarsenal.api.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;

public interface ShootEffectArrow {

	public void onShoot(EntityShootBowEvent event, Arrow arrow);
}

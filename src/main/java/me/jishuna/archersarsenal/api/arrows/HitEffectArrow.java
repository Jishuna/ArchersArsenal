package me.jishuna.archersarsenal.api.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;

public interface HitEffectArrow {

	public void onHit(ProjectileHitEvent event, Arrow arrow);
}

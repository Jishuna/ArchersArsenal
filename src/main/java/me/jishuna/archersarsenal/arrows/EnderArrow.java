package me.jishuna.archersarsenal.arrows;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("ender_arrow")
public class EnderArrow extends CustomArrow implements HitEffectArrow {

	public EnderArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	@Override
	public void onHit(ProjectileHitEvent event, Arrow arrow) {
		if (arrow.getShooter() instanceof Entity entity) {
			Location location = arrow.getLocation();
			arrow.remove();

			location.setPitch(entity.getLocation().getPitch());
			location.setYaw(entity.getLocation().getYaw());

			entity.teleport(location);
			entity.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
		}
	}

}

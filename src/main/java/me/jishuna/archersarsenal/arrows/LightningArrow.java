package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("lightning_arrow")
public class LightningArrow extends CustomArrow implements HitEffectArrow {

	public LightningArrow(ArchersArsenal plugin, String name) {
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
		arrow.getWorld().strikeLightning(arrow.getLocation());
		arrow.remove();
	}

}

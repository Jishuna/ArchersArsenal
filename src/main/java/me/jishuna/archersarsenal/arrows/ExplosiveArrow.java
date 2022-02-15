package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("explosive_arrow")
public class ExplosiveArrow extends CustomArrow implements HitEffectArrow {
	private float explosionPower;

	public ExplosiveArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.explosionPower = (float) config.getDouble("explosion-power");
		});
	}

	@Override
	public void onHit(ProjectileHitEvent event, Arrow arrow) {
		arrow.getWorld().createExplosion(arrow.getLocation(), this.explosionPower, false, false);
		arrow.remove();
	}

}

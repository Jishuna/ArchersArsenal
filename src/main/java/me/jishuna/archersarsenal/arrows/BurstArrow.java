package me.jishuna.archersarsenal.arrows;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("burst_arrow")
public class BurstArrow extends CustomArrow implements HitEffectArrow {

	private int count;

	public BurstArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.count = config.getInt("burst-amount");
		});
	}

	@Override
	public void onHit(ProjectileHitEvent event, Arrow arrow) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < this.count; i++) {
			double x = (random.nextDouble() - 0.5) / 4;
			double z = (random.nextDouble() - 0.5) / 4;

			Arrow newArrow = arrow.getWorld().spawn(arrow.getLocation(), Arrow.class);

			newArrow.setVelocity(new Vector(x, 1, z));
			newArrow.setDamage(arrow.getDamage());
			newArrow.setCritical(arrow.isCritical());
			newArrow.setFireTicks(arrow.getFireTicks());

			if (arrow.getColor() != null) {
				newArrow.setColor(arrow.getColor());
			}
		}

		arrow.remove();
	}

}

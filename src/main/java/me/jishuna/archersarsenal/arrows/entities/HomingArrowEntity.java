package me.jishuna.archersarsenal.arrows.entities;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import me.jishuna.archersarsenal.api.arrows.CustomArrowEntity;
import me.jishuna.archersarsenal.arrows.HomingArrow;

public class HomingArrowEntity extends CustomArrowEntity<HomingArrow> {

	private LivingEntity target;

	public HomingArrowEntity(Arrow arrow, HomingArrow arrowType) {
		super(arrow, arrowType);
	}

	@Override
	public void tick() {
		super.tick();

		Arrow arrow = getArrow();
		if (target == null || !target.isValid()) {
			Entity shooter = null;
			if (arrow.getShooter() instanceof Entity) {
				shooter = (Entity) arrow.getShooter();
			}

			double radius = getArrowType().getRadius();
			for (Entity nearby : arrow.getNearbyEntities(radius, radius, radius)) {
				if (nearby == shooter || !(nearby instanceof LivingEntity living))
					continue;
				target = living;
				break;
			}
		}

		if (target != null && target.isValid()) {
			arrow.setVelocity(target.getEyeLocation().toVector().subtract(getLocation().toVector()).normalize());
		}
	}

}

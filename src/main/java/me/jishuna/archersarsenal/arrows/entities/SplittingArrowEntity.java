package me.jishuna.archersarsenal.arrows.entities;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;

import me.jishuna.archersarsenal.api.arrows.CustomArrowEntity;
import me.jishuna.archersarsenal.arrows.SplittingArrow;

public class SplittingArrowEntity extends CustomArrowEntity<SplittingArrow> {

	private boolean split;

	public SplittingArrowEntity(Arrow arrow, SplittingArrow arrowType) {
		super(arrow, arrowType);
	}

	@Override
	public void tick() {
		super.tick();

		Arrow arrow = getArrow();
		if (arrow.getTicksLived() < 3 || this.split)
			return;

		this.split = true;

		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < this.getArrowType().getCount(); i++) {
			double x = (random.nextDouble() - 0.5) / 4;
			double y = (random.nextDouble() - 0.5) / 4;
			double z = (random.nextDouble() - 0.5) / 4;

			Arrow newArrow = arrow.getWorld().spawn(arrow.getLocation(), Arrow.class);

			newArrow.setVelocity(arrow.getVelocity().add(new Vector(x, y, z)));
			newArrow.setDamage(arrow.getDamage());
			newArrow.setCritical(arrow.isCritical());
			newArrow.setFireTicks(arrow.getFireTicks());
			
			if (arrow.getColor() != null) {
				newArrow.setColor(arrow.getColor());
			}
		}
	}

}

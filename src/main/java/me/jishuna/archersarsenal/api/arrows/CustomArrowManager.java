package me.jishuna.archersarsenal.api.arrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomArrowManager extends BukkitRunnable {

	private List<CustomArrowEntity<?>> arrows = new ArrayList<>();

	public void addTrackedArrow(CustomArrowEntity<?> arrow) {
		this.arrows.add(arrow);
	}

	@Override
	public void run() {
		Iterator<CustomArrowEntity<?>> iterator = this.arrows.iterator();

		while (iterator.hasNext()) {
			CustomArrowEntity<?> arrowEntity = iterator.next();
			Arrow arrow = arrowEntity.getArrow();

			if (arrow == null || arrow.isInBlock() || !arrow.isValid()) {
				iterator.remove();
				return;
			}

			arrowEntity.tick();
		}
	}

}

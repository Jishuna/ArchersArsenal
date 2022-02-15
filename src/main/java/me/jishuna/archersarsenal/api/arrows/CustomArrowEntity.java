package me.jishuna.archersarsenal.api.arrows;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Arrow;

public class CustomArrowEntity<T extends CustomArrow> {

	private final Arrow arrow;
	private final T arrowType;

	public CustomArrowEntity(Arrow arrow, T arrowType) {
		this.arrow = arrow;
		this.arrowType = arrowType;
	}
	
	public void tick() {
		this.arrowType.tick(this);
	}

	public World getWorld() {
		return this.arrow.getWorld();
	}

	public Location getLocation() {
		return this.arrow.getLocation();
	}

	public Arrow getArrow() {
		return this.arrow;
	}

	public T getArrowType() {
		return this.arrowType;
	}

}

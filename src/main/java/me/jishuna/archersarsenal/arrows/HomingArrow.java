package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrowEntity;
import me.jishuna.archersarsenal.arrows.entities.HomingArrowEntity;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("homing_arrow")
public class HomingArrow extends CustomArrow {
	private double radius;

	public HomingArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
			
			this.radius = config.getDouble("homing-radius", 2.5);
		});
	}

	@Override
	public CustomArrowEntity<?> createArrow(Arrow arrow) {
		return new HomingArrowEntity(arrow, this);
	}

	public double getRadius() {
		return radius;
	}

}

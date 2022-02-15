package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrowEntity;
import me.jishuna.archersarsenal.arrows.entities.SplittingArrowEntity;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("splitting_arrow")
public class SplittingArrow extends CustomArrow {

	private int count;

	public SplittingArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);

			this.count = config.getInt("split-amount");
		});
	}
	
	@Override
	public CustomArrowEntity<?> createArrow(Arrow arrow) {
		return new SplittingArrowEntity(arrow, this);
	}

	public int getCount() {
		return this.count;
	}
}
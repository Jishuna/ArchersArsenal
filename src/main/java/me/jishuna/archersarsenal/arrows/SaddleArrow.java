package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.ShootEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("saddled_arrow")
public class SaddleArrow extends CustomArrow implements ShootEffectArrow {

	public SaddleArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	@Override
	public void onShoot(EntityShootBowEvent event, Arrow arrow) {
		arrow.addPassenger(event.getEntity());
	}

}

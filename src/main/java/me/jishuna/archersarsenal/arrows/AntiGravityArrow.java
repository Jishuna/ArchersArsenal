package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityShootBowEvent;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrowEntity;
import me.jishuna.archersarsenal.api.arrows.ShootEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("anti_gravity_arrow")
public class AntiGravityArrow extends CustomArrow implements ShootEffectArrow {

	public AntiGravityArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	@Override
	public void tick(CustomArrowEntity<?> arrowEntity) {
		if (arrowEntity.getArrow().getVelocity().length() < 0.05) {
			arrowEntity.getArrow().remove();
		}
	}

	@Override
	public void onShoot(EntityShootBowEvent event, Arrow arrow) {
		arrow.setGravity(false);
	}
}
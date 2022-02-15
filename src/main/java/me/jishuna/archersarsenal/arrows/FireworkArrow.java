package me.jishuna.archersarsenal.arrows;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("firework_arrow")
public class FireworkArrow extends CustomArrow implements HitEffectArrow {

	public FireworkArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			loadDefaults(config, true);
		});
	}

	@Override
	public void onHit(ProjectileHitEvent event, Arrow arrow) {
		Firework firework = event.getEntity().getWorld().spawn(arrow.getLocation(), Firework.class);
		Random random = ThreadLocalRandom.current();

		FireworkMeta meta = firework.getFireworkMeta();

		FireworkEffect.Builder builder = FireworkEffect.builder().flicker(random.nextBoolean())
				.trail(random.nextBoolean()).with(Type.BALL).withColor(getRandomColor(random));

		if (random.nextBoolean()) {
			builder.withFade(getRandomColor(random));
		}
		meta.addEffect(builder.build());

		firework.setFireworkMeta(meta);
		firework.detonate();
	}

	private Color getRandomColor(Random random) {
		return Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}

}

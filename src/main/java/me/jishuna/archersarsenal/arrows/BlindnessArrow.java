package me.jishuna.archersarsenal.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("blindness_arrow")
public class BlindnessArrow extends CustomArrow implements HitEffectArrow {

	private int ticks;

	public BlindnessArrow(ArchersArsenal plugin, String name) {
		super(plugin, name);
	}

	@Override
	public void reload() {
		FileUtils.loadResource(getPlugin(), CustomArrow.FOLDER_PATH + this.getName() + ".yml").ifPresent(config -> {
			this.ticks = config.getInt("blindness-ticks", 200);

			loadDefaults(config, true);
		});
	}

	@Override
	public ItemStack getItem() {
		ItemStack item = super.getItem();

		PotionMeta meta = (PotionMeta) item.getItemMeta();
		meta.addCustomEffect(new PotionEffect(PotionEffectType.BLINDNESS, this.ticks, 0), true);

		item.setItemMeta(meta);
		return item;
	}

	@Override
	public void onHit(ProjectileHitEvent event, Arrow arrow) {
		if (event.getHitEntity() instanceof Mob entity) {
			entity.setTarget(null);
		}
	}
}

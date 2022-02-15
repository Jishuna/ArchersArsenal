package me.jishuna.archersarsenal.api.arrows;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.PluginKeys;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.commonlib.utils.ReflectionUtils;

public class CustomArrowRegistry {
	private static final Class<?> TYPE_CLASS = CustomArrow.class;
	private static final String PACKAGE = "me.jishuna.archersarsenal.arrows";

	private final Map<String, CustomArrow> arrows = new HashMap<>();
	private final Map<Material, CustomArrow> catalystMap = new HashMap<>();
	private final ArchersArsenal plugin;

	public CustomArrowRegistry(ArchersArsenal plugin) {
		this.plugin = plugin;

		registerArrows(PACKAGE);
	}

	@SuppressWarnings("unchecked")
	public void registerArrows(String packageName) {
		for (Class<?> clazz : ReflectionUtils.getAllClassesInSubpackages(packageName,
				this.getClass().getClassLoader())) {
			if (!TYPE_CLASS.isAssignableFrom(clazz))
				continue;

			for (RegisterArrow annotation : clazz.getAnnotationsByType(RegisterArrow.class)) {
				try {
					CustomArrow arrow = ((Class<? extends CustomArrow>) clazz)
							.getDeclaredConstructor(ArchersArsenal.class, String.class)
							.newInstance((Object) this.plugin, (Object) annotation.value());

					arrow.reload();
					registerArrow(arrow);
				} catch (ReflectiveOperationException | IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void registerArrow(CustomArrow arrow) {
		this.arrows.put(arrow.getName(), arrow);

		if (arrow.getCatalyst() != null) {
			this.catalystMap.put(arrow.getCatalyst(), arrow);
		}
	}

	public Optional<CustomArrow> getCustomArrow(Arrow arrow) {
		if (arrow == null) {
			return Optional.empty();
		}

		PersistentDataContainer container = arrow.getPersistentDataContainer();

		if (container.has(PluginKeys.ARROW_TYPE.getKey(), PersistentDataType.STRING)) {
			return getCustomArrow(container.get(PluginKeys.ARROW_TYPE.getKey(), PersistentDataType.STRING));
		}
		return Optional.empty();
	}

	public Optional<CustomArrow> getCustomArrow(ItemStack item) {
		if (item == null || !item.hasItemMeta()) {
			return Optional.empty();
		}

		PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();

		if (container.has(PluginKeys.ARROW_TYPE.getKey(), PersistentDataType.STRING)) {
			return getCustomArrow(container.get(PluginKeys.ARROW_TYPE.getKey(), PersistentDataType.STRING));
		}
		return Optional.empty();
	}

	public Optional<CustomArrow> getCustomArrow(String key) {
		return Optional.ofNullable(this.arrows.get(key));
	}

	public Optional<CustomArrow> getByCatalyst(Material mat) {
		return Optional.ofNullable(this.catalystMap.get(mat));
	}

	public Collection<CustomArrow> getCustomArrows() {
		return this.arrows.values();
	}
}

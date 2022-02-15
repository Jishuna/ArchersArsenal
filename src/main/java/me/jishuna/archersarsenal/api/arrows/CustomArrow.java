package me.jishuna.archersarsenal.api.arrows;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.PluginKeys;
import me.jishuna.archersarsenal.api.Util;

public abstract class CustomArrow {
	public static final String FOLDER_PATH = "Arrows/";

	private final ArchersArsenal plugin;
	private final String name;

	private ItemStack item;
	private boolean enabled;
	private String displayName;

	private Material catalyst;
	private int craftingCount;

	public CustomArrow(ArchersArsenal plugin, String name) {
		this.name = name;
		this.plugin = plugin;
	}

	public abstract void reload();

	public void loadDefaults(ConfigurationSection section, boolean def) {
		this.enabled = section.getBoolean("enabled", def);
		this.displayName = Util.colorString(section.getString("display-name", name));

		this.item = setupItem(section);

		this.catalyst = Material.matchMaterial(section.getString("crafting.catalyst"));
		this.craftingCount = section.getInt("crafting.arrow-count", 1);
	}

	private ItemStack setupItem(ConfigurationSection section) {
		ItemStack item = new ItemStack(Material.TIPPED_ARROW);
		PotionMeta meta = (PotionMeta) item.getItemMeta();

		Color color = parseColor(section.getString("arrow-color"));
		meta.setColor(color);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

		meta.setDisplayName(this.displayName);
		meta.setLore(section.getStringList("lore").stream().map(Util::colorString).toList());
		meta.getPersistentDataContainer().set(PluginKeys.ARROW_TYPE.getKey(), PersistentDataType.STRING, this.name);

		item.setItemMeta(meta);
		return item;
	}

	private Color parseColor(String string) {
		String[] raw = string.split(",");

		int red = NumberUtils.toInt(raw[0]);
		int green = NumberUtils.toInt(raw[1]);
		int blue = NumberUtils.toInt(raw[2]);

		return Color.fromRGB(red, green, blue);
	}

	public CustomArrowEntity<?> createArrow(Arrow arrow) {
		return new CustomArrowEntity<>(arrow, this);
	}

	public void tick(CustomArrowEntity<?> arrowEntity) {
	}

	public String getName() {
		return this.name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public ArchersArsenal getPlugin() {
		return plugin;
	}

	public ItemStack getItem() {
		return item.clone();
	}

	public Material getCatalyst() {
		return catalyst;
	}

	public int getCraftingCount() {
		return craftingCount;
	}

	public boolean isEnabled() {
		return enabled;
	}

}

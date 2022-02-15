package me.jishuna.archersarsenal.arrows;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.RegisterArrow;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.archersarsenal.api.arrows.HitEffectArrow;
import me.jishuna.commonlib.utils.FileUtils;

@RegisterArrow("torch_arrow")
public class TorchArrow extends CustomArrow implements HitEffectArrow {

	public TorchArrow(ArchersArsenal plugin, String name) {
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
		BlockFace face = event.getHitBlockFace();
		Block block = event.getHitBlock();

		if (block == null || face == null || face == BlockFace.DOWN) {
			return;
		}

		if (face == BlockFace.UP) {
			placeTorch(block.getRelative(face), block, Material.TORCH.createBlockData(), arrow);
		} else {
			Block newBlock = block.getRelative(face);

			Directional data = (Directional) Material.WALL_TORCH.createBlockData();
			data.setFacing(face);

			placeTorch(newBlock, block, data, arrow);
		}
		arrow.remove();
	}

	private void placeTorch(Block block, Block hitBlock, BlockData data, Arrow arrow) {
		if (!block.canPlace(data))
			return;

		if (arrow.getShooter() instanceof Player player) {
			BlockPlaceEvent event = new BlockPlaceEvent(block, block.getState(), hitBlock,
					new ItemStack(Material.TORCH), player, true, EquipmentSlot.HAND);
			Bukkit.getPluginManager().callEvent(event);
			
			if (event.isCancelled())
				return;
		}
		block.setBlockData(data);
	}
}

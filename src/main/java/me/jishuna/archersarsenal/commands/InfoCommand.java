package me.jishuna.archersarsenal.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.commonlib.commands.SimpleCommandHandler;
import net.md_5.bungee.api.ChatColor;

public class InfoCommand extends SimpleCommandHandler {

	private final ArchersArsenal plugin;

	public InfoCommand(ArchersArsenal plugin) {
		super("archersarsenal.command.info");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(this.plugin.getMessageConfig().getString("no-permission"));
			return true;
		}
		PluginDescriptionFile descriptionFile = this.plugin.getDescription();
		sender.sendMessage(ChatColor.GOLD + "=".repeat(40));
		sender.sendMessage(ChatColor.GOLD + descriptionFile.getFullName() + ChatColor.GREEN + " by " + ChatColor.GOLD
				+ String.join(", ", descriptionFile.getAuthors()));
		sender.sendMessage(" ");
		sender.sendMessage(ChatColor.GOLD + "Enabled Arrows: " + ChatColor.GREEN + getEnabledArrows());
		sender.sendMessage(ChatColor.GOLD + "=".repeat(40));

		return true;
	}

	private String getEnabledArrows() {
		int total = plugin.getArrowRegistry().getCustomArrows().size();
		long enabled = plugin.getArrowRegistry().getCustomArrows().stream().filter(CustomArrow::isEnabled).count();
		return enabled + "/" + total;
	}

}

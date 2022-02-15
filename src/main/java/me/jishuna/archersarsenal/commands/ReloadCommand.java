package me.jishuna.archersarsenal.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.commonlib.commands.SimpleCommandHandler;

public class ReloadCommand extends SimpleCommandHandler {

	private final ArchersArsenal plugin;

	public ReloadCommand(ArchersArsenal plugin) {
		super("archersarsenal.command.reload");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(this.plugin.getMessageConfig().getString("no-permission"));
			return true;
		}

		sender.sendMessage(this.plugin.getMessageConfig().getString("reload-messages"));
		this.plugin.getMessageConfig().refresh();

		sender.sendMessage(this.plugin.getMessageConfig().getString("reload-arrows"));
		this.plugin.getArrowRegistry().getCustomArrows().forEach(CustomArrow::reload);
		
		sender.sendMessage(this.plugin.getMessageConfig().getString("reload-complete"));
		return true;
	}

}

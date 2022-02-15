package me.jishuna.archersarsenal.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.archersarsenal.api.arrows.CustomArrow;
import me.jishuna.commonlib.commands.SimpleCommandHandler;

public class GiveArrowCommand extends SimpleCommandHandler {

	private final ArchersArsenal plugin;

	public GiveArrowCommand(ArchersArsenal plugin) {
		super("archersarsenal.command.give");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission(getPermission())) {
			sender.sendMessage(this.plugin.getMessageConfig().getString("no-permission"));
			return true;
		}

		if (args.length < 2) {
			sendUsage(sender, "none");
			return true;
		}

		String name = args[0].toLowerCase();
		Optional<CustomArrow> arrowOptional = plugin.getArrowRegistry().getCustomArrow(name);

		if (arrowOptional.isEmpty()) {
			sendUsage(sender, name);
			return true;
		}

		Player player = null;
		if (args.length >= 3) {
			player = Bukkit.getPlayer(args[1]);
		} else if (sender instanceof Player) {
			player = (Player) sender;
		}

		if (player == null) {
			sender.sendMessage(this.plugin.getMessageConfig().getString("invalid-player"));
			return true;
		}

		player.getInventory().addItem(arrowOptional.get().getItem());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 2) {
			return StringUtil.copyPartialMatches(args[0], getArrowNames(), new ArrayList<>());
		}

		if (args.length == 3) {
			return StringUtil.copyPartialMatches(args[1],
					Bukkit.getOnlinePlayers().stream().map(Player::getName).toList(), new ArrayList<>());
		}
		return Collections.emptyList();
	}

	private void sendUsage(CommandSender sender, String arg) {
		String msg = this.plugin.getMessageConfig().getString("command-usage");

		msg = msg.replace("%arg%", arg);
		msg = msg.replace("%args%", String.join(", ", getArrowNames()));

		sender.sendMessage(msg);
	}

	private List<String> getArrowNames() {
		return this.plugin.getArrowRegistry().getCustomArrows().stream().map(CustomArrow::getName).toList();
	}
}

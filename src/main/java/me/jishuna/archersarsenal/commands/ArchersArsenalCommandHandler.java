package me.jishuna.archersarsenal.commands;

import me.jishuna.archersarsenal.ArchersArsenal;
import me.jishuna.commonlib.commands.ArgumentCommandHandler;
import me.jishuna.commonlib.commands.SimpleCommandHandler;

public class ArchersArsenalCommandHandler extends ArgumentCommandHandler {
	public ArchersArsenalCommandHandler(ArchersArsenal plugin) {
		super(plugin.getMessageConfig(), "archersarsenal.command");

		SimpleCommandHandler info = new InfoCommand(plugin);

		setDefault(info);
		addArgumentExecutor("info", info);
		addArgumentExecutor("reload", new ReloadCommand(plugin));
		addArgumentExecutor("give", new GiveArrowCommand(plugin));
	}
}
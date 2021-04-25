package com.thomasandrasek.hallosim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor
{
	private final Main plugin;
	
	public CommandManager(Main plugin)
	{
		this.plugin = plugin;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (label.equalsIgnoreCase("hello"))
		{
			Player player = (Player) sender;
			
			player.sendMessage("Hello world!");
			
			return true;
		}
		return false;
	}
}

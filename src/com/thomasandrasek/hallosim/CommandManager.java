package com.thomasandrasek.hallosim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager 
{
	public static boolean executeCommand(CommandSender sender, Command cmd, String label, String[] args)
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

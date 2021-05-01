package com.thomasandrasek.hallosim;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

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
		else if (label.equalsIgnoreCase("hs-wand"))
		{
			Player player = (Player) sender;
			
			if (MapWand.giveWand(player.getDisplayName()))
			{
				player.sendMessage("Added wand to inventory.");
			}
			else
			{
				player.sendMessage("Could not add wand to inventory, inventory full.");
			}
			
			return true;
		}
		else if (label.equalsIgnoreCase("hs-set-map"))
		{
			Player player = (Player) sender;
			
			if (MapManager.setMinMaxLocation(player, plugin))
			{
				player.sendMessage("Positions have been set.");
			}
			else
			{
				player.sendMessage("Positions could not be set, please select two locations using the map wand.");
			}
			
			return true;
		}
		
		else if (label.equalsIgnoreCase("hs-spawn-baskets"))
		{
			Player player = (Player) sender;
			
			CandyManager.spawnCandyBaskets();
			
			player.sendMessage("Baskets have been spawned.");
			
			return true;
		}
		else if (label.equalsIgnoreCase("hs-clear-baskets"))
		{
			Player player = (Player) sender;
			
			CandyManager.clearBaskets();
			
			player.sendMessage("Baskets have been cleared.");
			
			return true;
		}
		
		return false;
	}
}

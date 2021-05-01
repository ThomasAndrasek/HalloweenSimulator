package com.thomasandrasek.hallosim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MapManager 
{
	public static void runGame()
	{
		
	}
	
	public static void initializeTrickOrTreaters()
	{
		Player[] players = (Player[]) Bukkit.getOnlinePlayers().toArray();
		
		for (Player player : players)
		{
			new TrickOrTreater(player.getDisplayName());
		}
	}
	
	public static void startGame()
	{
		CandyManager.clearBaskets();
		CandyManager.spawnCandyBaskets();
		initializeTrickOrTreaters();
		runGame();
	}
	
	public static boolean setMinMaxLocation(Player player, Main plugin)
	{
		MapWand wand = MapWand.getWandFromName(player.getDisplayName());
		
		if (wand == null)
		{
			return false;
		}
		
		Location location1 = wand.getLocation1();
		Location location2 = wand.getLocation2();
		
		if (location1 == null || location2 == null)
		{
			return false;
		}
		
		if (!location1.getWorld().equals(location2.getWorld()))
		{
			return false;
		}
		
		double maxX, maxY, maxZ = 0;
		double minX, minY, minZ = 0;
		
		maxX = Math.max(location1.getX(), location2.getX());
		maxY = Math.max(location1.getY(), location2.getY());
		maxZ = Math.max(location1.getZ(), location2.getZ());
		
		minX = Math.min(location1.getX(), location2.getX());
		minY = Math.min(location1.getY(), location2.getY());
		minZ = Math.min(location1.getZ(), location2.getZ());
		
		Location minLocation = new Location(location1.getWorld(), minX, minY, minZ);
		Location maxLocation = new Location(location1.getWorld(), maxX, maxY, maxZ);
		
		FileConfiguration config = plugin.getConfig();
		config.set("Map.min-location", minLocation);
		config.set("Map.max-location", maxLocation);
		plugin.saveConfig();
		
		CandyManager.loadSpawnLocations(plugin);
		
		return true;
	}
}

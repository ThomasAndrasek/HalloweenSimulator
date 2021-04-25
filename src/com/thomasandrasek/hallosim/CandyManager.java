package com.thomasandrasek.hallosim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class CandyManager 
{
	private static Map<String, Material> spawnBlocks = new HashMap<>();
	
	private static ArrayList<Location> spawnLocations = new ArrayList<>();
	
	public static void loadSpawnBlocks(Main plugin)
	{
		FileConfiguration config = plugin.getConfig();
		
		List<String> blockNames = config.getStringList("Map.blocks");
		
		for (String name : blockNames)
		{
			Material material = Material.getMaterial(name.toUpperCase());
			if (material != null)
			{
				spawnBlocks.put(material.name(), material);
			}
			else
			{
				plugin.getLogger().info(name + " is not a valid block!");
			}
		}
	}
	
	public static void loadSpawnLocations(Main plugin)
	{
		spawnLocations.clear();
		
		FileConfiguration config = plugin.getConfig();
		
		Location minLocation = config.getLocation("Map.min-location");
		Location maxLocation = config.getLocation("Map.max-location");
		
		if (minLocation == null || maxLocation == null)
		{
			plugin.getLogger().info("Locations have not been set for the map!");
			return;
		}
		
		for (double x = minLocation.getX(); x < maxLocation.getX(); x++)
		{
			for (double y = minLocation.getY(); y < maxLocation.getY(); y++)
			{
				for (double z = minLocation.getZ(); z < maxLocation.getZ(); z++)
				{
					Location block = new Location(minLocation.getWorld(), x, y, z);
					Location blockAbove = new Location(minLocation.getWorld(), x, y+1, z);
					
					if (spawnBlocks.get(block.getBlock().getType().name()) != null && blockAbove.getBlock().getType().equals(Material.AIR))
					{
						spawnLocations.add(block);
						plugin.getLogger().info("Added location: " + block.toString());
					}
				}
			}
		}
	}
}

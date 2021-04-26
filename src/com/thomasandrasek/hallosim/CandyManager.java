package com.thomasandrasek.hallosim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class CandyManager 
{
	private static Map<String, Material> spawnBlocks = new HashMap<>();
	
	private static ArrayList<Location> spawnLocations = new ArrayList<>();
	
	private static int candyCount = 0;
	
	public static void loadSpawnBlocks(Main plugin)
	{
		plugin.getLogger().info("Loading spawn blocks.");
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
		
		plugin.getLogger().info("Finished loading spawn blocks.");
	}
	
	public static void loadSpawnLocations(Main plugin)
	{
		plugin.getLogger().info("Loading candy spawn positions");
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
					}
				}
			}
		}
		plugin.getLogger().info("Finished loading candy spawn positions.");
	}
	
	public static void loadCandyBasketAmount(Main plugin)
	{
		candyCount = plugin.getConfig().getInt("Map.candy-basket-amount");
		plugin.getLogger().info("Loaded candy basket count.");
	}
	
	public static void spawnCandyBaskets()
	{
		int count = 0;
		
		Random random = new Random();
		
		while (count < candyCount)
		{
			int choice = random.nextInt(spawnLocations.size());
			
			Location spawnLocation = spawnLocations.get(choice);
			
			Location blockAbove = new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY() + 1, spawnLocation.getZ());
			
			if (blockAbove.getBlock().getType().equals(Material.AIR))
			{
				blockAbove.getBlock().setType(Material.IRON_BLOCK);
				
				new CandyBasket(blockAbove);
				
				count++;
			}
		}
	}
	
	public static void clearBaskets()
	{
		CandyBasket.clearActiveBaskets();
		CandyBasket.clearInactiveBaskets();
	}
}

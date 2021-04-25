package com.thomasandrasek.hallosim;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class CandyManager 
{
	private static ArrayList<Material> spawnBlocks = new ArrayList<>();
	
	public static void loadSpawnBlocks(Main plugin)
	{
		FileConfiguration config = plugin.getConfig();
		
		List<String> blockNames = config.getStringList("Map.blocks");
		
		for (String name : blockNames)
		{
			Material material = Material.getMaterial(name.toUpperCase());
			if (material != null)
			{
				spawnBlocks.add(material);
			}
			else
			{
				plugin.getLogger().info(name + " is not a valid block!");
			}
		}
	}
}

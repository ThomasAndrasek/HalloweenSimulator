package com.thomasandrasek.hallosim;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class CandyManager 
{
	private static Map<String, Material> spawnBlocks = new HashMap<>();
	
	private static ArrayList<Location> spawnLocations = new ArrayList<>();
	
	private static ArrayList<ItemStack> candyBasketsData = new ArrayList<>();
	
	private static ArrayList<String> candyBasketStrings = new ArrayList<>();
	
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
	
	public static void loadCandyBasketBlocks(Main plugin)
	{
		plugin.getLogger().info("Loading candy basket textures.");
		
		FileConfiguration config = plugin.getConfig();
		
		List<String> ids = config.getStringList("candy-basket-id");
		
		for (String id : ids)
		{
			candyBasketStrings.add(id);
		}
		
		plugin.getLogger().info("Finished loading candy basket textures.");
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
				blockAbove.getBlock().setType(Material.PLAYER_HEAD);
				
				spawnRandomCandyBasket(blockAbove);
				
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
	
	public static BlockFace getBlockFace(Location location)
	{
		ArrayList<BlockFace> possibleFaces = new ArrayList<>();
		
		Location tempLocation = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
		
		tempLocation.setZ(tempLocation.getZ()-1);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			possibleFaces.add(BlockFace.NORTH);
		}
		tempLocation.setX(tempLocation.getX()+1);
		tempLocation.setZ(tempLocation.getZ()+1);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			possibleFaces.add(BlockFace.EAST);
		}
		tempLocation.setX(tempLocation.getX()-1);
		tempLocation.setZ(tempLocation.getZ()+1);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			possibleFaces.add(BlockFace.SOUTH);
		}
		tempLocation.setX(tempLocation.getX()-1);
		tempLocation.setZ(tempLocation.getZ()-1);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			possibleFaces.add(BlockFace.WEST);
		}
		tempLocation.setX(tempLocation.getX()+1);
		
		tempLocation.setX(tempLocation.getX()+1);
		tempLocation.setZ(tempLocation.getZ()-1);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			if (possibleFaces.contains(BlockFace.NORTH))
			{
				possibleFaces.add(BlockFace.NORTH_NORTH_EAST);
			}
			
			if (possibleFaces.contains(BlockFace.EAST))
			{
				possibleFaces.add(BlockFace.EAST_NORTH_EAST);
			}
			
			if (possibleFaces.contains(BlockFace.NORTH) && possibleFaces.contains(BlockFace.EAST))
			{
				possibleFaces.add(BlockFace.NORTH_EAST);
			}
		}
		
		tempLocation.setZ(tempLocation.getZ()+2);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			if (possibleFaces.contains(BlockFace.EAST))
			{
				possibleFaces.add(BlockFace.EAST_SOUTH_EAST);
			}
			
			if (possibleFaces.contains(BlockFace.SOUTH))
			{
				possibleFaces.add(BlockFace.SOUTH_SOUTH_EAST);
			}
			
			if (possibleFaces.contains(BlockFace.EAST) && possibleFaces.contains(BlockFace.SOUTH))
			{
				possibleFaces.add(BlockFace.SOUTH_EAST);
			}
		}
		
		tempLocation.setX(tempLocation.getX()-2);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			if (possibleFaces.contains(BlockFace.SOUTH))
			{
				possibleFaces.add(BlockFace.SOUTH_SOUTH_WEST);
			}
			
			if (possibleFaces.contains(BlockFace.WEST))
			{
				possibleFaces.add(BlockFace.WEST_SOUTH_WEST);
			}
			
			if (possibleFaces.contains(BlockFace.SOUTH) && possibleFaces.contains(BlockFace.WEST))
			{
				possibleFaces.add(BlockFace.SOUTH_WEST);
			}
		}
		
		tempLocation.setZ(tempLocation.getZ()-2);
		if (tempLocation.getBlock().getType().equals(Material.AIR))
		{
			if (possibleFaces.contains(BlockFace.NORTH))
			{
				possibleFaces.add(BlockFace.NORTH_NORTH_WEST);
			}
			
			if (possibleFaces.contains(BlockFace.WEST))
			{
				possibleFaces.add(BlockFace.WEST_NORTH_WEST);
			}
			
			if (possibleFaces.contains(BlockFace.NORTH) && possibleFaces.contains(BlockFace.WEST))
			{
				possibleFaces.add(BlockFace.NORTH_WEST);
			}
		}
		
		
		if (possibleFaces.size() == 0)
		{
			return BlockFace.NORTH;
		}
		
		Random random = new Random();
		return possibleFaces.get(random.nextInt(possibleFaces.size()));
	}
	
	public static void spawnRandomCandyBasket(Location location)
	{
		Random random = new Random();
		
		final Skull skull = (Skull)location.getBlock().getState();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures",candyBasketStrings.get(random.nextInt(candyBasketStrings.size()))));
        try {
            Field profileField = skull.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skull, profile);
        }catch (NoSuchFieldException | IllegalAccessException e) { e.printStackTrace(); }
        skull.update(); // so that the result can be seen
        
        Rotatable skullRotatable = (Rotatable) location.getBlock().getBlockData();
        skullRotatable.setRotation(getBlockFace(location).getOppositeFace());
        location.getBlock().setBlockData(skullRotatable);
	}
	
	public static ItemStack getCustomSkull(String url) {

        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty()) return head;

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Method mtd = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(skullMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }

        head.setItemMeta(skullMeta);
        return head;
    }
	
	
}

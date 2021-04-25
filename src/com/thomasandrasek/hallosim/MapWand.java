package com.thomasandrasek.hallosim;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MapWand 
{
	private static Map<String, MapWand> wands = new HashMap<String, MapWand>();
	
	private String owner;
	private Location location1;
	private Location location2;
	
	public MapWand(String owner)
	{
		this.owner = owner;
		
		wands.put(owner, this);
	}
	
	public String getOwner()
	{
		String temp = owner.substring(0);
		
		return temp;
	}
	
	public Location getLocation1()
	{
		Location ret = new Location(location1.getWorld(), location1.getX(), location1.getY(), location1.getZ());
		
		return ret;
	}
	
	public Location getLocation2()
	{
		Location ret = new Location(location2.getWorld(), location2.getX(), location2.getY(), location2.getZ());
		
		return ret;
	}
	
	public void setLocation1(Location location)
	{
		this.location1 = location;
	}
	
	public void setLocation2(Location location)
	{
		this.location2 = location;
	}
	
	public static boolean giveWand(String player)
	{
		new MapWand(player);
		
		Player p = Bukkit.getPlayer(player);
		
		if (p.getInventory().firstEmpty() == -1)
		{
			return false;
		}
		
		ItemStack wand = new ItemStack(Material.NETHERITE_HOE);
		ItemMeta meta = wand.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Halloween Simulator Wand"));
		wand.setItemMeta(meta);
		
		p.getInventory().setItem(p.getInventory().firstEmpty(), wand);
		
		return true;
	}
	
	public static MapWand getWandFromName(String player)
	{
		if (wands.get(player) != null)
		{
			return wands.get(player);
		}
		
		return null;
	}
}

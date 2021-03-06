package com.thomasandrasek.hallosim;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CandyBasket 
{
	private static ArrayList<CandyBasket> activeBaskets = new ArrayList<>();
	private static ArrayList<CandyBasket> inactiveBaskets = new ArrayList<>();
	
	private boolean active;
	private Player whoClicked;
	private Location location;
	
	public CandyBasket(Location location)
	{
		this.active = false;
		this.location = location;
		
		activeBaskets.add(this);
	}
	
	public boolean isActive()
	{
		return this.active;
	}
	
	public Player getWhoClicked()
	{
		return this.whoClicked;
	}
	
	public Location getLocation()
	{
		if (this.location == null)
		{
			return null;
		}
		
		Location temp = new Location(this.location.getWorld(), this.location.getX(), this.location.getY(), this.location.getZ());
		
		return temp;
	}
	
	public void setInactive(Player player)
	{
		this.active = false;
		this.whoClicked = player;
		
		activeBaskets.remove(this);
		inactiveBaskets.add(this);
	}
	
	public static CandyBasket getActiveBasketFromLocation(Location location)
	{
		for (CandyBasket basket : activeBaskets)
		{
			if (basket.getLocation().equals(location))
			{
				return basket;
			}
		}
		
		return null;
	}
	
	public static void clearActiveBaskets()
	{
		for (CandyBasket basket : activeBaskets)
		{
			basket.location.getBlock().setType(Material.AIR);
		}
		
		activeBaskets.clear();
	}
	
	public static void clearInactiveBaskets()
	{
		for (CandyBasket basket : inactiveBaskets)
		{
			basket.location.getBlock().setType(Material.AIR);
		}
		
		inactiveBaskets.clear();
	}
}

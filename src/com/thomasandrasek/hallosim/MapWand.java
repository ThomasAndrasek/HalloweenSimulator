package com.thomasandrasek.hallosim;

import org.bukkit.Location;

public class MapWand 
{
	private String owner;
	private Location location1;
	private Location location2;
	
	public MapWand(String owner)
	{
		this.owner = owner;
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
}

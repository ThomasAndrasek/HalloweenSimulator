package com.thomasandrasek.hallosim;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EventManager implements Listener 
{
	private Main plugin;
	
	public EventManager(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK))
		{
			ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
			
			if (itemInHand.getType().equals(Material.NETHERITE_HOE) && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&6Halloween Simulator Wand")))
			{
				MapWand wand = MapWand.getWandFromName(event.getPlayer().getDisplayName());
				
				if (wand == null)
				{
					wand = new MapWand(event.getPlayer().getDisplayName());
				}
				
				wand.setLocation1(event.getClickedBlock().getLocation());
				
				event.getPlayer().sendMessage("Location 1 set");
				
				event.setCancelled(true);
			}
		}
		else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().equals(EquipmentSlot.HAND))
		{
			ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
			
			if (itemInHand.getType().equals(Material.NETHERITE_HOE) && itemInHand.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&6Halloween Simulator Wand")))
			{
				MapWand wand = MapWand.getWandFromName(event.getPlayer().getDisplayName());
				
				if (wand == null)
				{
					wand = new MapWand(event.getPlayer().getDisplayName());
				}
				
				wand.setLocation2(event.getClickedBlock().getLocation());
				
				event.getPlayer().sendMessage("Location 2 set");
				
				event.setCancelled(true);
			}
		}
	}
}

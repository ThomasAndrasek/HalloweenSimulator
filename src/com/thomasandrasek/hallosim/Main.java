package com.thomasandrasek.hallosim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	@Override
	public void onEnable()
	{
		this.getCommand("hello").setExecutor(new CommandManager(this));
		this.getCommand("hs-wand").setExecutor(new CommandManager(this));
		this.getCommand("hs-set-map").setExecutor(new CommandManager(this));
		
		Bukkit.getPluginManager().registerEvents(new EventManager(this), this);
		
		this.getConfig().options().copyDefaults(true);
        saveConfig();
        
        CandyManager.loadSpawnBlocks(this);
        CandyManager.loadSpawnLocations(this);
	}
	
	@Override
	public void onDisable()
	{
		
	}
}

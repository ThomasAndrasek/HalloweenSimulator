package com.thomasandrasek.hallosim;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin 
{
	@Override
	public void onEnable()
	{
		this.getCommand("hello").setExecutor(new CommandManager(this));
	}
	
	@Override
	public void onDisable()
	{
		
	}
}

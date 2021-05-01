package com.thomasandrasek.hallosim;

import java.util.ArrayList;

public class TrickOrTreater 
{
	private String name;
	private int collected;
	
	private static ArrayList<TrickOrTreater> trickOrTreaters = new ArrayList<>();
	
	public TrickOrTreater(String name)
	{
		this.name = name;
		this.collected = 0;
		
		trickOrTreaters.add(this);
	}
	
	public String getName()
	{
		String temp = name.substring(0);
		return temp;
	}
	
	public int getCollectedAmount()
	{
		return collected;
	}
	
	public void incrementCollected()
	{
		this.collected++;
	}
	
	public TrickOrTreater getTrickOrTreaterFromName(String name)
	{
		for (TrickOrTreater treater : trickOrTreaters)
		{
			if (treater.getName().equals(name))
			{
				return treater;
			}
		}
		
		return null;
	}
}

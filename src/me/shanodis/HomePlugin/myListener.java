package me.shanodis.HomePlugin;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class myListener implements Listener {
	Vector<Houses> houses;
	// Constructor
	public myListener(Main plugin, Vector<Houses> houses) {
		this.houses = houses;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Iterator<Houses> iterator = houses.iterator();
		Houses house = new Houses(event.getPlayer());
		boolean add = true;
		while(iterator.hasNext()) {
			if(iterator.next().player == house.player)
				add = false;
		}
		String welcomeMessage = ChatColor.AQUA + ChatColor.BOLD.toString() + 
				"Hi " + event.getPlayer().getName();
		event.getPlayer().sendMessage(welcomeMessage);
		try { house.checkFile(event.getPlayer()); } 
		catch (IOException e) { e.printStackTrace(); }
		if(add) houses.add(house);
	}
	
}

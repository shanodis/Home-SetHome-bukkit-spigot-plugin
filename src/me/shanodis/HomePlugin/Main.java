package me.shanodis.HomePlugin;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	// myPlayerObject
	Vector<Houses> playerHouses;
	
	public Main() {
		playerHouses = new Vector<>();
	}
	
	@Override
	public void onEnable() {
		getLogger().info("Hello World!");
		PluginManager pluginManager = getServer().getPluginManager();
		myListener listener = new myListener(this, playerHouses);
		pluginManager.registerEvents(listener, this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("GoodBye!");
	}
	
	private void setHome(Houses house, Player player, String homeName) throws IOException {
		int errorType = house.setHouseLocation(homeName);
		if(errorType < 0) {
			sendErrMsg(player, errorType);
			return;
		}
		String message = ChatColor.GRAY + "Home set at " + "X: " + player.getLocation().getBlockX() + 
				" Y: " + player.getLocation().getBlockY() + " Z: " + 
				player.getLocation().getBlockZ();
		player.sendMessage(message);
	}
	
	private void home(Houses house, Player player, int index) throws IOException {
		if(index < 1 || index > 3) {
			sendErrMsg(player, -3);
			return;
		}
		
		Node<Location, String> home = house.getHouseLocation(index);
		if(home == null) {
			sendErrMsg(player, -3);
			return;
		}
		player.teleport(home.location);
		String message = ChatColor.AQUA + ChatColor.BOLD.toString() + "Teleported to home: " + home.name;
		player.sendMessage(message);
	}
	
	private boolean sendErrMsg(Player player, int errorType) {
		switch(errorType) {
		case -1:
			player.sendMessage(ChatColor.RED + "Failed to execute command. Your home list is full!");
			break;
		
		case -2:
			player.sendMessage(ChatColor.RED + "Failed to execute command. Home with that name already exists.");
			break;
			
		case -3:
			player.sendMessage(ChatColor.RED + "Failed to execute command. Invaild argument.");
			break;
		}
		return false;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Player player = (Player)sender;
		Houses house = null;
		
		Iterator<Houses> iterator = playerHouses.iterator();
		while(iterator.hasNext()) {
			Houses tmp = iterator.next();
			if(tmp.player == player)
				house = tmp;
		}
	
		if(sender instanceof Player) {
			if(args.length != 1)
				return sendErrMsg(player, -3);
			
			String lowerCmd = cmd.getName().toLowerCase();
			
			switch(lowerCmd) {
			
			case "sethome":
				try { setHome(house, player, args[0]); } 
				catch (IOException e) { e.printStackTrace(); }
				break;
				
			case "home":
				int intArg = Integer.parseInt(args[0]);
				try { home(house, player, intArg); } 
				catch (IOException e) { e.printStackTrace(); }
				break;
				
			default:
				player.sendMessage(ChatColor.RED + "Your command was not recognized! Try again.");
				break;
			}
		}
		
		return true;
	}
}

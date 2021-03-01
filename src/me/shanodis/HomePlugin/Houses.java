package me.shanodis.HomePlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Houses {
	List<Location, String> housesLocations;
	Player player;
	String filePath;
	
	// Constructors
	public Houses() {
		housesLocations = new List<Location, String>();
	}
	
	public Houses(Player player) {
		this.player = player;
		housesLocations = new List<Location, String>();
	}
	
	// Public Methods
	public Node<Location, String> getHouseLocation(int index) throws IOException {
		return housesLocations.searchNode(index);
	}
	
	public String checkFile(Player player) throws IOException {
		this.player = player;
		
		filePath = player.getName() + "Houses.txt";
		File file = new File(filePath);
		if(!file.exists()) {
			file.createNewFile(); // Creating player file with home details
		}
		else {
			Scanner scanner = new Scanner(file);
			int[] data = new int[3];
			while(scanner.hasNext()) {
				for(int i = 0; i < 3; i++)
					data[i] = scanner.nextInt();
				
				String worldName = scanner.next();
				String homeName = scanner.nextLine();
				World world = Bukkit.getWorld(worldName.trim());
				
				housesLocations.addLast(new Location(world, data[0], data[1], data[2]), homeName.trim());
			}
			scanner.close();
		}
		return filePath;
	}
	
	public int setHouseLocation(String homeName) throws IOException {
		if(housesLocations.size() < 3) {
			
			if(!housesLocations.addLast(player.getLocation(), homeName))
				return -2;
			
			FileWriter savingPrintWriter = new FileWriter(filePath, true);
			savingPrintWriter.write(player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + 
				player.getLocation().getBlockZ() + " " + player.getWorld().getName() + " " + homeName + "\n");
			savingPrintWriter.close();
			
			return 0;
		}
		return -1;
	}
}

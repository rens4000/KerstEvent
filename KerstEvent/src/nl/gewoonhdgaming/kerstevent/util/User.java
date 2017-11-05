package nl.gewoonhdgaming.kerstevent.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class User {
	
	private Player p;
	private int score;
	private int deaths;
	private ItemStack[] armor;
	private ItemStack[] inv;
	
	public User(Player p) {
		this.p = p;
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public ItemStack[] getArmor() {
		return armor;
	}
	
	public void setArmor(ItemStack[] a) {
		armor = a;
	}
	
	public ItemStack[] getInventory() {
		return inv;
	}
	
	public void setInventory(ItemStack[] i) {
		inv = i;
	}

}

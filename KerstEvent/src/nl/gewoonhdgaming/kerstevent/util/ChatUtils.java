package nl.gewoonhdgaming.kerstevent.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.gewoonhdgaming.kerstevent.KerstEvent;

public class ChatUtils {
	
	private KerstEvent ke;
	public ChatUtils(KerstEvent ke) {
		this.ke = ke;
	}
	public String PREFIX = ChatColor.GRAY + "[" + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.GRAY + "] " + ChatColor.WHITE;
	public String ERROR = ChatColor.RED + "[KERST" + ChatColor.DARK_RED + "EVENT" + ChatColor.RED + "ERROR] " + ChatColor.DARK_RED;
	
	public void broadcast(String message) {
		for(User u : ke.USERS.values()) {
			Player p = u.getPlayer();
			p.sendMessage(PREFIX + message);
		}
	}

}

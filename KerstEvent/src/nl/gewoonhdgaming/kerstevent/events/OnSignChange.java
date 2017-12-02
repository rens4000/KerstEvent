package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import nl.gewoonhdgaming.kerstevent.KerstEvent;
import nl.gewoonhdgaming.kerstevent.util.ChatUtils;

public class OnSignChange implements Listener {
	
	private ChatUtils cu;
	
	public OnSignChange(KerstEvent ke) {
		cu = new ChatUtils(ke);
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		if(!e.getPlayer().hasPermission("KerstEvent.Admin")) {
			e.getPlayer().sendMessage(cu.ERROR + "Je hebt geen permissie om dat commando uit te voeren!");
			return;
		}
		if(e.getLine(0).equalsIgnoreCase("[snowgenerator]")) {
			e.setLine(0, ChatColor.WHITE + "[" + ChatColor.AQUA + "SnowGenerator" + ChatColor.WHITE + "]");
			e.setLine(1, ChatColor.WHITE + "Klik hier!");
			e.setLine(2, ChatColor.WHITE + "Om sneeuwballen");
			e.setLine(3, ChatColor.WHITE + "te krijgen.");
			e.getPlayer().sendMessage(cu.PREFIX + "Sneeuw generator gemaakt!");
		}
		if(e.getLine(0).equalsIgnoreCase("[kerstEvent]")) {
			e.setLine(0, ChatColor.WHITE + "[" + ChatColor.AQUA + "KerstEvent" + ChatColor.WHITE + "]");
			e.setLine(1, ChatColor.WHITE + "Join!");
			e.getPlayer().sendMessage(cu.PREFIX + "Join sign gemaakt!");
		}
	}

}

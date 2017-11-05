package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import nl.gewoonhdgaming.kerstevent.KerstEvent;
import nl.gewoonhdgaming.kerstevent.util.ChatUtils;

public class OnSignClickEvent implements Listener {

	private KerstEvent ke;
	
	private ChatUtils cu;
	
	public OnSignClickEvent(KerstEvent ke) {
		this.ke = ke;
		cu = new ChatUtils(ke);
	}
	
	@EventHandler
	public void onSignClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.SIGN ||e.getClickedBlock().getType() == Material.SIGN_POST ||e.getClickedBlock().getType() == Material.WALL_SIGN) {
				 Sign sign = (Sign) e.getClickedBlock().getState();
				 if(sign.getLine(0).equalsIgnoreCase(ChatColor.WHITE + "[" + ChatColor.AQUA + "SnowGenerator" + ChatColor.WHITE + "]")) {
					 if(!ke.USERS.containsKey(e.getPlayer().getUniqueId())) {
						 e.getPlayer().sendMessage(cu.ERROR + "Je zit niet in de game!");
						 return;
					 }
					 Player p = e.getPlayer();
					 if(p.getInventory().contains(new ItemStack(Material.SNOW_BALL, 16))) {
						 p.sendMessage(cu.ERROR + "Je hebt nog sneeuwballen!");
						 return;
					 }
					 p.getInventory().clear();
					 p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
					 p.sendMessage(cu.PREFIX + "Je hebt 16 sneeuwballen gekregen!");
				 }
			}
		}
	}
	
}

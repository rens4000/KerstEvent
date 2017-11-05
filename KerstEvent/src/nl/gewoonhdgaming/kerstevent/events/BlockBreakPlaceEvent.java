package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import nl.gewoonhdgaming.kerstevent.KerstEvent;

public class BlockBreakPlaceEvent implements Listener {
	
	private KerstEvent ke;
	
	public BlockBreakPlaceEvent(KerstEvent ke) {
		this.ke = ke;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(ke.USERS.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(ke.USERS.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

}

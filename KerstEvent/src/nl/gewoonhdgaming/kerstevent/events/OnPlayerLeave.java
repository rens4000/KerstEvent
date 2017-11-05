package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import nl.gewoonhdgaming.kerstevent.KerstEvent;
import nl.gewoonhdgaming.kerstevent.util.GameUtils;

public class OnPlayerLeave implements Listener {
	
	private KerstEvent ke;
	
	private GameUtils gu;
	
	public OnPlayerLeave(KerstEvent ke) {
		this.ke = ke;
		gu = new GameUtils();
	}
	
	@EventHandler
	public void playerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(ke.USERS.containsKey(p.getUniqueId())) {
			gu.removePlayer(p, ke);
		}
	}

}

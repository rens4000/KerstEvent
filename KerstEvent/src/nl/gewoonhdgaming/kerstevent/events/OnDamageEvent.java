package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import nl.gewoonhdgaming.kerstevent.KerstEvent;

public class OnDamageEvent implements Listener {
	
	private KerstEvent ke;
	
	public OnDamageEvent(KerstEvent ke) {
		this.ke = ke;
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player) e.getEntity();
		if(ke.USERS.containsKey(p.getUniqueId())) {
			p.setHealth(20.0);
			e.setCancelled(true);
		}
	}

}

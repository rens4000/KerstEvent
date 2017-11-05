package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import nl.gewoonhdgaming.kerstevent.KerstEvent;

public class OnFoodChangeEvent implements Listener {
	
	private KerstEvent ke;
	
	public OnFoodChangeEvent(KerstEvent ke) {
		this.ke = ke;
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		Player p = (Player) e.getEntity();
		if(ke.USERS.containsKey(p.getUniqueId())) {
			p.setFoodLevel(20);
			e.setCancelled(true);
		}
	}

}

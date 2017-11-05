package nl.gewoonhdgaming.kerstevent.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import nl.gewoonhdgaming.kerstevent.KerstEvent;
import nl.gewoonhdgaming.kerstevent.util.ChatUtils;
import nl.gewoonhdgaming.kerstevent.util.GameUtils;
import nl.gewoonhdgaming.kerstevent.util.User;

public class OnSnowballHit implements Listener {
	
	private KerstEvent ke;
	
	private ChatUtils cu;
	
	public OnSnowballHit(KerstEvent ke) {
		this.ke = ke;
		cu = new ChatUtils(ke);
	}
	
    @EventHandler (priority = EventPriority.HIGHEST)
    public void onSnowballHit(EntityDamageByEntityEvent event)
    {
        Entity damaged = event.getEntity();
        Entity damageEntity = event.getDamager();
 
        if(damaged instanceof Player)
        if(damageEntity instanceof Snowball)
        {
            Snowball snowball = (Snowball)damageEntity;
            ProjectileSource entityThrower = snowball.getShooter();
            if(entityThrower instanceof Player)
            {
                Player playerThrower = (Player)entityThrower;
                Player playerHit = (Player)damaged;
                if(ke.USERS.containsKey(playerThrower.getUniqueId()) && ke.USERS.containsKey(playerHit.getUniqueId())) {
                	if(playerThrower.getName() == playerHit.getName()) {
                		playerThrower.sendMessage(cu.ERROR + "Je kan geen sneeuwbal op jezelf gooien.");
                		return;
                	}
                	cu.broadcast(ChatColor.GREEN + playerThrower.getName() + ChatColor.AQUA + " heeft " + ChatColor.GREEN + playerHit.getName() + ChatColor.AQUA + " geraakt met een sneeuwbal!");
                	User ut = ke.getUser(playerThrower);
                	User ud = ke.getUser(playerHit);
                	ut.setScore(ut.getScore() + 1);
                	ud.setDeaths(ud.getDeaths() + 1);
                	GameUtils gu = new GameUtils();
                	gu.teleportRandomSpawn(playerHit, ke);
                    
                	gu.generateScoreboard(ke, playerThrower);
                	gu.generateScoreboard(ke, playerHit);
                	
                }
            }
        }
    }

}

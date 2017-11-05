package nl.gewoonhdgaming.kerstevent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import nl.gewoonhdgaming.kerstevent.commands.KerstEventCMD;
import nl.gewoonhdgaming.kerstevent.events.BlockBreakPlaceEvent;
import nl.gewoonhdgaming.kerstevent.events.OnDamageEvent;
import nl.gewoonhdgaming.kerstevent.events.OnFoodChangeEvent;
import nl.gewoonhdgaming.kerstevent.events.OnPlayerLeave;
import nl.gewoonhdgaming.kerstevent.events.OnSignChange;
import nl.gewoonhdgaming.kerstevent.events.OnSignClickEvent;
import nl.gewoonhdgaming.kerstevent.events.OnSnowballHit;
import nl.gewoonhdgaming.kerstevent.util.GameUtils;
import nl.gewoonhdgaming.kerstevent.util.User;

public class KerstEvent extends JavaPlugin {
	
	public Location lobby;
	
	private GameUtils gu = new GameUtils();

	public List<Location> spawn = new ArrayList<Location>();
	
	public HashMap<UUID, User> USERS = new HashMap<UUID, User>();
	
	public boolean active = false;
	
	@Override
	public void onEnable() {
		//Initialize Plugin Manager
		PluginManager pm = Bukkit.getPluginManager();
		
		loadConfig();
		
		pm.registerEvents(new BlockBreakPlaceEvent(this), this);
		pm.registerEvents(new OnDamageEvent(this), this);
		pm.registerEvents(new OnFoodChangeEvent(this), this);
		pm.registerEvents(new OnPlayerLeave(this), this);
		pm.registerEvents(new OnSignChange(this), this);
		pm.registerEvents(new OnSignClickEvent(this), this);
		pm.registerEvents(new OnSnowballHit(this), this);
		getCommand("kerstevent").setExecutor(new KerstEventCMD(this));
	}
	
	@Override
	public void onDisable() {
		gu.kickAllPlayers(this);
	}
	
	public User getUser(Player p) {
		return USERS.get(p.getUniqueId());
	}
	
	public void loadConfig() {
		if(getConfig().contains("lobby")) {
			String world = getConfig().getString("lobby.world");
			Double x = getConfig().getDouble("lobby.x");
			Double y = getConfig().getDouble("lobby.y");
			Double z = getConfig().getDouble("lobby.z");
			lobby = new Location(Bukkit.getWorld(world), x, y, z);
		}
		if(!getConfig().contains("active")) {
			getConfig().set("active", false);
			active = false;
		} else {
			active = getConfig().getBoolean("active");
		}
		if(getConfig().contains("locs")) {
			for(String i : getConfig().getConfigurationSection("locs").getKeys(false)){
					spawn.add(new Location(Bukkit.getWorld(getConfig().getString("locs." + i + ".world")),
							getConfig().getDouble("locs." + i + ".x"),getConfig().getDouble("locs." + i + ".y"),getConfig().getDouble("locs." + i + ".z")));
			}
		}
	}

}

package nl.gewoonhdgaming.kerstevent.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import nl.gewoonhdgaming.kerstevent.KerstEvent;

public class GameUtils {
	
	
	public void addPlayer(Player p, KerstEvent ke) {
		ChatUtils cu = new ChatUtils(ke);
		ke.USERS.put(p.getUniqueId(), new User(p));
		loadPlayerConfig(p, ke);
		teleportRandomSpawn(p, ke);
		User u = ke.getUser(p);
		u.setArmor(p.getInventory().getArmorContents());
		u.setInventory(p.getInventory().getContents());
		p.getInventory().clear();
		p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
		cu.broadcast(ChatColor.GREEN + p.getName() + "(score: " + u.getScore() + ")" + ChatColor.AQUA + " Heeft de game gejoined!");
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(20.0);
		p.setFoodLevel(20);
		
		generateScoreboard(ke, p);
	}
	
	public void generateScoreboard(KerstEvent ke, Player p) {
		User u = ke.getUser(p);
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("kerstevent", ""); 
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "KerstEvent");
        Score streep = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD +"===");
        streep.setScore(11);
        Score score = objective.getScore("");
        score.setScore(10);
        Score score1 = objective.getScore(ChatColor.YELLOW + "Score: " + u.getScore());
        score1.setScore(9);
        Score score2 = objective.getScore(ChatColor.YELLOW + "Deaths: " + u.getDeaths());
        score2.setScore(8);
        Score score3 = objective.getScore("");
        score3.setScore(7);
        Score streep1 = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD +"===");
        streep1.setScore(6);
        Score score4 = objective.getScore(ChatColor.AQUA + "play.gewoonhdgaming.nl");
        score4.setScore(5);
        p.setScoreboard(board);
	}
	
	public void loadPlayerConfig(Player p, KerstEvent ke) {
		if(!ke.getConfig().contains("players." + p.getUniqueId())) {
			ke.getConfig().set("players." + p.getUniqueId() + ".score", 0);
			ke.getConfig().set("players." + p.getUniqueId() + ".deaths", 0);
			User u = ke.getUser(p);
			u.setScore(0);
			u.setDeaths(0);
		} else {
			User u = ke.getUser(p);
			u.setScore(ke.getConfig().getInt("players." + p.getUniqueId() + ".score"));
			u.setDeaths(ke.getConfig().getInt("players." + p.getUniqueId() + ".deaths"));
		}
	}
	
	public void changeActivated(boolean b, KerstEvent ke) {
		ke.active = b;
		ke.getConfig().set("active", b);
		ke.saveConfig();
	}
	
	public void kickAllPlayers(KerstEvent ke) {
		for(User u : ke.USERS.values()) {
			Player p = u.getPlayer();
			ChatUtils cu = new ChatUtils(ke);
			ke.getConfig().set("players." + p.getUniqueId() + ".score", u.getScore());
			ke.getConfig().set("players." + p.getUniqueId() + ".deaths", u.getDeaths());
			ke.saveConfig();
			cu.broadcast(ChatColor.GREEN + p.getName() + "(score: " + u.getScore() + ")" + ChatColor.AQUA + " Heeft de game verlaten!");
			p.getInventory().setContents(u.getInventory());
			p.getInventory().setArmorContents(u.getArmor());
			p.teleport(ke.lobby);
		}
		ke.USERS.clear();
	}

	public void teleportRandomSpawn(Player p, KerstEvent ke) {
		Random rand = new Random();
		int i = rand.nextInt(ke.spawn.size());
		Location loc = ke.spawn.get(i);
		p.teleport(loc);
	}

	public void removePlayer(Player p, KerstEvent ke) {
		ChatUtils cu = new ChatUtils(ke);
		User u = ke.getUser(p);
		ke.getConfig().set("players." + p.getUniqueId() + ".score", u.getScore());
		ke.getConfig().set("players." + p.getUniqueId() + ".deaths", u.getDeaths());
		cu.broadcast(ChatColor.GREEN + p.getName() + "(score: " + u.getScore() + ")" + ChatColor.AQUA + " Heeft de game verlaten!");
		p.getInventory().setContents(u.getInventory());
		p.getInventory().setArmorContents(u.getArmor());
		ke.USERS.remove(p.getUniqueId());
		p.teleport(ke.lobby);
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}

}

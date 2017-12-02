package nl.gewoonhdgaming.kerstevent.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.gewoonhdgaming.kerstevent.KerstEvent;
import nl.gewoonhdgaming.kerstevent.util.ChatUtils;
import nl.gewoonhdgaming.kerstevent.util.GameUtils;
import nl.gewoonhdgaming.kerstevent.util.User;

public class KerstEventCMD implements CommandExecutor {
	
	
	private KerstEvent ke;
	
	private GameUtils gu;
	
	public KerstEventCMD(KerstEvent ke) {
		this.ke = ke;
		gu = new GameUtils();
	}
	
	public boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	private ChatUtils cu = new ChatUtils(ke);

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(cu.ERROR + "Je moet een speler zijn om dit commando uit te voeren!");
			return false;
		}
		Player p = (Player) sender;
		if(args.length == 0) {
			if(p.hasPermission("KerstEvent.Admin")) {
				p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
				
				sendCommand(p, "", "Het algemene kerstevent commando.");
				sendCommand(p, "setlobby", "Maak de lobby van de server.");
				sendCommand(p, "setspawn <id>", "Maak een nieuwe spawn locatie.");
				sendCommand(p, "removespawn <id>", "Verwijder een nieuwe spawn locatie.");
				sendCommand(p, "toggle", "Zet de game aan of uit.");
				sendCommand(p, "join", "Join de game.");
				sendCommand(p, "leave", "Verlaat de game.");
				
				p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
				return false;
			} else {
				p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
				sendCommand(p, "join", "Join de game.");
				sendCommand(p, "leave", "Verlaat de game.");
				sendCommand(p, "stats", "Bekijk de statestieken van jezelf.");
			}
		}
		
		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("removespawn")) {
				if(!p.hasPermission("KerstEvent.Admin")) {
					p.sendMessage(cu.ERROR + "Je hebt geen permissie om dat commando uit te voeren!");
					return false;
				} 
				if(ke.active == true) {
					p.sendMessage(cu.ERROR + "Game is actief! doe /kerst toggle om de game inactief te maken.");
					return false;
				} 
				if(args.length != 2) {
					p.sendMessage(cu.ERROR + "Fout gebruik van commando! Doe /kerstevent setspawn <id>.");
					return false;
				} 
				if(!isInteger(args[1])) {
					p.sendMessage(cu.ERROR + "Het opgegeven id is geen getal!");
					return false;
				} 
				int id = Integer.parseInt(args[1]);
				if(!ke.getConfig().contains("locs." + id)) {
					p.sendMessage(cu.ERROR + "Het opgegeven id bestaat niet in de config!");
					return false;
				}
				Location loc = new Location(Bukkit.getWorld(ke.getConfig().getString("locs." + id + ".world")),
						ke.getConfig().getDouble("locs." + id + ".x"),ke.getConfig().getDouble("locs." + id + ".y"),ke.getConfig().getDouble("locs." + id + ".z"));
				ke.spawn.remove(loc);
				String world = null;
				Double x = null;
				Double y = null;
				Double z = null;
				ke.getConfig().set("locs." + id, null);
				ke.getConfig().set("locs." + id + ".world", world);
				ke.getConfig().set("locs." + id + ".x", x);
				ke.getConfig().set("locs." + id + ".y", y);
				ke.getConfig().set("locs." + id + ".z", z);
				ke.saveConfig();
				p.sendMessage(cu.PREFIX + "Spawn met het id: " + id + " is verwijderd!");
			} else 
			if(args[0].equalsIgnoreCase("stats")) {
				if(ke.USERS.containsKey(p.getUniqueId())) {
					User u = ke.getUser(p);
					p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
					p.sendMessage(ChatColor.YELLOW + "score: " + ChatColor.AQUA + u.getScore());
					p.sendMessage(ChatColor.YELLOW + "geraakt: " + ChatColor.AQUA + u.getDeaths());
					p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
					return false;
				} else {
					if(ke.getConfig().contains("players." + p.getUniqueId())) {
						p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
						p.sendMessage(ChatColor.YELLOW + "score: " + ChatColor.AQUA + ke.getConfig().getInt("players." + p.getUniqueId() + ".score"));
						p.sendMessage(ChatColor.YELLOW + "geraakt: " + ChatColor.AQUA + ke.getConfig().getInt("players." + p.getUniqueId() + ".deaths"));
						p.sendMessage(ChatColor.YELLOW + "-=-=-=-=-=-=-=-=-=-=- " + ChatColor.AQUA + "Kerst" + ChatColor.RED + "Event" + ChatColor.YELLOW + " -=-=-=-=-=-=-=-=-=-=-");
						return false;
					} else {
						p.sendMessage(cu.ERROR + "Je hebt nog geen score! Speel de game om je score te zien!");
					}
				}
			} else
			if(args[0].equalsIgnoreCase("leave")) {
				if(!ke.active) {
					p.sendMessage(cu.ERROR + "De game is niet actief!");
					return false;
				}
				if(!ke.USERS.containsKey(p.getUniqueId())) {
					p.sendMessage(cu.ERROR + "Je zit niet in de game!");
					return false;
				}
				gu.removePlayer(p, ke);
			} else
			if(args[0].equalsIgnoreCase("join")) {
				if(!ke.active) {
					p.sendMessage(cu.ERROR + "De game is niet actief!");
					return false;
				}
				if(ke.USERS.containsKey(p.getUniqueId())) {
					p.sendMessage(cu.ERROR + "Je zit al in de game!");
					return false;
				}
				gu.addPlayer(p, ke);
			} else
			if(args[0].equalsIgnoreCase("toggle")) {
				if(!p.hasPermission("KerstEvent.Admin")) {
					p.sendMessage(cu.ERROR + "Je hebt geen permissie om dat commando uit te voeren!");
					return false;
				}
				if(ke.active == false) {
					if(ke.spawn.size() == 0) {
						p.sendMessage(cu.ERROR + "Er zijn geen nog geen spawn locaties ingesteld! Maak spawn locatie's voordat je de game aanzet.");
						return false;
					}
					if(ke.lobby == null) {
						p.sendMessage(cu.ERROR + "Er is geen lobby!");
						return false;
					}
					gu.changeActivated(true, ke);
					p.sendMessage(cu.PREFIX + "Game is aangezet!");
					return false;
				}
				if(ke.active == true) {
					gu.kickAllPlayers(ke);
					gu.changeActivated(false, ke);
					p.sendMessage(cu.PREFIX + "Game is uitgezet!");
				}
				return false;
			} else
			if(args[0].equalsIgnoreCase("setlobby")) {
				if(!p.hasPermission("KerstEvent.Admin")) {
					p.sendMessage(cu.ERROR + "Je hebt geen permissie om dat commando uit te voeren!");
					return false;
				}
				if(ke.active == true) {
					p.sendMessage(cu.ERROR + "Game is actief! doe /kerst toggle om de game inactief te maken.");
					return false;
				}
				Location loc = p.getLocation();
				String world = loc.getWorld().getName();
				Double x = loc.getX();
				Double y = loc.getY();
				Double z = loc.getZ();
				ke.getConfig().set("lobby.world", world);
				ke.getConfig().set("lobby.x", x);
				ke.getConfig().set("lobby.y", y);
				ke.getConfig().set("lobby.z", z);
				ke.lobby = loc;
				p.sendMessage(cu.PREFIX + "De lobby is neergezet!");
				return false;
			} else
			if(args[0].equalsIgnoreCase("setspawn")) {
				if(!p.hasPermission("KerstEvent.Admin")) {
					p.sendMessage(cu.ERROR + "Je hebt geen permissie om dat commando uit te voeren!");
					return false;
				}
				if(ke.active == true) {
					p.sendMessage(cu.ERROR + "Game is actief! doe /kerst toggle om de game inactief te maken.");
					return false;
				}
				if(args.length != 2) {
					p.sendMessage(cu.ERROR + "Fout gebruik van commando! Doe /kerstevent setspawn <id>.");
					return false;
				}
				if(!isInteger(args[1])) {
					p.sendMessage(cu.ERROR + "Het opgegeven id is geen getal!");
					return false;
				}
				int id = Integer.parseInt(args[1]);
				if(ke.getConfig().contains("locs." + id)) {
					Location loc = p.getLocation();
					String world = loc.getWorld().getName();
					Double x = loc.getX();
					Double y = loc.getY();
					Double z = loc.getZ();
					Location loc1 = new Location(Bukkit.getWorld(ke.getConfig().getString("locs." + id + ".world")),
							ke.getConfig().getDouble("locs." + id + ".x"),ke.getConfig().getDouble("locs." + id + ".y"),ke.getConfig().getDouble("locs." + id + ".z"));
					ke.spawn.remove(loc1);
					ke.getConfig().set("locs." + id + ".world", world);
					ke.getConfig().set("locs." + id + ".x", x);
					ke.getConfig().set("locs." + id + ".y", y);
					ke.getConfig().set("locs." + id + ".z", z);
					ke.saveConfig();
					ke.spawn.add(new Location(Bukkit.getWorld(ke.getConfig().getString("locs." + id + ".world")),
					ke.getConfig().getDouble("locs." + id + ".x"),ke.getConfig().getDouble("locs." + id + ".y"),ke.getConfig().getDouble("locs." + id + ".z")));
					p.sendMessage(cu.PREFIX + "Spawnlocatie met het id: " + id + " is aangepast!");
					
					return false;
				} else {
					Location loc = p.getLocation();
					String world = loc.getWorld().getName();
					Double x = loc.getX();
					Double y = loc.getY();
					Double z = loc.getZ();
					ke.getConfig().set("locs." + id + ".world", world);
					ke.getConfig().set("locs." + id + ".x", x);
					ke.getConfig().set("locs." + id + ".y", y);
					ke.getConfig().set("locs." + id + ".z", z);
					ke.saveConfig();
					ke.spawn.add(new Location(Bukkit.getWorld(ke.getConfig().getString("locs." + id + ".world")),
					ke.getConfig().getDouble("locs." + id + ".x"),ke.getConfig().getDouble("locs." + id + ".y"),ke.getConfig().getDouble("locs." + id + ".z")));
					p.sendMessage(cu.PREFIX + "Spawnlocatie met het id: " + id + " is aangemaakt!");
				
					return false;
				}
			} else {
				p.sendMessage(cu.ERROR + "Commando niet gevonden! Doe /kerstevent om alle commando's te bekijken.");
				return false;
			}
		}
		return false;
	}

	private void sendCommand(Player p, String subcommand, String description) {
		p.sendMessage(ChatColor.AQUA + "/kerstevent " + subcommand + ChatColor.WHITE + " - " + ChatColor.RED + description);
	}

}

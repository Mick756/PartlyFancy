package fancy;

import api.builders.Globals;
import fancy.command.FancyCommandLoader;
import fancy.cosmetics.events.EnderBowEvents;
import fancy.cosmetics.events.GadgetEvents;
import fancy.menu.FancyMenuLoader;
import fancy.menu.events.MenuEvents;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.*;

public class PartlyFancy extends JavaPlugin implements Listener {
	
	private static @Getter PartlyFancy instance;
	private Map<UUID, FancyPlayer> fancyPlayers;
	private final String configVersion = "0.0.1-BETA";
	private static final @Getter Random random = new Random();
	
	@Override
	public void onLoad() {
		instance = this;
		fancyPlayers = new HashMap<>();
		
		log("&9PartlyFancy&7 >>&r &eLoading&b PartlyFancy&c v" + getVersion() + "&e...");
	}
	
	@Override
	public void onEnable() {
		log("&9PartlyFancy&7 >>&r &eEnabling&b PartlyFancy&c v" + getVersion() + "&e...");
		registerListeners(this, new MenuEvents(), new EnderBowEvents(), new GadgetEvents());
		
		log("&9PartlyFancy&7 >>&r &eGenerating&b PartlyFancy&e config&c v" + configVersion + "&e...");
		generateConfig();
		
		log("&9PartlyFancy&7 >>&r &eInitializing&b PartlyFancy&e menus...");
		FancyMenuLoader.initialize();
		
		log("&9PartlyFancy&7 >>&r &eInitializing&6 bStats&e for&b PartlyFancy&e.");
		new Metrics(this, 16081);
	}
	
	@Override
	public void onDisable() {
		log("&9PartlyFancy&7 >>&r &eStop all cosmetics of any&b online&e players.");
		fancyPlayers.forEach((uuid, fancyPlayer) -> fancyPlayer.stopAll(false));
		
		log("&9PartlyFancy&7 >>&r &eUnloaded&b PartlyFancy&c v" + getVersion() + "&c...");
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
		if (command.getName().equalsIgnoreCase("partlyfancy")) {
			if (sender instanceof Player player) {
				FancyPlayer fancyPlayer = FancyPlayer.getFancyPlayer(player);
				
				if (args.length == 0) {
					
					if (getBooleanValue("open-menu-on-default")) {
						FancyCommandLoader.runCommand(player, "menu");
					} else {
						FancyCommandLoader.runCommand(player, "help");
					}
					return true;
				}
				
				int result = FancyCommandLoader.runCommand(player, args);
				
				if (result == -1) {
					fancyPlayer.sendMessageWithPrefix(getStringValue("message.command.not-found", "%player%-" + player.getCustomName()));
				} else if (result == 0) {
					fancyPlayer.sendMessageWithPrefix(getStringValue("message.command.invalid-usage", "%player%-" + player.getCustomName()));
				}
				
			} else {
				sender.sendMessage(ChatColor.RED + "This command is restricted to players.");
			}
		}
		return true;
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		FancyPlayer.getFancyPlayer(p).stopParticle(false);
		
		getFancyPlayers().remove(p.getUniqueId());
	}
	
	@EventHandler
	public void onPlayerTakeFallDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
			boolean takeFallDamage = getBooleanValue("fall-damage-with-gadget-active");
			
			if (!takeFallDamage) {
				FancyPlayer fp = FancyPlayer.getFancyPlayer((Player) e.getEntity());
				
				if (fp.getGadget() != null) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(Globals.color(message));
	}
	
	public static String getVersion() {
		return getInstance().getDescription().getVersion();
	}
	
	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', getStringValue("prefix"));
	}
	
	public static String getStringValue(String path, String... replacements) {
		String message = getInstance().getConfig().getString(path);
		
		if (message != null || (replacements != null && replacements.length != 0)) {
			
			for (String replacement : replacements) {
				String[] split = replacement.split("-", 2);
				
				if (split.length == 2) {
					message = message.replace(split[0], split[1]);
				}
			}
			
			return ChatColor.translateAlternateColorCodes('&', message);
		} else {
			
			return "Error finding requested value at: " + path + ".";
		}
	}
	
	public static List<?> getListValue(String path) {
		return getInstance().getConfig().getList(path);
	}
	
	public static boolean getBooleanValue(String path) {
		return getInstance().getConfig().getBoolean(path);
	}
	
	public static int getIntValue(String path) {
		return getInstance().getConfig().getInt(path);
	}
	
	public static Sound getSound(String path) {
		String sound = getInstance().getConfig().getString(path);
		if (sound == null) return Sound.BLOCK_LEVER_CLICK;
		
		try {
			return Sound.valueOf(sound.toUpperCase());
		} catch (IllegalArgumentException ex) {
			return Sound.BLOCK_LEVER_CLICK;
		}
	}
	
	public static void sendConsoleMessage(String message) {
		getInstance().getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public Map<UUID, FancyPlayer> getFancyPlayers() {
		return fancyPlayers;
	}
	
	private void registerListeners(Listener... listeners) {
		
		for (Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener, this);
		}
	}
	
	private void generateConfig() {
		File f = new File(getDataFolder(), "config.yml");
		
		if (!f.exists()) {
			saveDefaultConfig();
			log("&9PartlyFancy&7 >>&r &eNew&b config.yml&e has been created.");
		} else {
			String ver = getConfig().getString("config-version");
			
			if (ver != null && !ver.equalsIgnoreCase(configVersion)) {
				boolean renamed = f.renameTo(new File(getDataFolder(), "config.yml.old"));
				
				if (renamed) {
					
					saveDefaultConfig();
					log("&9PartlyFancy&7 >>&r &cOld&b PartlyFancy&e config was changed to&c 'config.yml.old'&e.&c (REASON: OUTDATED)");
				} else {
					log("&4PartlyFancy&l >>&r&e Error creating the new config file. It is recommended you rename the old one to allow the creation of a new config.yml");
				}
			}
			
			log("&9PartlyFancy&7 >>&r&b config.yml&c v" + ver + "&e has been loaded.");
		}
	}
	
}
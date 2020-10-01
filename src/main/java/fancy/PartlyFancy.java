package fancy;

import com.cryptomorin.xseries.XSound;
import fancy.command.FancyCommandLoader;
import fancy.cosmetics.events.EnderBowEvents;
import fancy.menu.FancyMenuLoader;
import fancy.menu.events.MenuEvents;
import lombok.Getter;
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

import java.io.File;
import java.util.*;

public class PartlyFancy extends JavaPlugin implements Listener {

    private static @Getter PartlyFancy instance;
    private Map<UUID, FancyPlayer> fancyPlayers;
    //public static final String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    final String configVersion = "0.0.1-BETA";
    
    private static @Getter Random random = new Random();

    @Override
    public void onEnable() {
        instance = this;
        fancyPlayers = new HashMap<>();

        generateConfig();
        getLogger().info("Loading v" + getVersion() + "...");
        registerListeners(this, new MenuEvents(), new EnderBowEvents());

        new FancyCommandLoader();
        new FancyMenuLoader();

    }

    @Override
    public void onDisable() {
        getLogger().info("Unloading v" + getVersion() + "...");

        fancyPlayers.forEach((uuid, fancyPlayer) -> fancyPlayer.stopAll(false));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("cosmetics")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
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
                    player.sendMessage(getPrefix() + ChatColor.RED + getStringValue("message.command.not-found", "%player%-" + player.getDisplayName()));
                } else if (result == 0) {
                    player.sendMessage(getPrefix() + ChatColor.RED + getStringValue("message.command.invalid-usage", "%player%-" + player.getDisplayName()));
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
    
    public static String getVersion() {
        return getInstance().getDescription().getVersion();
    }

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getStringValue("prefix"));
    }
    
    public static String getStringValue(String path, String... replacements) {
        String message = getInstance().getConfig().getString(path);
        if (message != null) {
            if (replacements.length != 0) {
                for (String replacement : replacements) {
                    String[] split = replacement.split("-");
                    if (split.length == 2) {
                        message = message.replace(split[0], split[1]);
                    }
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
        if (sound == null) return XSound.BLOCK_LEVER_CLICK.parseSound();
        try {
            return Sound.valueOf(sound.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return XSound.BLOCK_LEVER_CLICK.parseSound();
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
            getLogger().info("New config.yml has been created.");
        } else {
            String ver = getConfig().getString("config-version");
            if (ver != null && !ver.equalsIgnoreCase(configVersion)) {
                boolean renamed = f.renameTo(new File(getDataFolder(), "config.yml.old"));
                if (renamed) {
                    saveDefaultConfig();
                    getLogger().info("Old config was changed to 'config.yml.old'. (REASON: OUTDATED)");

                } else {
                    getLogger().info("Error creating the new config file. It is recommended you rename the old one to allow the creation of a new config.yml");
                }
            }
        }
    }

}
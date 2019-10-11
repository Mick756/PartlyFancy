package fancy;

import com.sun.istack.internal.NotNull;
import fancy.command.FancyCommandLoader;
import fancy.menu.events.MenuEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PartlyFancy extends JavaPlugin implements Listener {

    private static PartlyFancy instance;
    private static Map<UUID, FancyPlayer> fancyPlayers;
    private static final String configVersion = "0.0.1-BETA";
    // Bukkit version. Ex: 1_14_R1
    public static final String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    @Override
    public void onEnable() {
        instance = this;
        fancyPlayers = new HashMap<>();

        generateConfig();
        getLogger().info("Loading v" + getVersion() + "...");
        registerListeners(this, new MenuEvents());

    }

    @Override
    public void onDisable() {
        getLogger().info("Unloading v" + getVersion() + "...");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("partlyfancy")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                // Default command: /partlyfancy help
                if (args.length == 0) {
                    FancyCommandLoader.runCommand(player, "help");
                    return true;
                }

                int result = FancyCommandLoader.runCommand(player, args);

                // Command not found error
                if (result == -1) {
                    player.sendMessage(getPrefix() + ChatColor.RED + getValue("message.command.not-found", "%player%-" + player.getDisplayName()));

                // Command used incorrectly error
                } else if (result == 0) {
                    player.sendMessage(getPrefix() + ChatColor.RED + getValue("message.command.invalid-usage", "%player%-" + player.getDisplayName()));
                }

            } else {
                sender.sendMessage(ChatColor.RED + "This command is restricted to players.");
            }
        }
        return true;
    }

    // Join event to register players and give item if enabled.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();


    }

    // Quit event to save and unload player data if enabled.
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        FancyPlayer.getFancyPlayer(p).stopParticle(false);

        getFancyPlayers().remove(p.getUniqueId());
    }

    // Instance of PartlyFancy JavaPlugin
    public static PartlyFancy getInstance() {
        return instance;
    }

    // PartlyFancy Version
    public static String getVersion() {
        return getInstance().getDescription().getVersion();
    }

    // Plugin prefix from config.yml
    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', getValue("prefix"));
    }

    /**
     * Retrieve a String value from a path. Does not work with Lists, Booleans, etc.
     * @param path         Path to the config.yml
     * @param replacements Finds all instances of something and replaces is it. %find%-replace
     * @return             The String found or an error if path value is null. Color codes auto translated from '&'
     */
    public static String getValue(@NotNull String path, String... replacements) {
        String message = getInstance().getConfig().getString(path);

        if (message != null) {

            if (replacements.length == 0) {

                return ChatColor.translateAlternateColorCodes('&', message);

            } else {

                for (String replacement : replacements) {

                    // [0] = find, [1] = replacement
                    String[] split = replacement.split("-");

                    // Check if a proper find and replacement is found
                    if (split.length == 2) {
                        message = message.replaceAll(split[0], split[1]);
                    }
                }
                return ChatColor.translateAlternateColorCodes('&', message);
            }

        } else {
            return "Error finding requested value at: " + path + ".";
        }
    }

    /**
     * Retrieve a List value from a path.
     * @param path         Path to the config.yml
     * @return             The List found or an error if path value is null.
     */
    public static List<?> getListValue(@NotNull String path) {
        List<?> list = getInstance().getConfig().getList(path);
        return (list == null ? null : list);
    }

    /**
     * Retrieve a String value from a path. Will take the valueOf(string) from Sound class
     * @param path Path to the config.yml
     * @return     The Sound found or the default sound 'Sound.BLOCK_LEVER_CLICK' if an error occures
     */
    public static Sound getSound(@NotNull String path) {
        String sound = getInstance().getConfig().getString(path);

        try {
            return Sound.valueOf(sound.toUpperCase());

        } catch (IllegalArgumentException ex) {
            return Sound.BLOCK_LEVER_CLICK;
        }
    }

    /**
     * Send a quick message to the console.
     * @param message The message to send
     */
    public static void sendConsoleMessage(String message) {
        getInstance().getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static Map<UUID, FancyPlayer> getFancyPlayers() {
        return fancyPlayers;
    }

    /*
    Private methods for registering listeners, generating configs, etc.
     */

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    /**
     * Generates a config. If one already exists, a version check occurs. If version is found
     * to be out of date, the old config will have the new extension of .old and a new config
     * file will be created taking the place of the old config.
     */
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
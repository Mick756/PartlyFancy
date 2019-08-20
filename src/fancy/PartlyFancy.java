package fancy;

import com.sun.istack.internal.NotNull;
import fancy.command.FancyCommandLoader;
import fancy.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartlyFancy extends JavaPlugin implements Listener {

    private static PartlyFancy instance;
    private static Map<UUID, FancyPlayer> fancyPlayers;
    private static final String configVersion = "0.0.1-BETA";
    public static final String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    @Override
    public void onEnable() {
        instance = this;
        fancyPlayers = new HashMap<>();

        generateConfig();
        getLogger().info("Loading v" + getVersion() + "...");
        registerListeners(this);

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

                if (args.length == 0) {
                    FancyCommandLoader.runCommand(player, "help");
                    return true;
                }

                int result = FancyCommandLoader.runCommand(player, args);

                if (result == -1) {
                    player.sendMessage(getPrefix() + ChatColor.RED + getValue("message.command.not-found", "%player%-" + player.getDisplayName()));
                } else if (result == 0) {
                    player.sendMessage(getPrefix() + ChatColor.RED + getValue("message.command.invalid-usage", "%player%-" + player.getDisplayName()));
                }

            } else {
                sender.sendMessage(ChatColor.RED + "This command is restricted to players.");
            }
        }
        return true;
    }

    // Join event to register players and give item.
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        FancyPlayer fp = FancyPlayer.getFancyPlayer(p);
        getFancyPlayers().put(p.getUniqueId(), fp);
        ItemStack s = NBTUtil.setItemTag(new ItemStack(Material.SADDLE, 1), "open inv", "PartlyFancy");
        p.getInventory().addItem(s);
        getLogger().info(NBTUtil.getItemNBTTag(s).toString());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();

        getFancyPlayers().remove(p.getUniqueId());
    }

    // Instance of PartlyFancy JavaPlugin
    public static PartlyFancy getInstance() {
        return instance;
    }

    //
    public static String getVersion() {
        return getInstance().getDescription().getVersion();
    }

    public static String getPrefix() {
        String prefix = getValue("prefix");
        return ChatColor.translateAlternateColorCodes('&', getValue("prefix"));
    }

    /**
     * Retrieve a String value from a path. Does not work with Lists, Booleans, etc.
     * @param path Path to the config.yml
     * @param replacements Finds all instances of something and replaces is it. %find%-replace
     * @return the String found or an error if path value is null.
     */
    public static String getValue(@NotNull String path, String... replacements) {
        String message = getInstance().getConfig().getString(path);

        if (message != null) {

            if (replacements.length == 0) {
                return ChatColor.translateAlternateColorCodes('&', message);
            } else {
                for (String replacement : replacements) {
                    String[] split = replacement.split("-");
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

    public static Sound getSound(@NotNull String path) {
        String sound = getInstance().getConfig().getString(path);
        try {
            return Sound.valueOf(sound.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return Sound.BLOCK_LEVER_CLICK;
        }
    }

    public static Map<UUID, FancyPlayer> getFancyPlayers() {
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

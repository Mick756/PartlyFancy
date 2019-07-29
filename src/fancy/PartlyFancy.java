package fancy;

import fancy.command.FancyCommandLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PartlyFancy extends JavaPlugin implements Listener {

    private static PartlyFancy instance;
    private static Map<UUID, FancyPlayer> fancyPlayers;

    private static final String DEFAULT_PREFIX = ChatColor.BLUE + "PartlyFancy " + ChatColor.GRAY + ChatColor.BOLD + ">> " + ChatColor.RESET;

    @Override
    public void onEnable() {
        instance = this;
        fancyPlayers = new HashMap<>();
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
                int result = FancyCommandLoader.runCommand(player, args);
                if (result == -1) {
                    player.sendMessage(getPrefix() + ChatColor.RED + "");
                } else if (result == 0) {

                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command is restricted to players.");
            }
        }
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        FancyPlayer fp = FancyPlayer.getFancyPlayer(p);
        getFancyPlayers().put(p.getUniqueId(), fp);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        getFancyPlayers().remove(p.getUniqueId());

    }

    public static PartlyFancy getInstance() {
        return instance;
    }

    public static String getVersion() {
        return getInstance().getDescription().getVersion();
    }

    public static String getPrefix() {
        // TODO: Make it return another prefix if changed in config
        return DEFAULT_PREFIX;
    }

    public static Map<UUID, FancyPlayer> getFancyPlayers() {
        return fancyPlayers;
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}

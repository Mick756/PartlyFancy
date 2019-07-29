package fancy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class PartlyFancy extends JavaPlugin implements Listener {

    private static PartlyFancy instance;
    public static String VERSION;
    private static Map<UUID, FancyPlayer> fancyPlayers;

    @Override
    public void onEnable() {
        instance = this;
        VERSION = getInstance().getDescription().getVersion();
        fancyPlayers = new HashMap<>();
        getLogger().info("Loading v" + VERSION + "...");
        registerListeners(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Unloading v" + VERSION + "...");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        getFancyPlayers().remove(p.getUniqueId());
    }

    public static PartlyFancy getInstance() {
        return instance;
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

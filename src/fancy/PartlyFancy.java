package src.fancy;

import org.bukkit.plugin.java.JavaPlugin;

public class PartlyFancy extends JavaPlugin {

    private static PartlyFancy instance;
    public static final String VERSION = getInstance().getDescription().getVersion();

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("PartlyFancy >> Loading up PartlyFancy v" + VERSION + "...");
    }

    @Override
    public void onDisable() {

    }

    public static PartlyFancy getInstance() {
        return instance;
    }
}

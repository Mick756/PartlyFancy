package fancy;

import org.bukkit.plugin.java.JavaPlugin;

public class PartlyFancy extends JavaPlugin {

    private static PartlyFancy instance;
    public static String VERSION;

    @Override
    public void onEnable() {
        instance = this;
        VERSION = getInstance().getDescription().getVersion();
        getLogger().info("Loading v" + VERSION + "...");
    }

    @Override
    public void onDisable() {
        getLogger().info("Unloading v" + VERSION + "...");
    }

    public static PartlyFancy getInstance() {
        return instance;
    }
}

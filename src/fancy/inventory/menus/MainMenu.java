package fancy.inventory.menus;

import fancy.inventory.FancyMenuLoader;
import fancy.inventory.FancyMenuTheme;
import fancy.inventory.themes.Rainbow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class MainMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;
    public MainMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return 0;
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {

        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Main Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.main", "Permission to the main fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return new Rainbow(this, new Material[]{ Material.BLUE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE,
                Material.ORANGE_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE }, true);
    }
}

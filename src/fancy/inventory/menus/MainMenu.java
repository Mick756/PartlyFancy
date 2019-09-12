package fancy.inventory.menus;

import fancy.inventory.FancyMenuLoader;
import fancy.inventory.FancyMenuTheme;
import fancy.inventory.themes.StaticTheme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class MainMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;
    public MainMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
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
        this.getTheme().apply();

        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Main Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.main", "Permission to the main fancy menu.", PermissionDefault.FALSE);
    }

    @Override
    public FancyMenuTheme getTheme() {
        return new StaticTheme(this, DyeColor.BLUE, true);
    }
}

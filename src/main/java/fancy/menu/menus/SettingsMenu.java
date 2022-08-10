package fancy.menu.menus;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class SettingsMenu implements FancyMenuLoader.FancyMenu {

    private final Inventory inv;

    public SettingsMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return FancyMenuLoader.FancyMenuIds.SETTINGS.getId();
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {
        this.inv.setItem(48, MenuItemConstant.BACK_TO_MAIN_MENU.getItem());
        this.inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());
        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Settings Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.settings", "Permission to the particle settings menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.settings");
    }
}

package fancy.inventory.menus;

import fancy.inventory.FancyMenuLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class MainMenu implements FancyMenuLoader.FancyMenu {
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
        Inventory inv = Bukkit.createInventory(null, 54, this.getName());



        return inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Main Menu";
    }

    @Override
    public Permission permission() {
        return null;
    }
}

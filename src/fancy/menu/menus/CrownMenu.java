package fancy.menu.menus;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class CrownMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;

    public CrownMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return 1;
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {

        inv.setItem(49,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.BARRIER, 1, "&cClose Menu", null, "&7Close this menu.."),
                        "null",
                        "PartlyFancy", "close"
                ));
        inv.setItem(48,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.ARROW, 1, "&cGo Back", null, "&7Go back a menu.."),
                        0,
                        "PartlyFancy", "goback"
                ));

        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Crown Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.crown", "Permission to the main fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.crown");
    }
}

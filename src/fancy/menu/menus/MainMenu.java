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

public class MainMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;

    public MainMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return FancyMenuLoader.FancyMenuIds.MAIN.getId();
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {
        inv.setItem(20,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.BREWING_STAND, 1, "&bParticle Effects", null, "&7Open up the particle effects."),
                        FancyMenuLoader.FancyMenuIds.PARTICLE.getId(),
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(48, MenuItemConstant.TURN_OFF_ALL.getItem());
        inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());
        inv.setItem(50,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.COMPARATOR, 1, "&aYour Settings", null, "&7Open the settings menu.."),
                        FancyMenuLoader.FancyMenuIds.SETTINGS.getId(),
                        "PartlyFancy", "openinv"
                ));

        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Main Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.main", "Permission to the main fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.main");
    }
}

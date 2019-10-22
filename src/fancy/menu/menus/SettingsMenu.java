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

public class SettingsMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;

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

        inv.setItem(20,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.MAP, 1, "&aToggle Particles", null, "&7Click to toggle whether you can", "&7see your own particles."),
                        "null",
                        "PartlyFancy", "changeParticleViewSetting"
                ));

        inv.setItem(48,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.ARROW, 1, "&cGo Back", null, "&7Go back a menu.."),
                        FancyMenuLoader.FancyMenuIds.MAIN.getId(),
                        "PartlyFancy", "goback"
                ));
        inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());

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

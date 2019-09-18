package fancy.menu.menus;

import fancy.cosmetics.particles.AuraParticle;
import fancy.cosmetics.particles.CrownParticle;
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
        return 0;
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {
        inv.setItem(20,
                NBTUtil.setItemTag(
                        CrownParticle.item(),
                        1,
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(22,
                NBTUtil.setItemTag(
                        AuraParticle.item(),
                        2,
                        "PartlyFancy", "openinv"
                ));
        inv.setItem(49,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.BARRIER, 1, "&cClose Menu", null, "&7Close this menu.."),
                        "null",
                        "PartlyFancy", "close"
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

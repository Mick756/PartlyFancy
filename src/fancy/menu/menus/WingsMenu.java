package fancy.menu.menus;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import java.util.List;

public class WingsMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;

    public WingsMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return 3;
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {

        List<ItemStack> items = FancyUtil.generateParticleItems("wings_particle");

        int index = 0;
        for (int i = 20; i < 34; i++) {

            inv.setItem(i, items.get(index));

            index++;
            if (i == 24) {
                i = 28;
            }
        }

        inv.setItem(48,
                NBTUtil.setItemTag(
                        FancyUtil.createItemStack(Material.ARROW, 1, "&cGo Back", null, "&7Go back a menu.."),
                        0,
                        "PartlyFancy", "goback"
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
        return ChatColor.BLACK + "Fancy Wings Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.crown", "Permission to the wings fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.wings");
    }
}

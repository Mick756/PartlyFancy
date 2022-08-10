package fancy.menu.menus.particle;

import api.builders.ItemStackBuilder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.menus.MenuItemConstant;
import fancy.util.CosmeticUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;

import java.util.List;

public class CrownMenu implements FancyMenuLoader.FancyMenu {

    private final Inventory inv;

    public CrownMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return FancyMenuLoader.FancyMenuIds.PARTICLE_CROWN.getId();
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {
        List<ItemStack> items = CosmeticUtil.generateParticleItems("crown_particle");
        int index = 0;
        for (int i = 19; i < 35; i++) {
            if (items.size() <= index) break;
            this.inv.setItem(i, items.get(index));
            index++;
            if (i == 25) {
                i = 27;
            }
        }
        
        NBTItem back = new NBTItem(new ItemStackBuilder(Material.ARROW).setDisplayName("&cGo Back").setLore("&7Go back a menu..").build());
        back.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE.getId());
        this.inv.setItem(48, back.getItem());
        
        this.inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());

        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Crown Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.crown", "Permission to the crown fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.crown");
    }
}

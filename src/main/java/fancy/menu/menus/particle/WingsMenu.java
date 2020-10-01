package fancy.menu.menus.particle;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.menus.MenuItemConstant;
import fancy.util.CosmeticUtil;
import fancy.util.Particles;
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
        return FancyMenuLoader.FancyMenuIds.PARTICLE_WINGS.getId();
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return this;
    }

    @Override
    public Inventory getInventory() {
        List<ItemStack> items = CosmeticUtil.generateParticleItems("wings_particle", Particles.ANGRY_VILLAGER, Particles.HEARTS, Particles.SPARKS, Particles.NOTES, Particles.WATER_DROPS, Particles.LAVA_DROPS, Particles.SLIME);
        int index = 0;
        for (int i = 19; i < 35; i++) {
            if (items.size() <= index) break;
            this.inv.setItem(i, items.get(index));
            index++;
            if (i == 25) {
                i = 27;
            }
        }
        
        NBTItem back = new NBTItem(CosmeticUtil.createItemStack(Material.ARROW, 1, "&cGo Back", null, "&7Go back a menu.."));
        back.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE.getId());
        this.inv.setItem(48, back.getItem());
        
        this.inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());
        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Wings Menu";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.wings", "Permission to the wings fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.wings");
    }
}

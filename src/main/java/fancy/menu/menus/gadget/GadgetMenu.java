package fancy.menu.menus.gadget;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.menus.MenuItemConstant;
import fancy.util.CosmeticUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class GadgetMenu implements FancyMenuLoader.FancyMenu {

    private Inventory inv;

    public GadgetMenu() {
        this.inv = Bukkit.createInventory(null, 54, this.getName());
        this.getTheme().apply();
    }

    @Override
    public Integer inventoryId() {
        return FancyMenuLoader.FancyMenuIds.GADGET_PAGE_ONE.getId();
    }

    @Override
    public FancyMenuLoader.FancyMenu getInstance() {
        return null;
    }

    @Override
    public Inventory getInventory() {
        NBTItem enderBow = new NBTItem(CosmeticUtil.createItemStack(Material.BOW, 1, "&5Ender Bow", null, "&7A magical bow that quickly", "&7fires a ender pearl,", "&7not an arrow.", " ", "&aClick to select!"));
        enderBow.setString("gadget", "enderbow");
        this.inv.setItem(20, enderBow.getItem());
        
        this.inv.setItem(48, MenuItemConstant.BACK_TO_MAIN_MENU.getItem());
        
        return this.inv;
    }

    @Override
    public String getName() {
        return ChatColor.BLACK + "Fancy Gadgets Menu (Page 1)";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.menu.gadget.one", "Permission to the aura fancy menu.");
    }

    @Override
    public FancyMenuTheme getTheme() {
        return FancyMenuTheme.parseTheme(this, "menu.gadget.page.one");
    }
}

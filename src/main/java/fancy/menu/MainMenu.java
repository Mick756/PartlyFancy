package fancy.menu;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.menu.menus.MenuItemConstant;
import fancy.util.CosmeticUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        
        NBTItem particlesItem = new NBTItem(CosmeticUtil.createItemStack(XMaterial.BREWING_STAND.parseItem().getType(), 1, "&bParticle Effects", null, "&7Open up the particle effects menu."));
        particlesItem.setInteger("inventory", FancyMenuLoader.FancyMenuIds.PARTICLE.getId());
        this.inv.setItem(20, particlesItem.getItem());
        
        NBTItem gadgetsItem = new NBTItem(CosmeticUtil.createItemStack(XMaterial.PISTON.parseItem().getType(), 1, "&bGadgets", null, "&7Open up the gadgets menu."));
        gadgetsItem.setInteger("inventory", FancyMenuLoader.FancyMenuIds.GADGET_PAGE_ONE.getId());
        this.inv.setItem(22, gadgetsItem.getItem());
        
        this.inv.setItem(48, MenuItemConstant.TURN_OFF_ALL.getItem());
        this.inv.setItem(49, MenuItemConstant.CLOSE_MENU.getItem());
        
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

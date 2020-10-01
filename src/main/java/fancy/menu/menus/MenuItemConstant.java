package fancy.menu.menus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.menu.FancyMenuLoader;
import lombok.Getter;
import fancy.util.CosmeticUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum MenuItemConstant {

    CLOSE_MENU (CosmeticUtil.createItemStack(Material.BARRIER, 1, "&cClose Menu", "&7Close this menu.."), "action", "close"),
    TURN_OFF_ALL (CosmeticUtil.createItemStack(Material.REDSTONE_BLOCK, 1, "&cTurn off Cosmetics","&7Turn off all cosmetics.."), "action", "stopall"),
    BACK_TO_MAIN_MENU (CosmeticUtil.createItemStack(Material.ARROW, 1, "&cGo Back", "&7Go back a menu.."), "inventory", FancyMenuLoader.FancyMenuIds.MAIN.getId());

    private @Getter ItemStack item;
    MenuItemConstant(ItemStack stack, String key, Object nbtAction) {
        NBTItem nbt = new NBTItem(stack);
        if (nbtAction instanceof String) {
            nbt.setString(key, (String) nbtAction);
        } else if (nbtAction instanceof Integer) {
            nbt.setInteger(key, (int) nbtAction);
        }
        this.item = nbt.getItem();
    }
    
}

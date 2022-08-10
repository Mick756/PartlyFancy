package fancy.menu.menus;

import api.builders.ItemStackBuilder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.menu.FancyMenuLoader;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum MenuItemConstant {

    CLOSE_MENU (new ItemStackBuilder(Material.BARRIER).setDisplayName("&cClose Menu").setLore("&7Close this menu..").build(), "action", "close"),
    TURN_OFF_ALL (new ItemStackBuilder(Material.REDSTONE_BLOCK).setDisplayName("&cTurn off Cosmetics").setLore("&7Turn off all cosmetics..").build(), "action", "stopall"),
    BACK_TO_MAIN_MENU (new ItemStackBuilder(Material.ARROW).setDisplayName("&cGo Back").setLore("&7Go back a menu..").build(), "inventory", FancyMenuLoader.FancyMenuIds.MAIN.getId());

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

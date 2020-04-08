package fancy.menu.menus;

import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum MenuItemConstant {

    CLOSE_MENU (NBTUtil.setItemTag(FancyUtil.createItemStack(Material.BARRIER, 1, "&cClose Menu", null, "&7Close this menu.."), "null", "PartlyFancy", "close")),

    TURN_OFF_ALL (NBTUtil.setItemTag(FancyUtil.createItemStack(Material.REDSTONE_BLOCK, 1, "&cTurn off Cosmetics", null, "&7Turn off all cosmetics.."), "null", "PartlyFancy", "stopall"));

    private ItemStack stack;
    MenuItemConstant(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStack getItem() {
        return this.stack;
    }

}

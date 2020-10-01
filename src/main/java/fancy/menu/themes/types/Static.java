package fancy.menu.themes.types;

import fancy.menu.FancyMenuTheme;
import org.bukkit.inventory.ItemStack;

public interface Static {

    ItemStack item();

    FancyMenuTheme setItem(ItemStack stack);

}

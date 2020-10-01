package fancy.menu.themes.types;

import fancy.menu.FancyMenuTheme;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface MultiColor {

    ItemStack[] items();

    FancyMenuTheme setItems(List<ItemStack> items);

}

package fancy.util;

import com.sun.istack.internal.NotNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;

public class FancyUtil {

    /**
     * Easily create an ItemStack with name, amount, damage, and lore with one method
     * @param name     Name to be displayed
     * @param material org.bukkit.Material
     * @param amount   integer 0 < amount < 65
     * @param damage   short
     * @param lore     String[] lore in order from top to bottom
     * @return         org.bukkit.inventory.ItemStack with attributes defined
     */
    public static ItemStack createItemStack(@NotNull Material material, @NotNull Integer amount, @Nullable String name, @Nullable Short damage, @Nullable String... lore) {
        // Safety for creation
        amount = (amount == null || amount < 1 || amount > 64) ? 1 : amount;

        ItemStack itemStack;

        if (damage != null) {
            itemStack = new ItemStack(material, amount, damage);
        } else {
            itemStack = new ItemStack(material, amount);
        }

        ItemMeta meta = itemStack.getItemMeta();

        // Display name
        if (name != null && name.length() != 0) {
            meta.setDisplayName(name);
        }

        // Lore
        if (lore != null && lore.length != 0) {
            meta.setLore(Arrays.asList(lore));
        }

        return itemStack;
    }

    /**
     * Adds enchantment. Always added using 'unsafe' method.
     * @param stack       Input ItemStack
     * @param enchantment org.bukkit.enchantments.Enchantment
     * @param level       Enchantment level
     * @return            ItemStack with added Enchantment (will overwrite pre-existing enchantment)
     */
    public static ItemStack addEnchantment(@NotNull  ItemStack stack, @NotNull Enchantment enchantment, @Nullable Integer level) {
        // Safety for creation
        level = (level == null || level < 1 || level > 255) ? 1 : level;

        if (stack != null && enchantment != null) {
            stack.getEnchantments().remove(enchantment);
            stack.addUnsafeEnchantment(enchantment, level);
        }

        return stack;
    }

    /**
     * Adds multiple enchantments. Always added using 'unsafe' method.
     * @param stack        Input ItemStack
     * @param enchantments <org.bukkit.enchantments.Enchantment, Integer>
     * @return             ItemStack with added Enchantments (will clear pre-existing enchantments)
     */
    public static ItemStack addEnchantments(@NotNull ItemStack stack, @NotNull Map<Enchantment, Integer> enchantments) {

        if (stack != null && !enchantments.isEmpty()) {
            stack.getEnchantments().clear();
            stack.addUnsafeEnchantments(enchantments);
        }

        return stack;
    }

    /**
     * @param input A string value
     * @return      true is a number - false is not a number or is not formatted correctly
     */
    public static boolean isInteger(@NotNull String input) {

        try {

            Integer.parseInt(input.replace(" ", ""));
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    /**
     * @param input A string value
     * @return      -1 if not a number - number is input is formatted correctly
     */
    public static Integer fromString(@NotNull String input) {

        try {

            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return -1;
        }

    }

    /**
     * Get an array of ints locating the boarders of any size chest-like inventory. (Does not work for anvils, hoppers, etc.)
     * @param inv Inventory to trace.
     * @param omit Omit the center 3 slots at the bottom to allow for menu navigational purposes.
     * @return Array of ints reflecting the slots of the inventory
     */
    public static int[] getInventoryBorder(Inventory inv, boolean omit) {
        int invSize = inv.getSize();
        if (invSize >= 9 && invSize <= 54) {
            if (omit) {
                switch (invSize) {
                    case 9:
                        return new int[]{0, 8};
                    case 18:
                        return new int[]{0, 8, 9, 17};
                    case 27:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 24, 25, 26};
                    case 36:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 33, 34, 35};
                    case 45:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 42, 43, 44};
                    case 54:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 51, 52, 53};
                }
            } else {
                switch (invSize) {
                    case 9:
                        return new int[]{0, 8};
                    case 18:
                        return new int[]{0, 8, 9, 17};
                    case 27:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
                    case 36:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
                    case 45:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
                    case 54:
                        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
                }
            }
        }
        return new int[]{};
    }
}

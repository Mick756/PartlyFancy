package fancy.util;

import com.sun.istack.internal.NotNull;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
}

package fancy.util;

import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.*;

public class FancyUtil {

    public static final Random RANDOM = new Random();

    /**
     * Easily create an ItemStack with name, amount, damage, and lore with one method
     * @param name     Name to be displayed
     * @param material org.bukkit.Material
     * @param amount   integer 0 < amount < 65
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
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        }

        // Lore
        if (lore != null && lore.length != 0) {
            List<String> loreLines = new ArrayList<>();

            Arrays.asList(lore).forEach(line -> {
                loreLines.add(ChatColor.translateAlternateColorCodes('&', line));
            });
            meta.setLore(loreLines);
        }

        itemStack.setItemMeta(meta);

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
     * @param inv  Inventory to trace.
     * @param omit Omit the center 3 slots at the bottom to allow for menu navigational purposes.
     * @return     Array of ints reflecting the slots of the inventory
     */
    public static int[] getInventoryBorder(Inventory inv, boolean omit) {
        int invSize = inv.getSize();
        switch (invSize) {
            case 9:
                return new int[]{0, 8};
            case 18:
                return new int[]{0, 8, 9, 17};
            case 27:
                if (!omit) {
                    return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
                } else return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 24, 25, 26};
            case 36:
                if (!omit) {
                    return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
                } else return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 33, 34, 35};
            case 45:
                if (!omit) {
                    return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
                } else return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 42, 43, 44};
            case 54:
                if (!omit) {
                    return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
                } else return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 51, 52, 53};
            default:
                return new int[]{};
        }
    }

    /**
     * Generate the Particle items for use in menus
     * @param nbt_key Key specifying which particle effect. Ex. 'crown_particle'
     * @return        A list of all the items
     */
    public static List<ItemStack> generateParticleItems(String nbt_key) {

        List<ItemStack> items = new ArrayList<>();
        Object[] keys = new Object[]{"PartlyFancy", nbt_key};

        for (Particles particle : Particles.values()) {

            String name;

            if (particle.name().contains("_")) {

                String[] split = particle.name().split("_");
                name = (split[0].substring(0, 1).toUpperCase() + split[0].substring(1).toLowerCase()) + " " + (split[1].substring(0, 1).toUpperCase() + split[1].substring(1).toLowerCase());

            } else {

                name = (particle.name().substring(0, 1).toUpperCase() + particle.name().substring(1).toLowerCase());
            }

            ItemStack stack = NBTUtil.setItemTag(createItemStack(particle.item, 1, "&b" + name, null, particle.description), particle.name(), keys);
            items.add(stack);

        }
        return items;
    }

    /**
     * Generates a random number.
     *
     * @param minimum the minimum value of the generated value.
     * @param maximum the maximum value of the generated value.
     * @return        a randomly generated int in the defined range.
     * @see #RANDOM
     */
    public static int generateRandomInteger(int minimum, int maximum) {
        return minimum + (int) (RANDOM.nextDouble() * ((maximum - minimum) + 1));
    }


    /**
     * @param value the value which should be checked.
     * @param max   the maximum value.
     * @param min   the minimum value
     * @return      the calculated value.
     */
    public static int getMaxOrMin(int value, int max, int min) {
        return value >= max ? max : (value <= min ? min : value);
    }

    /**
     * Rotate a current vector at a certain angle around the y-axis
     * @param v     The vector to rotate
     * @param angle The angle to rotate by
     * @return      A vector rotated from the other
     */
    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    /**
     * Get the back of a location
     * @param loc Relative location
     * @return    A vector
     */
    public static Vector getBackVector(Location loc) {
        float newZ = (float)(loc.getZ() + 1.0f * Math.sin(Math.toRadians(loc.getYaw() + 90.0F)));
        float newX = (float)(loc.getX() + 1.0f * Math.cos(Math.toRadians(loc.getYaw() + 90.0F)));
        return new Vector(newX - loc.getX(), 0.0f, newZ - loc.getZ());
    }

    /**
     * Get the cardinal direction a player is looking
     * @param player Player to check
     * @return       A string of the direction Ex. 'NE'
     */
    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        return (337.5D <= rotation) && (rotation < 360.0D) ? "N" : (292.5D <= rotation) && (rotation < 337.5D) ? "NW" : (247.5D <= rotation) && (rotation < 292.5D) ? "W" : (202.5D <= rotation) && (rotation < 247.5D) ? "SW" : (157.5D <= rotation) && (rotation < 202.5D) ? "S" : (112.5D <= rotation) && (rotation < 157.5D) ? "SE" : (67.5D <= rotation) && (rotation < 112.5D) ? "E" : (22.5D <= rotation) && (rotation < 67.5D) ? "NE" : (0.0D <= rotation) && (rotation < 22.5D) ? "N" : null;
    }

    /**
     * Get a vector that points from a starting location to the final.
     * @param first  Starting location
     * @param second Ending location
     * @return       A vector
     */
    public static Vector getVector(Location first, Location second) {
        Vector from = new Vector(first.getX(), first.getY(), first.getZ());
        Vector to = new Vector(second.getX(), second.getY(), second.getZ());
        return to.subtract(from);
    }

    private static double deg_to_rad = Math.PI/180;
    public static Vector rotateVectorDegree(Vector v, double degrees) {
        return rotateVectorRadians(v, degrees * deg_to_rad);
    }

    public static Vector rotateVectorRadians(Vector v, double radians) {
        double ca = Math.cos(radians);
        double sa = Math.sin(radians);
        return new Vector(ca*v.getX() - sa*v.getZ(), v.getY(), sa*v.getX() + ca*v.getZ());
    }
}

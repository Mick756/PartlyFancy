package fancy.util;

import api.builders.ItemStackBuilder;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class CosmeticUtil {

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input.replace(" ", ""));
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

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
    
    public static List<ItemStack> generateParticleItems(String nbt_key, Particles... excludes) {
        List<ItemStack> items = new ArrayList<>();
        for (Particles particle : Particles.values()) {
            boolean isExcluded = false;
            for (Particles exp : excludes) {
                if (exp.equals(particle)) {
                    isExcluded = true;
                    break;
                }
            }
            if (!isExcluded) {
                String name;
                if (particle.name().contains("_")) {
                    String[] split = particle.name().split("_");
                    name = (split[0].substring(0, 1).toUpperCase() + split[0].substring(1).toLowerCase()) + " " + (split[1].substring(0, 1).toUpperCase() + split[1].substring(1).toLowerCase());
                } else {
                    name = (particle.name().substring(0, 1).toUpperCase() + particle.name().substring(1).toLowerCase());
                }
                ItemStack stack = new ItemStackBuilder(particle.getItem()).setDisplayName("&b" + name).setLore(particle.getDescription()).build();
                NBTItem nbt = new NBTItem(stack);
                nbt.setString("particle", nbt_key);
                items.add(nbt.getItem());
            }
        }
        return items;
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    public static Vector getBackVector(Location loc) {
        float newZ = (float)(loc.getZ() + 1.0f * Math.sin(Math.toRadians(loc.getYaw() + 90.0F)));
        float newX = (float)(loc.getX() + 1.0f * Math.cos(Math.toRadians(loc.getYaw() + 90.0F)));
        return new Vector(newX - loc.getX(), 0.0f, newZ - loc.getZ());
    }

    public static String getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        return (337.5D <= rotation) && (rotation < 360.0D) ? "N" : (292.5D <= rotation) && (rotation < 337.5D) ? "NW" : (247.5D <= rotation) && (rotation < 292.5D) ? "W" : (202.5D <= rotation) && (rotation < 247.5D) ? "SW" : (157.5D <= rotation) && (rotation < 202.5D) ? "S" : (112.5D <= rotation) && (rotation < 157.5D) ? "SE" : (67.5D <= rotation) && (rotation < 112.5D) ? "E" : (22.5D <= rotation) && (rotation < 67.5D) ? "NE" : (0.0D <= rotation) && (rotation < 22.5D) ? "N" : null;
    }

    public static Vector getVector(Location first, Location second) {
        Vector from = new Vector(first.getX(), first.getY(), first.getZ());
        Vector to = new Vector(second.getX(), second.getY(), second.getZ());
        return to.subtract(from);
    }

    private static final double deg_to_rad = Math.PI/180;
    public static Vector rotateVectorDegree(Vector v, double degrees) {
        return rotateVectorRadians(v, degrees * deg_to_rad);
    }

    public static Vector rotateVectorRadians(Vector v, double radians) {
        double ca = Math.cos(radians);
        double sa = Math.sin(radians);
        return new Vector(ca*v.getX() - sa*v.getZ(), v.getY(), sa*v.getX() + ca*v.getZ());
    }
}

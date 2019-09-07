package fancy.inventory;

import fancy.PartlyFancy;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

public class FancyInventoryLoader {

    public static boolean openInventory(Player player, FancyInventory inventory) {

        if (player.hasPermission(inventory.permission())) {

            player.closeInventory();
            player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.open"), 2f, 1f);
            player.openInventory(inventory.getInventory());
            return true;
        }
        return false;
    }

    public interface FancyInventory {

        FancyInventory getInstance();

        Inventory getInventory();

        String getName();

        Permission permission();

    }
}

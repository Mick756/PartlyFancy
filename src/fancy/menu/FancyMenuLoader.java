package fancy.menu;

import fancy.PartlyFancy;
import fancy.menu.menus.MainMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.List;

public class FancyMenuLoader {

    /**
     * Open a Fancy Menu. Automatically checks if player has permission and plays sound if true.
     * @param player    Player to open the menu
     * @param inventory FancyMenu to open
     * @param sound     Play a sound before the opening. (config-path: sound.inventory.open)
     * @return          If the inventory was opened successfully
     */
    public static boolean openMenu(Player player, FancyMenu inventory, boolean sound) {

        if (player.hasPermission(inventory.permission())) {
            player.closeInventory();

            if (sound) {
                player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.open"), 2f, 1f);
            }

            player.openInventory(inventory.getInventory());
            return true;
        } else {
            player.sendMessage(PartlyFancy.getValue("message.menu.no-permission", "%player%-" + player.getDisplayName(), "%permission%-" + inventory.permission().getName()));
        }
        return false;
    }

    public interface FancyMenu {

        static List<FancyMenu> menus = Arrays.asList(new FancyMenu[]{new MainMenu()});

        /*
        Inventory Id
         */
        Integer inventoryId();

        /*
        Use this to open an Inventory
         */
        FancyMenu getInstance();

        Inventory getInventory();

        /*
        Color stripped inventory name
         */
        String getName();

        Permission permission();

        /*
        Style for the inventory
         */
        FancyMenuTheme getTheme();

        static FancyMenu getFromId(int id) {
            for (FancyMenu m : menus) {
                if (m.inventoryId().equals(id)) {
                    return m;
                }
            }
            return new MainMenu();
        }

        static void registerFancyMenu(FancyMenu m) {
            menus.add(m);
        }

    }
}
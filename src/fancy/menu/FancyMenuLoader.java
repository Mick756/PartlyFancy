package fancy.menu;

import fancy.PartlyFancy;
import fancy.menu.menus.MainMenu;
import fancy.menu.menus.ParticleMenu;
import fancy.menu.menus.SettingsMenu;
import fancy.menu.menus.particle.AuraMenu;
import fancy.menu.menus.particle.CrownMenu;
import fancy.menu.menus.particle.OrbMenu;
import fancy.menu.menus.particle.WingsMenu;
import fancy.menu.themes.Rainbow;
import fancy.menu.themes.Snake;
import fancy.menu.themes.Solid;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class FancyMenuLoader {

    public static List<FancyMenu> menus = new ArrayList<>();
    public static List<FancyMenuTheme> themes = new ArrayList<>();

    static {

        //Themes
        addTheme(new Solid());
        addTheme(new Rainbow());
        addTheme(new Snake());

        // Main Menus
        registerFancyMenu(new MainMenu());
        registerFancyMenu(new ParticleMenu());
        registerFancyMenu(new SettingsMenu());

        // Particle Menus
        registerFancyMenu(new CrownMenu());
        registerFancyMenu(new AuraMenu());
        registerFancyMenu(new WingsMenu());
        registerFancyMenu(new OrbMenu());


        for (FancyMenu menu : menus) {

            Bukkit.getPluginManager().addPermission(menu.permission());

        }
    }

    /**
     * Open a Fancy Menu. Automatically checks if player has permission and plays sound if true.
     * @param player    Player to open the menu
     * @param inventory FancyMenu to open
     * @param sound     Play a sound before the opening. (config-path: sound.inventory.open)
     * @return          If the inventory was opened successfully
     */
    public static boolean openMenu(Player player, FancyMenu inventory, boolean sound) {

        if (player.hasPermission(inventory.permission())) {

            if (sound) {
                player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.open"), 0.2f, 1f);
            }

            player.openInventory(inventory.getInventory());
            return true;
        } else {
            player.sendMessage(PartlyFancy.getPrefix() + PartlyFancy.getStringValue("message.menu.no-permission", "%player%-" + player.getDisplayName(), "%permission%-" + inventory.permission().getName()));

            if (sound) {
                player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.no-permission"), 0.5f, 1f);
            }

        }
        return false;
    }

    public static void closeMenu(Player player, boolean sound) {
        player.closeInventory();

        if (sound) {
            player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.close"), 0.2f, 1f);
        }

        player.updateInventory();
    }

    public enum FancyMenuIds {

        MAIN(1), PARTICLE(2), SETTINGS(3),

        PARTICLE_CROWN(20), PARTICLE_AURA(21), PARTICLE_ORB(22), PARTICLE_WINGS(23);

        int id;
        FancyMenuIds(int id) {
            this.id = id;
        }

        public String getIdString() {
            return Integer.toString(id);
        }

        public int getId() {
            return id;
        }
    }

    public static void registerFancyMenu(FancyMenu m) {
        menus.add(m);
    }

    static void addTheme(FancyMenuTheme theme) {
        themes.add(theme);
    }

    public static FancyMenu getFromId(int id) {
        for (FancyMenu m : menus) {
            if (m.inventoryId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public interface FancyMenu {

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

    }

}

package fancy.cosmetics;

import org.bukkit.inventory.ItemStack;

public interface FancyCosmetic {

    /**
     * @return Name of cosmetic item
     */
    String name();

    /**
     * @return if able to start
     */
    boolean start();

    /**
     * @return if able to stop
     */
    boolean stop();

    /**
     * @return type of cosmetic item
     */
    FancyCosmeticType type();

    /**
     * @return item to display in selection inventories
     */
    ItemStack item();

}

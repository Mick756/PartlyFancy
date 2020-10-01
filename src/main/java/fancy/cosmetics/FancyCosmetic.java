package fancy.cosmetics;

import org.bukkit.entity.Player;

public interface FancyCosmetic {

    /**
     * @return Player of cosmetic
     */
    Player getPlayer();

    /**
     * Assign the player to a cosmetic
     */
    void setPlayer(Player player);

    /**
     * @return Name of cosmetic item
     */
    String name();

    /**
     * Start cosmetic.
     */
    boolean start();

    /**
     * Stop cosmetic.
     */
    boolean stop();


}

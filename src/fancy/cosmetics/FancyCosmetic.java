package fancy.cosmetics;

import org.bukkit.entity.Player;

public interface FancyCosmetic {

    /**
     * @return Player of cosmetic
     */
    Player getPlayer();

    /**
     * @return Name of cosmetic item
     */
    String name();

    /**
     * Start cosmetic.
     */
    void start();

    /**
     * Stop cosmetic.
     */
    void stop();


}

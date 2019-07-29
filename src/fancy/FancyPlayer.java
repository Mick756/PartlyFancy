package fancy;

import fancy.cosmetics.FancyCosmetic;
import fancy.cosmetics.FancyCosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class FancyPlayer {

    private UUID playerUUID;
    private Map<FancyCosmeticType, FancyCosmetic> activeCosmetics;

    /**
     * PartlyFancy's player object
     * @param p Player to create object for
     */
    public FancyPlayer(Player p) {

        this.playerUUID = p.getUniqueId();
        this.activeCosmetics = new HashMap<>();
    }

    public boolean startCosmetic(FancyCosmetic cosmetic) {

        if (!activeCosmetics.containsKey(cosmetic.type())) {
            activeCosmetics.put(cosmetic.type(), cosmetic);

            if (cosmetic.start()) {
                return true;
            } else {
                activeCosmetics.remove(cosmetic.type());
                return false;
            }

        }

        return false;
    }

    public boolean stopCosmetic(FancyCosmeticType type) {

        if (activeCosmetics.containsKey(type)) {
            FancyCosmetic fc = activeCosmetics.get(type);
            return fc.stop();
        }

        return true;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUUID);
    }

    public static FancyPlayer getFancyPlayer(Player player) {
        return getFancyPlayer(player.getUniqueId());
    }

    public static FancyPlayer getFancyPlayer(UUID uuid) {

        if (PartlyFancy.getFancyPlayers().containsKey(uuid)) {
            return PartlyFancy.getFancyPlayers().get(uuid);
        } else {
            return new FancyPlayer(Bukkit.getPlayer(uuid));
        }
    }

}

package fancy;

import fancy.cosmetics.FancyCosmetic;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FancyPlayer {

    private UUID playerUUID;
    private List<FancyCosmetic> activeCosmetics;
    private boolean operator;

    /**
     * PartlyFancy's player object
     * @param p Player to create object for
     */
    public FancyPlayer(Player p) {
        this.playerUUID = p.getUniqueId();
        this.activeCosmetics = new ArrayList<>();
        this.operator = p.isOp();
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

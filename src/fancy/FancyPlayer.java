package fancy;

import fancy.cosmetics.FancyCosmetic;
import fancy.cosmetics.Gadget;
import fancy.cosmetics.Particle;
import fancy.cosmetics.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class FancyPlayer {

    private UUID playerUUID;
    private Particle particleEffect = null;
    private Gadget gadget = null;
    private Pet pet = null;

    /**
     * PartlyFancy's player object
     * @param p Player to create object for
     */
    private FancyPlayer(Player p) {

        this.playerUUID = p.getUniqueId();
    }

    public void startCosmetic(FancyCosmetic cosmetic) {
        if (cosmetic == null) {
            sendMessage(PartlyFancy.getValue("message.cosmetic.invalid"));
        } else {
            cosmetic.start();
        }
    }

    public void stopCosmetic(FancyCosmetic cosmetic) {
        if (cosmetic == null) {
            sendMessage(PartlyFancy.getValue("message.cosmetic.turn-off-inactive"));
        } else {
            cosmetic.stop();
        }
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
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

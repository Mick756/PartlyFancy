package fancy;

import com.sun.istack.internal.NotNull;
import fancy.cosmetics.FancyCosmetic;
import fancy.cosmetics.Gadget;
import fancy.cosmetics.Particle;
import fancy.cosmetics.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FancyPlayer {

    private UUID playerUUID;

    // Stored objects of cosmetics
    public Particle particleEffect;
    public Gadget gadget;
    public Pet pet;

    //Settings
    public boolean canSeeOwnParticles;

    /**
     * PartlyFancy's player object
     * @param p Player to create object for
     */
    private FancyPlayer(@NotNull Player p) {
        this.particleEffect = null;
        this.gadget = null;
        this.pet = null;
        this.playerUUID = p.getUniqueId();
        this.canSeeOwnParticles = true;
        PartlyFancy.getFancyPlayers().put(this.playerUUID, this);
    }

    /**
     * Start a cosmetic.
     * @param cosmetic A new FancyCosmetic
     */
    public void startParticle(FancyCosmetic cosmetic, boolean msg) {
        if (this.particleEffect == null) {
            if (cosmetic == null) {
                if (msg) sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.invalid", "%player%-" + getPlayer().getDisplayName()));
            } else {
                this.particleEffect = ((Particle) cosmetic);
                this.particleEffect.start();
            }
        } else {
            if (msg) sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.already-in-use", "%player%-" + getPlayer().getDisplayName()));
        }
    }

    /**
     * Stop a cosmetic.
     */
    public void stopParticle(boolean msg) {
        if (this.particleEffect == null) {
            if (msg) sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.turn-off-inactive", "%player%-" + getPlayer().getDisplayName()));
        } else {
            this.particleEffect.stop();
            this.particleEffect = null;
        }
    }

    public void stopAll() {

        stopParticle(false);

        sendMessage(true, "&cYou turned off all your cosmetic abilities!");
    }

    public void sendMessage(boolean prefix, String message) {
        getPlayer().sendMessage((prefix ? PartlyFancy.getPrefix() : "") + ChatColor.translateAlternateColorCodes('&', message));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUUID);
    }

    /**
     * Retrieve a Fancy Player or make a new one if the instance doesn't exist.
     * @param player Player to create the new instance around.
     * @return new FancyPlayer object. Will return null if player is found to be offline.
     */
    public static FancyPlayer getFancyPlayer(Player player) {
        return getFancyPlayer(player.getUniqueId());
    }

    /**
     * Retrieve a Fancy Player or make a new one if the instance doesn't exist.
     * @param uuid UUID of the player to create the new instance around.
     * @return new FancyPlayer object. Will return null if player is found to be offline.
     */
    public static FancyPlayer getFancyPlayer(UUID uuid) {

        if (PartlyFancy.getFancyPlayers().containsKey(uuid)) {
            return PartlyFancy.getFancyPlayers().get(uuid);
        } else {

            // Check if a new FancyPlayer can be created
            if (Bukkit.getPlayer(uuid) == null) {
                PartlyFancy.sendConsoleMessage("&cPlayer with UUID of " + uuid.toString() + " was not found to be online.");
                return null;
            } else {

                try {
                    return new FancyPlayer(Bukkit.getPlayer(uuid));
                } catch (NullPointerException ex) {
                    PartlyFancy.sendConsoleMessage("&cPlayer with UUID of " + uuid.toString() + " was not found to be online.");
                    return null;
                }
            }
        }
    }
}

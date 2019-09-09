package fancy;

import com.sun.istack.internal.NotNull;
import fancy.cosmetics.FancyCosmetic;
import fancy.cosmetics.Gadget;
import fancy.cosmetics.Particle;
import fancy.cosmetics.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class FancyPlayer {

    private UUID playerUUID;

    // Stored objects of cosmetics
    private Particle particleEffect = null;
    private Gadget gadget = null;
    private Pet pet = null;

    /**
     * PartlyFancy's player object
     * @param p Player to create object for
     */
    private FancyPlayer(@NotNull Player p) {
        this.playerUUID = p.getUniqueId();
    }

    /**
     * Start a cosmetic.
     * @param cosmetic A new FancyCosmetic
     */
    public void startCosmetic(FancyCosmetic cosmetic) {
        if (cosmetic == null) {
            sendMessage(PartlyFancy.getValue("message.cosmetic.invalid"));
        } else {
            cosmetic.start();
        }
    }

    /**
     * Stop a cosmetic.
     * @param cosmetic An active cosmetic of the FancyPlayer
     */
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


    /*
    Getters, Setters, and Checks Below
     */

    public Particle getParticleEffect() {
        return particleEffect;
    }

    public void setParticleEffect(Particle particleEffect) {
        this.particleEffect = particleEffect;
    }

    public boolean hasParticleEffect() {
        return (particleEffect == null);
    }

    public Gadget getGadget() {
        return gadget;
    }

    public void setGadget(Gadget gadget) {
        this.gadget = gadget;
    }

    public boolean hasGadget() {
        return (gadget == null);
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public boolean hasPet() {
        return (pet == null);
    }

}

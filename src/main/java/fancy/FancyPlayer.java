package fancy;

import lombok.Getter;
import fancy.cosmetics.FancyCosmetic;
import fancy.cosmetics.Gadget;
import fancy.cosmetics.Particle;
import fancy.cosmetics.Pet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FancyPlayer {

    private final @Getter UUID playerUUID;
    
    private @Getter Particle particleEffect;
    private @Getter Gadget gadget;
    private @Getter Pet pet;
    
    private FancyPlayer(Player p) {
        this.particleEffect = null;
        this.gadget = null;
        this.pet = null;
        this.playerUUID = p.getUniqueId();
        PartlyFancy.getInstance().getFancyPlayers().put(this.playerUUID, this);
    }
    
    public void startCosmetic(FancyCosmetic cosmetic, boolean msg) {
        if (cosmetic == null && msg) {
            sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.invalid", "%player%-" + getPlayer().getDisplayName()));
            return;
        }
        boolean activated = true;
        boolean alreadyInUse = true;
        
        if (cosmetic instanceof Particle) {
            if (this.particleEffect == null) {
                alreadyInUse = false;
                this.particleEffect = (Particle) cosmetic;
                activated = this.particleEffect.start();
            }
        } else if (cosmetic instanceof Gadget) {
            if (this.gadget == null) {
                alreadyInUse = false;
                this.gadget = (Gadget) cosmetic;
                activated = this.gadget.start();
            }
        } else if (cosmetic instanceof Pet) {
            if (this.pet == null) {
                alreadyInUse = false;
                this.pet = (Pet) cosmetic;
                activated = this.pet.start();
            }
        }
        if (msg) {
            if (alreadyInUse) {
                sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.already-in-use", "%player%-" + getPlayer().getDisplayName()));
                return;
            }
            if (activated) {
                sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.activated", "%cosmetic%-" + cosmetic.name()));
                this.getPlayer().playSound(this.getPlayer().getLocation(), PartlyFancy.getSound("sound.cosmetic.activated"), 0.5f, 1f);
            }
        }
    }

    public void stopParticle(boolean msg) {
        if (this.particleEffect == null) {
            if (msg) {
                sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.turn-off-inactive", "%player%-" + getPlayer().getDisplayName()));
            }
        } else {
            this.particleEffect.stop();
            this.particleEffect = null;
        }
    }

    public void stopGadget(boolean msg) {
        if (this.gadget == null) {
            if (msg) {
                sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.turn-off-inactive", "%player%-" + getPlayer().getDisplayName()));
            }
        } else {
            this.gadget.stop();
            this.gadget = null;
        }
    }

    public void stopAll(boolean msg) {
        stopParticle(false);
        stopGadget(false);
        //stopPet(false);
        if (msg) {
            sendMessage(true, PartlyFancy.getStringValue("message.cosmetic.turn-off-all", "%player%-" + getPlayer().getDisplayName()));
        }
    }

    public void sendMessage(boolean prefix, String message) {
        getPlayer().sendMessage((prefix ? PartlyFancy.getPrefix() : "") + ChatColor.translateAlternateColorCodes('&', message));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.playerUUID);
    }
    
    public static FancyPlayer getFancyPlayer(Player player) {
        return getFancyPlayer(player.getUniqueId());
    }
    
    public static FancyPlayer getFancyPlayer(UUID uuid) {
        if (PartlyFancy.getInstance().getFancyPlayers().containsKey(uuid)) {
            return PartlyFancy.getInstance().getFancyPlayers().get(uuid);
        } else {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) {
                PartlyFancy.sendConsoleMessage("&cPlayer with UUID of " + uuid.toString() + " was not found to be online.");
                return null;
            } else {
                try {
                    return new FancyPlayer(player);
                } catch (NullPointerException ex) {
                    PartlyFancy.sendConsoleMessage("&cPlayer with UUID of " + uuid.toString() + " was not found to be online.");
                    return null;
                }
            }
        }
    }
}

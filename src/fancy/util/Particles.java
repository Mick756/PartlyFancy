package fancy.util;

import com.sun.istack.internal.NotNull;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

public enum Particles {

    FLAMES(new String[] { "flame", "fire" }, Particle.FLAME, false, false),

    HAPPY_VILLAGER(new String[] { "happyvillager", "happy_villager", "happy-villager", "happy", "greensparks" }, Particle.VILLAGER_HAPPY, false, false),

    ANGRY_VILLAGER(new String[] { "angryvillager", "angry_villager", "angry-villager", "angry" }, Particle.VILLAGER_ANGRY, false, false),

    REDSTONE(new String[] { "redstone", "dust" }, Particle.REDSTONE, true, false),

    CRITS(new String[] { "crit" }, Particle.CRIT, false, false),

    HEARTS(new String[] { "heart" }, Particle.HEART, false, true),

    LAVA_DROPS(new String[] { "lava", "lava-drops", "lavadrops", "lavadrop", "lava-drop" }, Particle.DRIP_LAVA, false, true),

    MAGIC_CRITS(new String[] { "magicscrits", "magic-crits", "magic_crits", "mcrits" }, Particle.CRIT_MAGIC, false, false),

    NOTES(new String[] { "note", "noteblock" }, Particle.NOTE, true, false),

    SLIME(null, Particle.SLIME, false, false),

    SMOKE(null, Particle.SMOKE_NORMAL, false, false),

    SPARKS(new String[] { "sparks", "fireworksparks", "firework", "fireworks", "spark" }, Particle.FIREWORKS_SPARK, false, true),

    WATER_DROPS(new String[] { "water", "water-drops", "waterdrops", "waterdrop", "water-drop" }, Particle.DRIP_WATER, false, true);


    public String[] altNames;
    public Particle effect;
    public boolean colorable;
    public boolean spam;

    /**
     * Bridge between ParticleEffect and the PartlyFancy display
     * @param altNames Alternate names for commands
     * @param effect The respective effect from ParticleEffect
     * @param colorable If the particle has different colors to appear as
     * @param spam If the particle is either large or widely disbursed
     */
    Particles(@Nullable String[] altNames, @NotNull Particle effect, @NotNull boolean colorable, @NotNull boolean spam) {
        this.altNames = altNames;
        this.effect = effect;
        this.colorable = colorable;
        this.spam = spam;
    }

    /**
     * Display the particle at a specific location
     * @param loc Location to display particle
     * @param amount Amount of the particle to display. (If particle is spammy, amount fixed to 2)
     */
    public void display(Location loc, int amount) {
        loc.getWorld().spawnParticle(this.effect, loc, amount, 0 , 0, 0, null);
    }

    /**
     * Get Particle from altName
     * @param altName Alternate name to search for
     * @return the Particle if found, or null.
     */
    public Particles getParticle(String altName) {
        for (Particles p : values()) {
            if (p.name().equalsIgnoreCase(altName)) return p;
            for (String alt : p.altNames) {
                if (altName.equalsIgnoreCase(alt)) {
                    return p;
                }
            }
        }
        return null;
    }
}

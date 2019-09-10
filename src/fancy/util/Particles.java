package fancy.util;

import com.sun.istack.internal.NotNull;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

public enum Particles {

    FLAMES(new String[] { "flame", "flames", "fire" }, ParticleEffect.FLAME, false, false),

    HAPPY_VILLAGER(new String[] { "happyvillager", "happy_villager", "happy-villager", "happy", "greensparks" }, ParticleEffect.HAPPY_VILLAGER, false, false),

    ANGRY_VILLAGER(new String[] { "angryvillager", "angry_villager", "angry-villager", "angry" }, ParticleEffect.ANGRY_VILLAGER, false, false),

    REDSTONE(new String[] { "redstone", "dust" }, ParticleEffect.REDSTONE, true, false),

    CRITS(new String[] { "crit", "crits" }, ParticleEffect.CRITS, false, false),

    HEARTS(new String[] { "hearts", "heart" }, ParticleEffect.HEARTS, false, true),

    LAVA_DROPS(new String[] { "lava-drops", "lavadrops", "lava_drops", "lavadrop", "lava-drop", "lava_drops" }, ParticleEffect.LAVA_DROPS, false, true),

    MAGIC_CRITS(new String[] { "magicscrits", "magic-crits", "magic_crits", "mcrits" }, ParticleEffect.MAGIC_CRITS, false, false),

    NOTES(new String[] { "note", "notes", "noteblock" }, ParticleEffect.NOTE, true, false),

    SLIME(new String[] { "slime" }, ParticleEffect.SLIME, false, false),

    SMOKE(new String[] { "smoke" }, ParticleEffect.NORMAL_SMOKE, false, false),

    SPARKS(new String[] { "sparks", "fireworksparks", "firework", "fireworks", "spark" }, ParticleEffect.SPARKS, false, true),

    WATER_DROPS(new String[] { "water-drops", "waterdrops", "water_drops", "waterdrop", "water-drop", "water_drops" }, ParticleEffect.WATER_DROPS, false, true);


    public String[] altNames;
    public ParticleEffect effect;
    public boolean colorable;
    public boolean spam;

    /**
     * Bridge between ParticleEffect and the PartlyFancy display
     * @param altNames Alternate names for commands
     * @param effect The respective effect from ParticleEffect
     * @param colorable If the particle has different colors to appear as
     * @param spam If the particle is either large or widely disbursed
     */
    Particles(@Nullable String[] altNames, @NotNull ParticleEffect effect, @NotNull boolean colorable, @NotNull boolean spam) {
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
        this.effect.display(0f, 0f, 0f, (this.colorable ? 1 : 0), (this.spam ? 2 : amount), loc, 64d);
    }

    /**
     * Get Particle from altName
     * @param altName Alternate name to search for
     * @return the Particle if found, or null.
     */
    public Particles getParticle(String altName) {
        for (Particles p : values()) {
            for (String alt : p.altNames) {
                if (altName.equalsIgnoreCase(alt)) {
                    return p;
                }
            }
        }
        return null;
    }
}

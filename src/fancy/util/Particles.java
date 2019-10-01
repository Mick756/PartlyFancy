package fancy.util;

import com.sun.istack.internal.NotNull;
import fancy.FancyPlayer;
import fancy.util.particlelib.ParticleEffect;
import fancy.util.particlelib.data.color.NoteColor;
import fancy.util.particlelib.data.color.RegularColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toCollection;


public enum Particles {

    FLAMES(new String[] { "flame", "fire" }, ParticleEffect.FLAME, false, false, Material.BLAZE_POWDER, "&7Not angry, just heated."),

    HAPPY_VILLAGER(new String[] { "happyvillager", "happy-villager", "happy", "greensparks" }, ParticleEffect.VILLAGER_HAPPY, false, false, Material.EMERALD, "&7Show how happy you are."),

    ANGRY_VILLAGER(new String[] { "angryvillager", "angry-villager", "angry" }, ParticleEffect.VILLAGER_ANGRY, false, false, Material.MAGMA_CREAM, "&7Or how angry..."),

    REDSTONE(new String[] { "redstone", "dust" }, ParticleEffect.REDSTONE, true, false, Material.REDSTONE, "&7A burst of color."),

    MOB_SPELL(new String[] { "spell", "bubbles" }, ParticleEffect.SPELL_MOB, true, false, Material.POTION, "&7Like a rainbow bubble bath."),

//    CRITS(new String[] { "crit" }, ParticleEffect.CRIT, false, false, Material.WOODEN_SWORD, "&7Wow, don't be so critical."),

    HEARTS(new String[] { "heart" }, ParticleEffect.HEART, false, true, Material.ARROW, "&7You give me love kernels."),

    LAVA_DROPS(new String[] { "lava", "lava-drops", "lavadrops", "lavadrop", "lava-drop" }, ParticleEffect.DRIP_LAVA, false, true, Material.LAVA_BUCKET, "&7Droplet like its hot."),

    MAGIC_CRITS(new String[] { "magicscrits", "magic-crits", "magic_crits", "mcrits" }, ParticleEffect.CRIT_MAGIC, false, false, Material.DIAMOND_SWORD, "&7How shiny these are."),

//    NOTES(new String[] { "note", "noteblock" }, ParticleEffect.NOTE, true, false, Material.NOTE_BLOCK, "&7You like.. music?"),

//    SLIME(null, ParticleEffect.SLIME, false, false, Material.SLIME_BALL, "&7Not sticky, but slimy."),

//    SMOKE(null, ParticleEffect.SMOKE_NORMAL, false, false, Material.GUNPOWDER, "&7This kind isn't bad for you."),

    SPARKS(new String[] { "sparks", "fireworksparks", "firework", "fireworks", "spark" }, ParticleEffect.FIREWORKS_SPARK, false, true, Material.FIREWORK_ROCKET, "&7You're not shorting out."),

    WATER_DROPS(new String[] { "water", "water-drops", "waterdrops", "waterdrop", "water-drop" }, ParticleEffect.DRIP_WATER, false, true, Material.WATER_BUCKET, "&7Water from above... so rain?");


    public String[] altNames;
    public ParticleEffect effect;
    public boolean colorable;
    public boolean spam;
    public Material item;
    public String description;

    /**
     * Bridge between ParticleEffect and the PartlyFancy display
     * @param altNames Alternate names for commands
     * @param effect The respective effect from ParticleEffect
     * @param colorable If the particle has different colors to appear as
     * @param spam If the particle is either large or widely disbursed
     */
    Particles(@Nullable String[] altNames, @NotNull ParticleEffect effect, @NotNull boolean colorable, @NotNull boolean spam, @NotNull Material item, @NotNull String description) {
        this.altNames = altNames;
        this.effect = effect;
        this.colorable = colorable;
        this.spam = spam;
        this.item = item;
        this.description = description;
    }

    /**
     * Display the particle at a specific location
     * @param fp FancyPlayer attached to the particle display event
     * @param loc Location to display particle
     * @param amt Amount of the particle to display. (If particle is spammy, amount fixed to 2)
     */
    public void display(FancyPlayer fp, Location loc, int amt) {
        int amount = (this.spam ? 2 : amt);

        List<Player> playersToShowTo = Bukkit.getOnlinePlayers().stream().collect(toCollection(ArrayList::new));

        if (!fp.canSeeOwnParticles) {
            playersToShowTo.remove(fp.getPlayer());
        }


        Random r = FancyUtil.RANDOM;
        if (this.colorable && this.effect != ParticleEffect.NOTE) {
            RegularColor c = new RegularColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            this.effect.display(loc, 0, 0, 0, 0, amount, c, playersToShowTo);
        } else if (this.colorable && this.effect == ParticleEffect.NOTE) {
            this.effect.display(loc, 0, 0, 0, 0, amount, NoteColor.random(), playersToShowTo);
        } else {
            this.effect.display(loc, playersToShowTo);
        }
    }

    /**
     * Display the particle at a specific location
     * @param loc Location to display particle
     * @param amt Amount of the particle to display. (If particle is spammy, amount fixed to 2)
     */
    public void display(Location loc, int amt) {
        int amount = (this.spam ? 2 : amt);
        Random r = FancyUtil.RANDOM;
        if (this.colorable && this.effect != ParticleEffect.NOTE) {
            RegularColor c = new RegularColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            this.effect.display(loc, 0, 0, 0, 0, amount, c);
        } else if (this.colorable && this.effect == ParticleEffect.NOTE) {
            this.effect.display(loc, 0, 0, 0, 0, amount, NoteColor.random());
        } else {
            this.effect.display(loc);
        }
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

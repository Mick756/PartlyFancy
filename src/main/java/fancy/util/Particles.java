package fancy.util;

import fancy.PartlyFancy;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.Random;

public enum Particles {

    FLAMES(new String[] {"flame", "fire"}, Particle.FLAME, false, false, Material.BLAZE_POWDER, "&7Not angry, just heated.", " ", " ", "&aClick to select!"),
    HAPPY_VILLAGER(new String[] {"happyvillager", "happy-villager", "happy", "greensparks"}, Particle.VILLAGER_HAPPY, false, false, Material.EMERALD, "&7Show how happy you are.", " ", " ", "&aClick to select!"),
    ANGRY_VILLAGER(new String[] {"angryvillager", "angry-villager", "angry"}, Particle.VILLAGER_ANGRY, false, false, Material.MAGMA_CREAM, "&7Or how angry...", " ", " ", "&aClick to select!"),
    REDSTONE(new String[] {"redstone", "dust"}, Particle.REDSTONE, true, false, Material.REDSTONE, "&7A burst of color.", " ", " ", "&aClick to select!"),
    MOB_SPELL(new String[] {"spell", "bubbles"}, Particle.SPELL_MOB, true, false, Material.POTION, "&7Like a rainbow bubble bath.", " ", " ", "&aClick to select!"),
    CRITS(new String[] {"crit"}, Particle.CRIT, false, false, Material.WOODEN_SWORD, "&7Wow, don't be so critical.", " ", " ", "&aClick to select!"),
    HEARTS(new String[] {"heart"}, Particle.HEART, false, true, Material.ARROW, "&7You give me love kernels.", " ", " ", "&aClick to select!"),
    LAVA_DROPS(new String[] {"lava", "lava-drops", "lavadrops", "lavadrop", "lava-drop"}, Particle.DRIP_LAVA, false, true, Material.LAVA_BUCKET, "&7Droplet like its hot.", " ", " ", "&aClick to select!"),
    MAGIC_CRITS(new String[] {"magicscrits", "magic-crits", "magic_crits", "mcrits"}, Particle.CRIT_MAGIC, false, false, Material.DIAMOND_SWORD, "&7How shiny these are.", " ", " ", "&aClick to select!"),
    NOTES(new String[] {"note", "noteblock"}, Particle.NOTE, true, false, Material.NOTE_BLOCK, "&7You like.. music?", " ", " ", "&aClick to select!"),
    SLIME(null, Particle.SLIME, false, false, Material.SLIME_BALL, "&7Not sticky, but slimy.", " ", " ", "&aClick to select!"),
    SMOKE(null, Particle.SMOKE_NORMAL, false, false, Material.GUNPOWDER, "&7This kind isn't bad for you.", " ", " ", "&aClick to select!"),
    SPARKS(new String[] {"sparks", "fireworksparks", "firework", "fireworks", "spark"}, Particle.FIREWORKS_SPARK, false, true, Material.FIREWORK_ROCKET, "&7You're not shorting out.", " ", " ", "&aClick to select!"),
    WATER_DROPS(new String[] {"water", "water-drops", "waterdrops", "waterdrop", "water-drop"}, Particle.DRIP_WATER, false, true, Material.WATER_BUCKET, "&7Water from above... so rain?", " ", " ", "&aClick to select!");

    private final @Getter String[] altNames;
    private final @Getter Particle effect;
    private final @Getter boolean color;
    private final @Getter boolean spam;
    private final @Getter Material item;
    private final @Getter String[] description;
    
    Particles(String[] altNames, Particle effect, boolean color, boolean spam, Material item, String... description) {
        this.altNames = altNames;
        this.effect = effect;
        this.color = color;
        this.spam = spam;
        this.item = item;
        this.description = description;
    }

    public void display(Location loc) {
        Object data = null;
        
        if (color && this.effect.equals(Particle.REDSTONE)) {
            Random r = PartlyFancy.getRandom();
            
            data = new Particle.DustOptions(Color.fromBGR(r.nextInt(255), r.nextInt(255), r.nextInt(255)), 1);
        }
        
        if (this.effect.equals(Particle.NOTE)) {
            loc.getWorld().spawnParticle(Particle.NOTE, loc.getX(),loc.getY(),loc.getZ(), 0, PartlyFancy.getRandom().nextInt(24), 0.0d, 0.0d, 0.1d);
        } else {
            loc.getWorld().spawnParticle(this.effect, loc, 0, 0, 0, 0, data);
        }
    }

    public static Particles getParticle(String altName) {
        
        for (Particles p : values()) {
            if (p.name().equalsIgnoreCase(altName)) return p;
            if (p.altNames == null) continue;
            
            for (String alt : p.altNames) {
                if (altName.equalsIgnoreCase(alt)) {
                    return p;
                }
            }
        }
        
        return null;
    }
}

package fancy.util;

import fancy.PartlyFancy;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.Random;

public enum Particles {

    FLAMES(Particle.FLAME, false, false, Material.BLAZE_POWDER, "&7Not angry, just heated.", " ", " ", "&aClick to select!"),
    HAPPY_VILLAGER(Particle.VILLAGER_HAPPY, false, false, Material.EMERALD, "&7Show how happy you are.", " ", " ", "&aClick to select!"),
    ANGRY_VILLAGER(Particle.VILLAGER_ANGRY, false, false, Material.MAGMA_CREAM, "&7Or how angry...", " ", " ", "&aClick to select!"),
    REDSTONE(Particle.REDSTONE, true, false, Material.REDSTONE, "&7A burst of color.", " ", " ", "&aClick to select!"),
    MOB_SPELL(Particle.SPELL_MOB, true, false, Material.POTION, "&7Like a rainbow bubble bath.", " ", " ", "&aClick to select!"),
    CRITS(Particle.CRIT, false, false, Material.WOODEN_SWORD, "&7Wow, don't be so critical.", " ", " ", "&aClick to select!"),
    HEARTS(Particle.HEART, false, true, Material.ARROW, "&7You give me love kernels.", " ", " ", "&aClick to select!"),
    LAVA_DROPS(Particle.DRIP_LAVA, false, true, Material.LAVA_BUCKET, "&7Droplet like its hot.", " ", " ", "&aClick to select!"),
    MAGIC_CRITS(Particle.CRIT_MAGIC, false, false, Material.DIAMOND_SWORD, "&7How shiny these are.", " ", " ", "&aClick to select!"),
    NOTES(Particle.NOTE, true, false, Material.NOTE_BLOCK, "&7You like.. music?", " ", " ", "&aClick to select!"),
    SLIME(Particle.SLIME, false, false, Material.SLIME_BALL, "&7Not sticky, but slimy.", " ", " ", "&aClick to select!"),
    SMOKE(Particle.SMOKE_NORMAL, false, false, Material.GUNPOWDER, "&7This kind isn't bad for you.", " ", " ", "&aClick to select!"),
    SPARKS(Particle.FIREWORKS_SPARK, false, true, Material.FIREWORK_ROCKET, "&7You're not shorting out.", " ", " ", "&aClick to select!"),
    WATER_DROPS(Particle.DRIP_WATER, false, true, Material.WATER_BUCKET, "&7Water from above... so rain?", " ", " ", "&aClick to select!");

    private final @Getter Particle effect;
    private final @Getter boolean color;
    private final @Getter boolean spam;
    private final @Getter Material item;
    private final @Getter String[] description;
    
    Particles(Particle effect, boolean color, boolean spam, Material item, String... description) {
        this.effect = effect;
        this.color = color;
        this.spam = spam;
        this.item = item;
        this.description = description;
    }

    public void display(Location loc) {
        if (loc.getWorld() == null) return;
        
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
    
}

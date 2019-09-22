package fancy.cosmetics.particles;

import com.sun.istack.internal.NotNull;
import fancy.PartlyFancy;
import fancy.cosmetics.Particle;
import fancy.util.FancyUtil;
import fancy.util.Particles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AuraParticle implements Particle {

    private boolean enabled;
    private Player player;
    private Particles[] effects;
    private int i;

    /**
     * An aura affect is a basic ring over a player's head
     * @param player Player to attach effect to
     * @param effects Particles to be displayed
     */
    public AuraParticle(@NotNull Player player, @NotNull Particles... effects) {
        this.enabled = true;
        this.player = player;
        this.effects = effects;
        this.i = 0;
    }

    @Override
    public Particles[] getParticles() {
        return this.effects;
    }

    @Override
    public ParticleType getType() {
        return ParticleType.AURA;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String name() {
        return "Aura Particle";
    }

    @Override
    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (!getPlayer().isOnline() || !enabled) {
                    stop();
                    return;
                }

                double radius = 0.7D;
                double amount = radius * 30.0D;
                double inc = (Math.PI * 4) / amount;
                double angle = i * inc;

                double x = radius * Math.cos(angle);
                double z = radius * Math.sin(angle);

                double y = 2.35;

                while (y > 0) {

                    Location loc = getPlayer().getLocation().add(new Vector(x, y, z));

                    for (Particles particle : getParticles()) {
                        if (particle != null) {
                            particle.display(loc, 5);
                        }
                    }

                    y -= .75;
                }

                if (i >= amount - 1.0D) {
                    i = 0;
                    y = 2.35;
                } else i += 1;

            }
        }.runTaskTimer(PartlyFancy.getInstance(), 10, interval());
    }

    @Override
    public int interval() {
        return 2;
    }

    @Override
    public void stop() {
        this.enabled = false;
    }

    public static ItemStack item() {

        return FancyUtil.createItemStack(
                Material.GOLDEN_CARROT,
                1,
                "&bAura Particle",
                null,
                "&7Be covered by your own power.");

    }
}

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

public class OrbParticle implements Particle {

    private boolean enabled;
    private Player player;
    private Particles[] effects;

    /**
     * A crown affect is a basic ring over a player's head
     * @param player Player to attach effect to
     * @param effects Particles to be displayed
     */
    public OrbParticle(@NotNull Player player, @NotNull Particles... effects) {
        this.enabled = true;
        this.player = player;
        this.effects = effects;
    }

    @Override
    public Particles[] getParticles() {
        return this.effects;
    }

    @Override
    public ParticleType getType() {
        return ParticleType.ORB;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String name() {
        return "Scan Particle";
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

                double radius = 1.2D;
                double amount = radius * 30.0D;
                double inc = (Math.PI * 4) / amount;

                for (int i = 0; i < 180; i++) {

                    double angle = i * inc;

                    double x = radius * Math.cos(angle);
                    double z = radius * Math.sin(angle);

                    Vector v = FancyUtil.rotateVectorDegree(new Vector(x, z + 1, 0), (i * 18));
                    Location loc = getPlayer().getLocation().add(v);

                    for (Particles particle : getParticles()) {
                        if (particle != null) {
                            particle.display(loc, 5);
                        }
                    }
                }

            }
        }.runTaskTimer(PartlyFancy.getInstance(), 10, interval());
    }

    @Override
    public int interval() {
        return 15;
    }

    @Override
    public void stop() {
        this.enabled = false;
    }

    public static ItemStack item() {

        return FancyUtil.createItemStack(
                Material.REDSTONE,
                1,
                "&bOrb Particle",
                null,
                "&7Just to make sure...");

    }
}

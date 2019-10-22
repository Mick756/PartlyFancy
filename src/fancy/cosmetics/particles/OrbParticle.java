package fancy.cosmetics.particles;

import com.sun.istack.internal.NotNull;
import fancy.FancyPlayer;
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

import java.util.ArrayList;
import java.util.List;

public class OrbParticle implements Particle {

    public static List<FancyPlayer> orbParticleUsers = new ArrayList<>();


    private static double radius = 1.2D;
    private static double amount = radius * 30.0D;
    private static double inc = (Math.PI * 4) / amount;
    private static int interval = 15;

    static {
        new BukkitRunnable() {

            @Override
            public void run() {

                if (orbParticleUsers.size() > 0) {

                    for (FancyPlayer fp : orbParticleUsers) {

                        fp.particleEffect.run(-1);

                    }
                }
            }

        }.runTaskTimer(PartlyFancy.getInstance(), 0, interval);
    }

    private Player player;
    private Particles[] effects;

    /**
     * A crown affect is a basic ring over a player's head
     * @param player Player to attach effect to
     * @param effects Particles to be displayed
     */
    public OrbParticle(@NotNull Player player, @NotNull Particles... effects) {
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

        orbParticleUsers.add(FancyPlayer.getFancyPlayer(this.getPlayer()));

    }

    @Override
    public void run(double... step) {


        for (int i = 0; i < 180; i++) {

            double angle = i * inc;

            double x = radius * Math.cos(angle);
            double z = radius * Math.sin(angle);

            Vector v = FancyUtil.rotateVectorDegree(new Vector(x, z + 1, 0), (i * 18)).multiply(1.3F);
            Location loc = getPlayer().getLocation().add(v);

            for (Particles particle : getParticles()) {
                if (particle != null) {
                    particle.display(FancyPlayer.getFancyPlayer(this.getPlayer()), loc, 5);
                }
            }
        }
    }

    @Override
    public void stop() {

        orbParticleUsers.remove(FancyPlayer.getFancyPlayer(this.getPlayer()));

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

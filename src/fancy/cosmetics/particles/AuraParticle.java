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

public class AuraParticle implements Particle {

    public static List<FancyPlayer> auraParticleUsers = new ArrayList<>();

    private static int interval = 3;
    private static double radius = 0.7D;
    private static double amount = radius * 30.0D;
    private static double inc = (Math.PI * 4) / amount;
    private static int i = 0;

    static {
        new BukkitRunnable() {

            @Override
            public void run() {

                if (auraParticleUsers.size() > 0) {

                    for (FancyPlayer fp : auraParticleUsers) {

                        fp.particleEffect.run(i);

                    }
                }

                if (i >= amount - 1.0D) {
                    i = 0;
                } else i += 1;
            }

        }.runTaskTimer(PartlyFancy.getInstance(), 0, interval);
    }

    private Player player;
    private Particles[] effects;

    /**
     * An aura affect is a basic ring over a player's head
     * @param player Player to attach effect to
     * @param effects Particles to be displayed
     */
    public AuraParticle(@NotNull Player player, @NotNull Particles... effects) {
        this.player = player;
        this.effects = effects;
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

        auraParticleUsers.add(FancyPlayer.getFancyPlayer(this.getPlayer()));

    }

    @Override
    public void run(double... step) {

        double angle = step[0] * inc;

        double x = radius * Math.cos(angle);
        double z = radius * Math.sin(angle);

        double y = 2.35;

        while (y > 0) {

            Location loc = getPlayer().getLocation().add(new Vector(x, y, z));

            for (Particles particle : getParticles()) {
                if (particle != null) {
                    particle.display(FancyPlayer.getFancyPlayer(this.getPlayer()), loc, 5);
                }
            }

            y -= .75;
        }
    }


    @Override
    public void stop() {

        auraParticleUsers.remove(FancyPlayer.getFancyPlayer(this.getPlayer()));

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

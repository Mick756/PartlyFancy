package fancy.cosmetics.particles;

import fancy.FancyPlayer;
import fancy.cosmetics.Particle;
import fancy.util.CosmeticUtil;
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
                        fp.getParticleEffect().run(-1);
                    }
                }
            }

        }.runTaskTimer(fancy.PartlyFancy.getInstance(), 0, interval);
    }

    private Player player;
    private Particles[] effects;

    public OrbParticle(Player player, Particles... effects) {
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
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String name() {
        return "Orb Particle";
    }

    @Override
    public boolean start() {
        orbParticleUsers.add(FancyPlayer.getFancyPlayer(this.getPlayer()));
        return true;
    }

    @Override
    public void run(double... step) {
        for (int i = 0; i < 180; i++) {
            double angle = i * inc;
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            Vector v = CosmeticUtil.rotateVectorDegree(new Vector(x, y + 0.75, 0), (i * 18)).multiply(1.3F);
            Location loc = getPlayer().getLocation().add(v);
            for (Particles particle : getParticles()) {
                if (particle != null) {
                    particle.display(loc);
                }
            }
        }
    }

    @Override
    public boolean stop() {
        orbParticleUsers.remove(FancyPlayer.getFancyPlayer(this.getPlayer()));
        return true;
    }

    public static ItemStack item() {
        return CosmeticUtil.createItemStack(Material.REDSTONE, 1, "&bOrb Particle", null, "&7Just to make sure...");
    }
}

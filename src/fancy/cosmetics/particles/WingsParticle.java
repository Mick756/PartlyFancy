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

public class WingsParticle implements Particle  {

    private static boolean X = true;
    private static boolean o = false;
    private static boolean[][] shape = { 
            { o, X, X, X, o, o, X, o, o, o, X, o, o, X, X, X, o, o },
            { X, o, X, X, X, o, o, X, o, X, o, o, X, X, X, o, X, o },
            { o, o, o, X, X, X, o, X, o, X, o, X, X, X, o, o, o, o },
            { o, o, o, o, X, X, o, X, X, X, o, X, X, o, o, o, o, o },
            { o, o, o, o, o, X, X, X, X, X, X, X, o, o, o, o, o, o },
            { o, o, o, o, o, X, X, X, X, X, X, X, o, o, o, o, o, o },
            { o, o, X, o, o, X, X, o, o, o, X, X, o, o, X, o, o, o },
            { o, o, X, o, X, X, o, o, o, o, o, X, X, o, X, o, o, o },
            { o, o, o, X, X, o, o, o, o, o, o, X, X, X, o, o, o, o },
    };

    private boolean enabled;
    private Player player;
    private Particles[] effects;
    private int i;

    /**
     * A crown affect is a basic ring over a player's head
     * @param player Player to attach effect to
     * @param effects Particles to be displayed
     */
    public WingsParticle(@NotNull Player player, @NotNull Particles... effects) {
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
        return ParticleType.CROWN;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String name() {
        return "Crown Particle";
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

                Location location = getPlayer().getLocation();
                double space = 0.2D;
                double defX;
                double x = defX = location.getX() - space * shape[0].length / 2.0D + space;
                double y = location.clone().getY() + 2.0D;
                double angle = -(location.getYaw() + 180.0F) / 60.0F;

                angle += (location.getYaw() < -180.0F ? 3.25D : 2.985D);

                for (int i = 0; i < shape.length; i++) {

                    for (int j = 0; j < shape[i].length; j++) {

                        if (shape[i][j]) {
                            Location target = location.clone();
                            target.setX(x);
                            target.setY(y);

                            Vector v = target.toVector().subtract(location.toVector());
                            Vector v2 = FancyUtil.getBackVector(location);

                            v = FancyUtil.rotateAroundAxisY(v, angle);
                            v2.setY(0).multiply(-0.2D);
                            location.add(v);
                            location.add(v2);

                            for (Particles particle : getParticles()) {
                                if (particle != null) {
                                    particle.display(location, 5);
                                }
                            }

                            location.subtract(v2);
                            location.subtract(v);
                        }
                        x += space;
                    }
                    y -= space;
                    x = defX;

                }
            }
        }.runTaskTimer(PartlyFancy.getInstance(), 10, interval());
    }

    @Override
    public int interval() {
        return 16;
    }

    @Override
    public void stop() {
        this.enabled = false;
    }

    public static ItemStack item() {

        return FancyUtil.createItemStack(
                Material.ELYTRA,
                1,
                "&bWing Particle",
                null,
                "&7Sorry, you can't fly away with these.");

    }


}

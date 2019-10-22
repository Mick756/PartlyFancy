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

public class WingsParticle implements Particle  {

    public static List<FancyPlayer> wingParticleUsers = new ArrayList<>();

    private static int interval = 15;

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

    static {
        new BukkitRunnable() {

            @Override
            public void run() {

                if (wingParticleUsers.size() > 0) {

                    for (FancyPlayer fp : wingParticleUsers) {

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
    public WingsParticle(@NotNull Player player, @NotNull Particles... effects) {
        this.player = player;
        this.effects = effects;
    }

    @Override
    public Particles[] getParticles() {
        return this.effects;
    }

    @Override
    public ParticleType getType() {
        return ParticleType.WINGS;
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
    public void run(double... step) {
        Location loc = getPlayer().getLocation();
        double space = 0.2D;
        double defX;
        double x = defX = loc.getX() - space * shape[0].length / 2.0D + space;
        double y = loc.clone().getY() + 2.0D;
        double angle = -(loc.getYaw() + 180.0F) / 60.0F;

        angle += (loc.getYaw() < -180.0F ? 3.25D : 2.985D);

        for (int i = 0; i < shape.length; i++) {

            for (int j = 0; j < shape[i].length; j++) {

                if (shape[i][j]) {
                    Location target = loc.clone();
                    target.setX(x);
                    target.setY(y);

                    Vector v = target.toVector().subtract(loc.toVector());
                    Vector v2 = FancyUtil.getBackVector(loc);

                    v = FancyUtil.rotateAroundAxisY(v, angle);
                    v2.setY(0).multiply(-0.3D);
                    loc.add(v);
                    loc.add(v2);

                    for (Particles particle : getParticles()) {
                        if (particle != null) {
                            particle.display(FancyPlayer.getFancyPlayer(this.getPlayer()), loc, 5);
                        }
                    }

                    loc.subtract(v2);
                    loc.subtract(v);
                }
                x += space;
            }
            y -= space;
            x = defX;
        }
    }

    @Override
    public void start() {

        wingParticleUsers.add(FancyPlayer.getFancyPlayer(this.getPlayer()));

    }

    @Override
    public void stop() {

        wingParticleUsers.remove(FancyPlayer.getFancyPlayer(this.getPlayer()));

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

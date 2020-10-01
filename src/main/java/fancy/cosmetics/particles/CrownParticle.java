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

public class CrownParticle implements Particle {

    public static List<FancyPlayer> crownParticleUsers = new ArrayList<>();

    private static double radius = 0.7D;
    private static double amount = radius * 30.0D;
    private static double inc = (Math.PI * 2) / amount;
    private static int i = 0;
    private static int interval = 2;

    static {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (crownParticleUsers.size() > 0) {
                    for (FancyPlayer fp : crownParticleUsers) {
                        fp.getParticleEffect().run(i);
                    }
                }
                if (i >= amount - 1.0D) {
                    i = 0;
                } else i += 1;
            }
        }.runTaskTimer(fancy.PartlyFancy.getInstance(), 0, interval);
    }

    private Player player;
    private Particles[] effects;
    
    public CrownParticle(Player player, Particles... effects) {
        this.player = player;
        this.effects = effects;
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
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String name() {
        return "Crown Particle";
    }

    @Override
    public boolean start() {
        crownParticleUsers.add(FancyPlayer.getFancyPlayer(this.getPlayer()));
        return true;
    }

    @Override
    public boolean stop() {
        crownParticleUsers.remove(FancyPlayer.getFancyPlayer(this.getPlayer()));
        return true;
    }


    @Override
    public void run(double... step) {
        double angle = step[0] * inc;
        double x = radius * Math.cos(angle);
        double z = radius * Math.sin(angle);
        Vector v = new Vector(x, 2.25D, z);
        Location loc = getPlayer().getLocation().add(v);
        for (Particles particle : getParticles()) {
            if (particle != null) {
                if (particle.equals(Particles.ANGRY_VILLAGER)) loc.subtract(0, 0.5, 0);
                particle.display(loc);
            }
        }
        if (i >= (amount - 1.0D)) {
            i = 0;
        } else i++;
    }

    public static ItemStack item() {
        return CosmeticUtil.createItemStack(Material.DIAMOND, 1, "&bCrown Particle", null, "&7A simple, yet beyond fancy crown.");
    }
}

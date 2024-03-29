package fancy.cosmetics.particles;

import api.builders.ItemStackBuilder;
import fancy.FancyPlayer;
import fancy.PartlyFancy;
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

public class WingsParticle implements Particle  {

    public static List<FancyPlayer> wingParticleUsers = new ArrayList<>();

    private static final int interval = 7;

    private static final boolean X = true;
    private static final boolean o = false;
    private static final boolean[][] shape = {
            { o, o, X, X, o, o, o, X, o, X, o, o, o, X, X, o, o, o },
            { o, X, X, X, o, o, X, o, o, o, X, o, o, X, X, X, o, o },
            { o, X, X, X, o, o, X, o, o, o, X, o, o, X, X, X, o, o },
            { X, X, X, X, X, o, o, X, o, X, o, o, X, X, X, X, X, o },
            { X, X, o, X, X, X, o, X, o, X, o, X, X, X, o, X, X, o },
            { X, o, o, o, X, X, o, X, X, X, o, X, X, o, o, o, X, o },
            { o, o, o, o, o, X, X, X, X, X, X, X, o, o, o, o, o, o },
            { o, o, o, o, o, X, X, X, X, X, X, X, o, o, o, o, o, o },
            { o, o, o, o, o, X, X, X, o, X, X, X, o, o, o, o, o, o },
            { o, X, X, o, X, X, X, o, o, o, X, X, X, o, X, X, o, o },
            { o, o, X, X, X, X, o, o, o, o, o, X, X, X, X, o, o, o },
    };

    static {
        new BukkitRunnable() {
            
            @Override
            public void run() {
                if (wingParticleUsers.size() > 0) {
                    for (FancyPlayer fp : wingParticleUsers) {
                        fp.getParticleEffect().run(-1);
                    }
                }
            }
        }.runTaskTimer(PartlyFancy.getInstance(), 0, interval);
    }

    private Player player;
    private final Particles[] effects;
    
    public WingsParticle(Player player, Particles... effects) {
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
    public void setPlayer(Player player) {
        this.player = player;
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
        
        for (boolean[] booleans : shape) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    Location target = loc.clone();
                    
                    target.setX(x);
                    target.setY(y);
                    
                    Vector v = target.toVector().subtract(loc.toVector());
                    Vector v2 = CosmeticUtil.getBackVector(loc);
                    
                    v = v.rotateAroundY(angle);
                    v2.setY(0).multiply(-0.3D);
                    
                    loc.add(v);
                    loc.add(v2);
                    
                    for (Particles particle : getParticles()) {
                        if (particle != null) {
                            particle.display(loc);
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
    public boolean start() {
        wingParticleUsers.add(FancyPlayer.getFancyPlayer(this.getPlayer()));
        return true;
    }

    @Override
    public boolean stop() {
        wingParticleUsers.remove(FancyPlayer.getFancyPlayer(this.getPlayer()));
        return true;
    }

    public static ItemStack item() {

        return new ItemStackBuilder(Material.FIREWORK_ROCKET).setDisplayName("&bWing Particle").setLore("&7Sorry, you can't fly away with these.").build();
    }


}

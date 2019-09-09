package fancy.cosmetics.particles;

import com.sun.istack.internal.NotNull;
import fancy.cosmetics.Particle;
import fancy.util.FancyUtil;
import fancy.util.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CrownParticle implements Particle {

    private int taskId;
    private Player player;
    private ParticleEffect[] effects;

    /**
     * A crown affect is a basic ring over a player's head
     * @param player Player to attach effect to
     * @param effects Particles to be displayed
     */
    public CrownParticle(@NotNull Player player, @NotNull ParticleEffect... effects) {
        this.player = player;
        this.effects = effects;
    }

    @Override
    public ParticleEffect[] getParticles() {
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

    }

    @Override
    public void stop() {

    }

    @Override
    public ItemStack item() {

        // Diamond item with name Crown Particle
        return FancyUtil.createItemStack(
                "Crown Particle",
                Material.DIAMOND,
                1,
                null);

    }
}

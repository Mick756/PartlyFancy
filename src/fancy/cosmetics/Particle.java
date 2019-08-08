package fancy.cosmetics;

public interface Particle extends FancyCosmetic {

    org.bukkit.Particle[] getParticles();

    ParticleType getType();

    enum ParticleType {
        CROWN, SPIRAL, SPIKE, AURA;
    }
}

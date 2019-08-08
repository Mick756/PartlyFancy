package fancy.cosmetics;

import fancy.util.ParticleEffect;

public interface Particle extends FancyCosmetic {

    ParticleEffect[] getParticles();

    ParticleType getType();

    enum ParticleType {
        CROWN, SPIRAL, SPIKE, AURA;
    }
}

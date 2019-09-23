package fancy.cosmetics;

import fancy.util.Particles;

public interface Particle extends FancyCosmetic {

    Particles[] getParticles();

    ParticleType getType();

    int interval();

    enum ParticleType {
        CROWN, SPIRAL, SPIKE, AURA, SCAN, WINGS, ORB
    }
}

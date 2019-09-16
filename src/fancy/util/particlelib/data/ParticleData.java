package fancy.util.particlelib.data;

import fancy.util.particlelib.ParticleEffect;

public abstract class ParticleData {


    private ParticleEffect effect;

    public void setEffect(ParticleEffect effect) {
        this.effect = effect;
    }

    public abstract Object toNMSData();

    public ParticleEffect getEffect() {
        return effect;
    }
}

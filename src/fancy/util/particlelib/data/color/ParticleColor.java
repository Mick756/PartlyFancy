package fancy.util.particlelib.data.color;

import fancy.util.particlelib.data.ParticleData;

public abstract class ParticleColor extends ParticleData {

    private final int red;
    private final int green;
    private final int blue;

    ParticleColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public abstract Object toNMSData();

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }
}

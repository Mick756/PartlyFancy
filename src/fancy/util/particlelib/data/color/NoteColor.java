package fancy.util.particlelib.data.color;

import fancy.util.FancyUtil;
import fancy.util.particlelib.ParticleEffect;

public class NoteColor extends ParticleColor {

    public NoteColor(int note) {
        super(FancyUtil.getMaxOrMin(note, 24, 0), 0, 0);
        setEffect(ParticleEffect.NOTE);
    }

    @Override
    public void setEffect(ParticleEffect effect) {
        super.setEffect(ParticleEffect.NOTE);
    }

    @Override
    public float getRed() {
        return super.getRed() / 24f;
    }

    @Override
    public float getGreen() {
        return 0;
    }

    @Override
    public float getBlue() {
        return 0;
    }

    @Override
    public Object toNMSData() {
        return null;
    }

    public static NoteColor random() {
        return new NoteColor(FancyUtil.generateRandomInteger(0, 24));
    }

}

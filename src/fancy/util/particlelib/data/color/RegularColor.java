package fancy.util.particlelib.data.color;

import fancy.util.FancyUtil;
import fancy.util.particlelib.ParticleConstants;
import fancy.util.particlelib.ParticleEffect;

import java.awt.*;

public class RegularColor extends ParticleColor {

    public RegularColor(Color color) {
        super(color.getRed(), color.getGreen(), color.getBlue());
    }

    public RegularColor(int red, int green, int blue) {
        super(FancyUtil.getMaxOrMin(red, 255, 0), FancyUtil.getMaxOrMin(green, 255, 0), FancyUtil.getMaxOrMin(blue, 255, 0));
    }

    @Override
    public float getRed() {
        return super.getRed() / 255f;
    }

    @Override
    public float getGreen() {
        return super.getGreen() / 255f;
    }

    @Override
    public float getBlue() {
        return super.getBlue() / 255f;
    }

    @Override
    public Object toNMSData() {
        if (getEffect() != ParticleEffect.REDSTONE || ParticleConstants.version < 13)
            return new int[0];
        try {
            return ParticleConstants.PARTICLE_PARAM_REDSTONE_CONSTRUCTOR.newInstance(getRed(), getGreen(), getBlue(), 1f);
        } catch (Exception ex) {
            return null;
        }
    }

    public static RegularColor random() {
        return random(true);
    }

    public static RegularColor random(boolean highSaturation) {
        if (highSaturation)
            return fromHSVHue(FancyUtil.generateRandomInteger(0, 360));
        else
            return new RegularColor(new Color(FancyUtil.RANDOM.nextInt(256), FancyUtil.RANDOM.nextInt(256), FancyUtil.RANDOM.nextInt(256)));
    }

    public static RegularColor fromHSVHue(int hue) {
        return new RegularColor(new Color(Color.HSBtoRGB(hue / 360f, 1f, 1f)));
    }

}

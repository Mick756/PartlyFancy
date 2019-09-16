package fancy.util.particlelib;

import fancy.util.particlelib.data.ParticleData;
import fancy.util.particlelib.data.color.NoteColor;
import fancy.util.particlelib.data.color.ParticleColor;
import fancy.util.particlelib.data.color.RegularColor;
import fancy.util.particlelib.data.texture.BlockTexture;
import fancy.util.particlelib.data.texture.ItemTexture;
import org.bukkit.Location;

import java.lang.reflect.Constructor;

public class ParticlePacket {

    private final ParticleEffect particle;
    private final float offsetX;
    private final float offsetY;
    private final float offsetZ;
    private final float speed;
    private final int amount;
    private final ParticleData particleData;

    public ParticlePacket(ParticleEffect particle, float offsetX, float offsetY, float offsetZ, float speed, int amount, ParticleData particleData) {
        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.particleData = particleData;
    }

    public ParticlePacket(ParticleEffect particle, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        this.particle = particle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.particleData = null;
    }

    public ParticleEffect getParticle() {
        return particle;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getSpeed() {
        return speed;
    }

    public int getAmount() {
        return amount;
    }

    public ParticleData getParticleData() {
        return particleData;
    }

    public Object createPacket(Location location) {
        try {
            ParticleData data = getParticleData();
            ParticleEffect effect = getParticle();
            Constructor packetConstructor = ParticleConstants.PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR;
            int version = ParticleConstants.version;
            if (effect == null || effect.getFieldName().equals("NONE"))
                return null;
            if (data != null) {
                if (data.getEffect() != effect)
                    return null;
                if ((data instanceof BlockTexture && getParticle().hasProperty(PropertyType.REQUIRES_BLOCK)) || (data instanceof ItemTexture && getParticle().hasProperty(PropertyType.REQUIRES_ITEM))) {
                    return createPacket(version < 13 ? effect.getNMSObject() : data.toNMSData(),
                            (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                            getOffsetX(), getOffsetY(), getOffsetZ(),
                            getSpeed(), getAmount(), version < 13 ? (int[]) data.toNMSData() : new int[0]);
                } else if (data instanceof ParticleColor && effect.hasProperty(PropertyType.COLORABLE)) {
                    if (data instanceof NoteColor && effect.equals(ParticleEffect.NOTE))
                        return createPacket(effect.getNMSObject(),
                                (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                                ((NoteColor) data).getRed(), 0f, 0f,
                                getSpeed(), getAmount(), new int[0]
                        );
                    else if (data instanceof RegularColor) {
                        RegularColor color = ((RegularColor) data);
                        if (version < 13 || !effect.equals(ParticleEffect.REDSTONE)) {
                            return createPacket(effect.getNMSObject(),
                                    (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                                    (effect.equals(ParticleEffect.REDSTONE) && color.getRed() == 0 ? Float.MIN_NORMAL : color.getRed()), color.getGreen(), color.getBlue(),
                                    1f, 0, new int[0]);
                        } else {
                            return createPacket(data.toNMSData(),
                                    (float) location.getX(), (float) location.getY(), (float) location.getZ(),
                                    getOffsetX(), getOffsetY(), getOffsetZ(),
                                    getSpeed(), getAmount(), new int[0]);
                        }
                    }
                }
                return null;
            } else if (!effect.hasProperty(PropertyType.REQUIRES_BLOCK) && !effect.hasProperty(PropertyType.REQUIRES_ITEM)) {
                return createPacket(effect.getNMSObject(), (float) location.getX(), (float) location.getY(), (float) location.getZ(), getOffsetX(), getOffsetY(), getOffsetZ(), getSpeed(), getAmount(), new int[0]);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private Object createPacket(Object param, float locationX, float locationY, float locationZ, float offsetX, float offsetY, float offsetZ, float speed, int amount, int[] data) {
        Constructor packetConstructor = ParticleConstants.PACKET_PLAY_OUT_WORLD_PARTICLES_CONSTRUCTOR;
        try {
            return ParticleConstants.version < 13
                    ? packetConstructor.newInstance(param, true, locationX, locationY, locationZ, offsetX, offsetY, offsetZ, speed, amount, data)
                    : packetConstructor.newInstance(param, true, locationX, locationY, locationZ, offsetX, offsetY, offsetZ, speed, amount);
        } catch (Exception ex) {
            return null;
        }
    }


}

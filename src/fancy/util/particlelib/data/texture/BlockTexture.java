package fancy.util.particlelib.data.texture;

import fancy.util.NBTUtil;
import fancy.util.particlelib.ParticleConstants;
import fancy.util.particlelib.PropertyType;
import org.bukkit.Material;

import java.lang.reflect.Field;

public class BlockTexture extends ParticleTexture {

    public BlockTexture(Material material) {
        super(material, (byte) 0);
    }

    public BlockTexture(Material material, byte data) {
        super(material, data);
    }

    @Override
    public Object toNMSData() {
        if (getMaterial() == null || !getMaterial().isBlock() || getEffect() == null || !getEffect().hasProperty(PropertyType.REQUIRES_BLOCK))
            return null;
        if (ParticleConstants.version < 13)
            return super.toNMSData();
        Object block = getBlockData(getMaterial());
        if (block == null)
            return null;
        try {
            return ParticleConstants.PARTICLE_PARAM_BLOCK_CONSTRUCTOR.newInstance(getEffect().getNMSObject(), block);
        } catch (Exception ex) {
            return null;
        }
    }

    public Object getBlockData(Material material) {
        try {
            Field blockField = NBTUtil.getField(ParticleConstants.BLOCKS_CLASS, material.name(), false);
            if (blockField == null)
                return null;
            Object block = NBTUtil.readField(blockField, null);
            return ParticleConstants.BLOCK_GET_BLOCK_DATA_METHOD.invoke(block);
        } catch (Exception ex) {
            return null;
        }
    }

}

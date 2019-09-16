package fancy.util.particlelib.data.texture;

import fancy.util.particlelib.data.ParticleData;
import org.bukkit.Material;

public class ParticleTexture extends ParticleData {

    private final Material material;
    private final byte data;

    ParticleTexture(Material material, byte data) {
        this.material = material;
        this.data = data;
    }

    public Material getMaterial() {
        return material;
    }

    public byte getData() {
        return data;
    }

    @Override
    public Object toNMSData() {
        return new int[]{getMaterial().ordinal(), getData()};
    }
}

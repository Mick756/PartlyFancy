package fancy.util.particlelib.data.texture;

import fancy.util.particlelib.ParticleConstants;
import fancy.util.particlelib.PropertyType;
import org.bukkit.inventory.ItemStack;

public class ItemTexture extends ParticleTexture {

    private final ItemStack itemStack;

    public ItemTexture(ItemStack itemStack) {
        super(itemStack == null ? null : itemStack.getType(), (byte) 0);
        this.itemStack = itemStack;
    }

    @Override
    public Object toNMSData() {
        if (getMaterial() == null || getData() < 0 || getEffect() == null || !getEffect().hasProperty(PropertyType.REQUIRES_ITEM))
            return null;
        if (ParticleConstants.version < 13)
            return super.toNMSData();
        else {
            try {
                return ParticleConstants.PARTICLE_PARAM_ITEM_CONSTRUCTOR.newInstance(getEffect().getNMSObject(), toNMSItemStack(getItemStack()));
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static Object toNMSItemStack(ItemStack itemStack) {
        if (itemStack == null)
            return null;
        try {
            return ParticleConstants.CRAFT_ITEM_STACK_AS_NMS_COPY_METHOD.invoke(null, itemStack);
        } catch (Exception ex) {
            return null;
        }
    }

}

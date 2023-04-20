package fancy.cosmetics.gadgets;

import fancy.cosmetics.Gadget;
import org.bukkit.inventory.ItemStack;

public enum GadgetEnum {
    
    ENDERBOW(new EnderBow());

    private final Gadget gadget;
    GadgetEnum(Gadget g) {
        this.gadget = g;
    }

    public Gadget getGadget() {
        return this.gadget.newInstance();
    }

    public static boolean isGadgetItem(ItemStack item) {
        for (GadgetEnum gadget : GadgetEnum.values()) {
            
            for (ItemStack gadgetItem : gadget.getGadget().getGadgetItems()) {
                
                if (gadgetItem.isSimilar(item)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
}

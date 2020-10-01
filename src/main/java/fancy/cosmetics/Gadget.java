package fancy.cosmetics;

import org.bukkit.inventory.ItemStack;

public interface Gadget extends FancyCosmetic {
    
    ItemStack[] getGadgetItems();
    
    int cooldownInSeconds();

    void startCooldown();

    Gadget newInstance();
}

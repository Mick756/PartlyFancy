package fancy.inventory.themes;

import fancy.inventory.FancyMenuLoader;
import fancy.inventory.FancyMenuTheme;
import fancy.util.FancyUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Static implements FancyMenuTheme {

    private FancyMenuLoader.FancyMenu host;
    private Material item;

    /**
     * Create new theme object for an inventory
     * @param hostInventory Inventory to host this
     * @param material
     * @param omit
     */
    public Static(FancyMenuLoader.FancyMenu hostInventory, Material material, boolean omit) {
        this.host = hostInventory;
        this.item = material;
    }

    @Override
    public String name() {
        return "STATIC";
    }

    @Override
    public void apply() {

        while (this.host.getInventory().getViewers().size() > 0) {
            ItemStack borderItem = FancyUtil.createItemStack(this.item, 1, " ", null);

            int[] slots = FancyUtil.getInventoryBorder(this.host.getInventory(), true);
            if (slots.length > 0) {
                for (int slot : slots) {
                    this.host.getInventory().setItem(slot, borderItem);
                }
            }
        }
    }

    @Override
    public boolean isStatic() {
        return true;
    }
}

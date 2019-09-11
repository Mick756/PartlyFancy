package fancy.inventory.themes;

import fancy.inventory.FancyMenuTheme;
import fancy.util.FancyUtil;
import org.bukkit.DyeColor;
import org.bukkit.inventory.Inventory;

public class StaticTheme implements FancyMenuTheme {

    private Inventory host;
    private DyeColor color;
    private int[] slots;

    /**
     * Create new theme object for an inventory
     * @param hostInventory Inventory to host this
     * @param color
     * @param omit
     */
    public StaticTheme(Inventory hostInventory, DyeColor color, boolean omit) {
        this.host = hostInventory;
        this.color = color;
        this.slots = FancyUtil.getInventoryBorder(this.host, omit);
    }

    @Override
    public String name() {
        return "STATIC";
    }

    @Override
    public void apply() {

    }

    @Override
    public boolean isStatic() {
        return false;
    }
}

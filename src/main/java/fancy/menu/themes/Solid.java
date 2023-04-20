package fancy.menu.themes;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.themes.types.Static;
import fancy.util.CosmeticUtil;
import org.bukkit.inventory.ItemStack;

public class Solid implements FancyMenuTheme, Static, Cloneable {

    private FancyMenuLoader.FancyMenu host;
    private ItemStack item;

    public Solid() { }

    @Override
    public String name() {
        return "SOLID";
    }

    @Override
    public void apply() {

        this.clear();

        int[] slots = CosmeticUtil.getInventoryBorder(this.host.getInventory(), true);
        
        for (int slot : slots) {
            this.host.getInventory().setItem(slot, this.item);
        }
    }

    @Override
    public void clear() {
        int[] slots = CosmeticUtil.getInventoryBorder(this.host.getInventory(), true);
        for (int slot : slots) {
            this.host.getInventory().setItem(slot, null);
        }
    }

    @Override
    public ItemStack item() {
        return this.item;
    }

    @Override
    public FancyMenuTheme setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    @Override
    public FancyMenuLoader.FancyMenu menu() {
        return this.host;
    }

    @Override
    public FancyMenuTheme setMenu(FancyMenuLoader.FancyMenu menu) {
        this.host = menu;
        return this;
    }

    @Override
    public FancyMenuTheme newInstance() {
        return (FancyMenuTheme) this.clone();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}

package fancy.menu.themes;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.themes.types.Static;
import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Solid implements FancyMenuTheme, Static, Cloneable {

    private FancyMenuLoader.FancyMenu host;
    private Material item;

    public Solid() { }

    @Override
    public String name() {
        return "SOLID";
    }

    @Override
    public void apply() {

        clear();

        ItemStack borderItem = NBTUtil.setItemTag(FancyUtil.createItemStack(this.item, 1, " ", null), "null", "PartlyFancy", "border");
        int[] slots = FancyUtil.getInventoryBorder(this.host.getInventory(), true);

        if (slots.length > 0) {
            for (int slot : slots) {
                this.host.getInventory().setItem(slot, borderItem);
            }
        }
    }

    @Override
    public void clear() {
        int[] slots = FancyUtil.getInventoryBorder(this.host.getInventory(), true);
        if (slots.length > 0) {
            for (int slot : slots) {
                this.host.getInventory().setItem(slot, null);
            }
        }
    }

    @Override
    public Material item() {
        return this.item;
    }

    @Override
    public FancyMenuTheme setItem(Material item) {
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
            return null;
        }

    }
}

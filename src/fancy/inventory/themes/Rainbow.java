package fancy.inventory.themes;

import fancy.PartlyFancy;
import fancy.inventory.FancyMenuLoader;
import fancy.inventory.FancyMenuTheme;
import fancy.util.FancyUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Rainbow implements FancyMenuTheme {

    private FancyMenuLoader.FancyMenu host;
    private Material[] items;

    /**
     * Create new theme object for an inventory
     * @param hostInventory Inventory to host this
     * @param materials
     * @param omit
     */
    public Rainbow(FancyMenuLoader.FancyMenu hostInventory, Material[] materials, boolean omit) {
        this.host = hostInventory;
        this.items = materials;
    }

    @Override
    public String name() {
        return "STATIC";
    }

    @Override
    public void apply() {

        new BukkitRunnable() {
            @Override
            public void run() {

                int[] slots = FancyUtil.getInventoryBorder(host.getInventory(), true);
                if (slots.length > 0) {
                    for (int slot : slots) {
                        ItemStack borderItem = FancyUtil.createItemStack(items[new Random().nextInt(items.length)], 1, " ", null);
                        host.getInventory().setItem(slot, borderItem);
                    }
                }

                if (host.getInventory().getViewers().size() == 0) {
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(PartlyFancy.getInstance(), 0, 15);
    }

    @Override
    public boolean isStatic() {
        return true;
    }
}

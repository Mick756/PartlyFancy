package fancy.menu.themes;

import fancy.PartlyFancy;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Rainbow implements FancyMenuTheme {

    private FancyMenuLoader.FancyMenu host;
    private List<Material> items;
    private int task;

    /**
     * Create new theme object for an inventory
     * @param hostInventory Inventory to host this
     * @param materials
     * @param omit
     */
    public Rainbow(FancyMenuLoader.FancyMenu hostInventory, Material[] materials, boolean omit) {
        this.host = hostInventory;
        this.items = Arrays.asList(materials);
    }

    public Rainbow(FancyMenuLoader.FancyMenu hostInventory, List<Material> materials, boolean omit) {
        this.host = hostInventory;
        this.items = materials;
    }

    @Override
    public String name() {
        return "RAINBOW";
    }

    @Override
    public void apply() {

        clear();

        task = new BukkitRunnable() {
            @Override
            public void run() {

                int[] slots = FancyUtil.getInventoryBorder(host.getInventory(), true);
                if (slots.length > 0) {
                    for (int slot : slots) {
                        ItemStack borderItem = NBTUtil.setItemTag(FancyUtil.createItemStack( items.get( new Random().nextInt( items.size() ) ), 1, " ", null), "null", "PartlyFancy");
                        host.getInventory().setItem(slot, borderItem);
                    }
                }

            }
        }.runTaskTimerAsynchronously(PartlyFancy.getInstance(), 0, 15).getTaskId();
    }

    @Override
    public void clear() {
        Bukkit.getScheduler().cancelTask(task);
        task = -1;
    }

    @Override
    public boolean isStatic() {
        return true;
    }
}

package fancy.menu.themes;

import fancy.PartlyFancy;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.themes.types.MultiColor;
import fancy.util.FancyUtil;
import fancy.util.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class Rainbow implements FancyMenuTheme, MultiColor {

    private FancyMenuLoader.FancyMenu host;
    private List<Material> items;
    private int task;

    private boolean init;
    public Rainbow(boolean init) {
        this.init = init;
    };

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
    public Material[] item() {
        return (Material[]) this.items.toArray();
    }

    @Override
    public FancyMenuTheme setItems(List<Material> materials) {
        this.items = materials;
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
}

package fancy.menu.themes;

import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.themes.types.Static;
import fancy.util.CosmeticUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Snake implements FancyMenuTheme, Static, Cloneable {

    private FancyMenuLoader.FancyMenu host;
    private int task;
    private ItemStack item;
    int[][] positions = {{0, 9, 18, 27}, {1, 0, 9, 18}, {2, 1, 0, 9}, {3, 2, 1, 0}, {4, 3, 2, 1}, {5, 4, 3, 2}, {6, 5, 4, 3}, {7, 6, 5, 4}, {8, 7, 6, 5}, {17, 8, 7, 6}, {26, 17, 8, 7}, {35, 26, 17, 8}, {44, 35, 26, 17}, {53, 44, 35, 26}, {52, 53, 44, 35}, {51, 52, 53, 44}, {47, 51, 52, 53}, {46, 47, 51, 52}, {45, 46, 47, 51}, {36, 45, 46, 47}, {27, 36, 45, 46}, {18, 27, 36, 45}, {9, 18, 27, 36}};

    public Snake() {}

    @Override
    public String name() {
        return "SNAKE";
    }

    @Override
    public void apply() {
        clear();
        int[] slots = CosmeticUtil.getInventoryBorder(this.host.getInventory(), true);
        task = new BukkitRunnable() {
            int pos_index = 0;
            @Override
            public void run() {
                if (slots.length > 0) {
                    for (int slot : slots) {
                        host.getInventory().setItem(slot, null);
                    }

                    for (int slot : positions[pos_index]) {
                        host.getInventory().setItem(slot, item);
                    }
                }

                if (pos_index >= positions.length - 1) {
                    pos_index = 0;
                } else pos_index++;
            }
        }.runTaskTimerAsynchronously(fancy.PartlyFancy.getInstance(), 0, 5).getTaskId();

    }

    @Override
    public void clear() {
        Bukkit.getScheduler().cancelTask(task);
        task = -1;
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
    public ItemStack item() {
        return item;
    }

    @Override
    public FancyMenuTheme setItem(ItemStack item) {
        this.item = item;
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

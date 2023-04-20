package fancy.menu.themes;

import fancy.PartlyFancy;
import fancy.menu.FancyMenuLoader;
import fancy.menu.FancyMenuTheme;
import fancy.menu.themes.types.MultiColor;
import fancy.util.CosmeticUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class Rainbow implements FancyMenuTheme, MultiColor, Cloneable {

    private FancyMenuLoader.FancyMenu host;
    private List<ItemStack> items;
    private int task;

    public Rainbow() { }

    @Override
    public String name() {
        return "RAINBOW";
    }

    @Override
    public void apply() {

        clear();

        int[] slots = CosmeticUtil.getInventoryBorder(host.getInventory(), true);

        this.task = new BukkitRunnable() {
            final Random r = new Random();
            
            @Override
            public void run() {
                
                for (int slot : slots) {
                    
                    host.getInventory().setItem(slot, items.get(this.r.nextInt(items.size())));
                }
                
            }
        }.runTaskTimerAsynchronously(PartlyFancy.getInstance(), 0, 15).getTaskId();
    }

    @Override
    public void clear() {
        Bukkit.getScheduler().cancelTask(this.task);
        this.task = -1;
    }

    @Override
    public ItemStack[] items() {
        return (ItemStack[]) this.items.toArray();
    }

    @Override
    public FancyMenuTheme setItems(List<ItemStack> stacks) {
        this.items = stacks;
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

package fancy.menu.events;

import fancy.PartlyFancy;
import fancy.menu.FancyMenuLoader;
import fancy.util.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        // Items checks

        if (e.getWhoClicked() instanceof Player) {

            Player p = (Player) e.getWhoClicked();

            if (e.getCurrentItem() != null) {

                ItemStack item = e.getCurrentItem();

                if (NBTUtil.getItemTag(item, "PartlyFancy", "openinv") != null || NBTUtil.getItemTag(item, "PartlyFancy", "goback") != null) {

                    e.setCancelled(true);

                    Object value;

                    if (NBTUtil.getItemTag(item, "PartlyFancy", "openinv") != null) {
                        value = NBTUtil.getItemTag(item, "PartlyFancy", "openinv");
                    } else {
                        value = NBTUtil.getItemTag(item, "PartlyFancy", "goback");
                    }

                    FancyMenuLoader.FancyMenu inv = FancyMenuLoader.getFromId(Integer.parseInt(value.toString()));

                    if (inv.getInventory() == null) {
                        p.sendMessage(PartlyFancy.getValue("message.menu.not-found", "%id%-" + value.toString()));
                    } else {
                        FancyMenuLoader.openMenu(p, inv, true);
                    }

                } else if (NBTUtil.getItemTag(item, "PartlyFancy", "close") != null) {

                    e.setCancelled(true);
                    FancyMenuLoader.closeMenu(p, true);

                } else if (NBTUtil.getItemTag(item, "PartlyFancy") != null) {

                    e.setCancelled(true);

                }

                return;
            }

            // Inventory checks
            String name = ChatColor.stripColor(e.getView().getTitle());
            if (name.equalsIgnoreCase("Fancy Main Menu") || name.equalsIgnoreCase("Fancy Crown Menu")) {

                e.setCancelled(true);

            }

        }
    }
}

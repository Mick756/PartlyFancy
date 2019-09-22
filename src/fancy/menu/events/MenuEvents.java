package fancy.menu.events;

import fancy.FancyPlayer;
import fancy.PartlyFancy;
import fancy.cosmetics.particles.AuraParticle;
import fancy.cosmetics.particles.CrownParticle;
import fancy.cosmetics.particles.WingsParticle;
import fancy.menu.FancyMenuLoader;
import fancy.util.NBTUtil;
import fancy.util.Particles;
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
            FancyPlayer fp = FancyPlayer.getFancyPlayer(p);

            if (e.getCurrentItem() != null) {

                ItemStack item = e.getCurrentItem();

                // Menu navigation checks
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

                    // Menu close
                } else if (NBTUtil.getItemTag(item, "PartlyFancy", "crown_particle") != null) {

                    e.setCancelled(true);

                    if (fp.particleEffect != null) {
                        fp.stopParticle();
                    }

                    CrownParticle crown = new CrownParticle(p, Particles.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase().replaceAll(" ", "_")));

                    fp.startParticle(crown);

                    FancyMenuLoader.closeMenu(p, true);

                }  else if (NBTUtil.getItemTag(item, "PartlyFancy", "aura_particle") != null) {

                    e.setCancelled(true);

                    if (fp.particleEffect != null) {
                        fp.stopParticle();
                    }

                    AuraParticle aura = new AuraParticle(p, Particles.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase().replaceAll(" ", "_")));

                    fp.startParticle(aura);

                    FancyMenuLoader.closeMenu(p, true);

                    // PartlyFancy item that does not meet any checks previously (ex. border glass)
                }  else if (NBTUtil.getItemTag(item, "PartlyFancy", "wings_particle") != null) {

                    e.setCancelled(true);

                    if (fp.particleEffect != null) {
                        fp.stopParticle();
                    }

                    WingsParticle aura = new WingsParticle(p, Particles.valueOf(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase().replaceAll(" ", "_")));

                    fp.startParticle(aura);

                    FancyMenuLoader.closeMenu(p, true);

                    // PartlyFancy item that does not meet any checks previously (ex. border glass)
                } else if (NBTUtil.getItemTag(item, "PartlyFancy", "close") != null) {

                    e.setCancelled(true);
                    FancyMenuLoader.closeMenu(p, true);

                    // PartlyFancy item that does not meet any checks previously (ex. border glass)
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

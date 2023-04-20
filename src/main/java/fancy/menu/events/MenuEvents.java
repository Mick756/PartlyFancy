package fancy.menu.events;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.FancyPlayer;
import fancy.PartlyFancy;
import fancy.cosmetics.Gadget;
import fancy.cosmetics.Particle;
import fancy.cosmetics.gadgets.GadgetEnum;
import fancy.cosmetics.particles.AuraParticle;
import fancy.cosmetics.particles.CrownParticle;
import fancy.cosmetics.particles.OrbParticle;
import fancy.cosmetics.particles.WingsParticle;
import fancy.menu.FancyMenuLoader;
import fancy.util.Particles;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MenuEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player p) {
            FancyPlayer fp = FancyPlayer.getFancyPlayer(p);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && e.getCurrentItem().getItemMeta() != null) {
    
                ItemMeta meta = e.getCurrentItem().getItemMeta();
                
                ItemStack item = e.getCurrentItem();
                NBTItem nbt = new NBTItem(item);
    
                String action = nbt.getString("action");
                String gadget = nbt.getString("gadget");
                Integer invId = nbt.getInteger("inventory");
                String particleId = nbt.getString("particle");
    
                if (invId != null && invId != 0) {
                    e.setCancelled(true);
                    FancyMenuLoader.FancyMenu inv = FancyMenuLoader.getFromId(invId);
                    
                    if (inv.getInventory() == null) {
                        p.sendMessage(PartlyFancy.getStringValue("message.menu.not-found", "%id%-" + invId));
                    } else {
                        if (!FancyMenuLoader.openMenu(p, inv, true)) {
                            p.sendMessage(PartlyFancy.getStringValue("message.menu.not-found", "%id%-" + invId));
                        }
                    }
    
                } else {
                    
                    if (gadget != null && !gadget.equals("")) {
                        e.setCancelled(true);
                        
                        if (fp.getGadget() != null) {
                            fp.stopGadget(true);
                        }
                        Gadget g = GadgetEnum.valueOf(gadget.toUpperCase()).getGadget();
                        
                        g.setPlayer(p);
                        fp.startCosmetic(g, true);
                        
                        FancyMenuLoader.closeMenu(p, true);
                        return;
                    }
                    
                    if (action != null && !action.equals("")) {
                        
                        switch (action) {
                            case "close" -> {
                                e.setCancelled(true);
                                e.setCurrentItem(null);
                                FancyMenuLoader.closeMenu(p, true);
                            }
                            case "stopall" -> {
                                e.setCancelled(true);
                                e.setCurrentItem(null);
                                fp.stopAll(true);
                                FancyMenuLoader.closeMenu(p, true);
                            }
                            case "cancel" -> e.setCancelled(true);
                        }
                        
                        return;
                    }
                    
                    if (particleId != null && !particleId.equals("")) {
                        
                        e.setCancelled(true);
                        
                        Particles particles = Particles.valueOf(ChatColor.stripColor(meta.getDisplayName()).toUpperCase().replaceAll(" ", "_"));
                        if (fp.getParticleEffect() != null) {
                            fp.stopParticle(true);
                        }
                        
                        Particle cosmetic = switch (particleId) {
                            case "crown_particle" -> new CrownParticle(p, particles);
                            case "aura_particle" -> new AuraParticle(p, particles);
                            case "wings_particle" -> new WingsParticle(p, particles);
                            case "orb_particle" -> new OrbParticle(p, particles);
                            default -> null;
                        };
    
                        
                        if (cosmetic != null) {
                            fp.startCosmetic(cosmetic, true);
                        }
                        
                        FancyMenuLoader.closeMenu(p, true);
                    }
                }
            }
        }
    }
}

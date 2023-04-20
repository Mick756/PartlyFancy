package fancy.cosmetics.events;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.FancyPlayer;
import fancy.PartlyFancy;
import fancy.cosmetics.gadgets.EnderBow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EnderBowEvents implements Listener {

    @EventHandler
    public void onEnderBowShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            
            ItemStack stack = e.getBow();
            if (stack == null) return;
            
            NBTItem nbt = new NBTItem(stack);
            String gadget = nbt.getString("gadget-item");
            FancyPlayer fp = FancyPlayer.getFancyPlayer((Player) e.getEntity());
            
            if (fp.getGadget() != null && fp.getGadget() instanceof EnderBow && gadget != null && gadget.equals("ender-bow-bow")) {
                e.setConsumeItem(false);
                
                if (EnderBow.COOLDOWNS.contains(fp)) {
                    e.setCancelled(true);
                    
                    fp.sendMessageWithPrefix(PartlyFancy.getStringValue("message.gadget.cooldown", "%player%-" + fp.getPlayer().getCustomName(), "%gadget%-Ender Bow"));
                    
                    return;
                }
                
                double y = (fp.getPlayer().isSneaking()) ? 1.25d : 1.5d;
                EnderPearl enderpearl = fp.getPlayer().getWorld().spawn(fp.getPlayer().getLocation().add(0, y, 0), EnderPearl.class);
                
                e.setProjectile(enderpearl);
                
                enderpearl.setCustomName("pf-eb-" + fp.getPlayer().getUniqueId());
                enderpearl.setCustomNameVisible(false);
                enderpearl.setVelocity(fp.getPlayer().getLocation().getDirection().multiply(e.getForce() * 3f));
                
                fp.getGadget().startCooldown();
            }
        }
    }

    @EventHandler
    public void onEnderBowPearlLand(ProjectileHitEvent e) {
        if (e.getEntity() instanceof EnderPearl proj && proj.getCustomName() != null) {
    
            if (proj.getCustomName().contains("pf-eb-")) {
                
                Player p = Bukkit.getPlayer(UUID.fromString(proj.getCustomName().replace("pf-eb-", "")));
                if (p != null) {
                    
                    int maxHeight = PartlyFancy.getIntValue("gadget.ender-bow.max-y-allowed");
                    float pitch = p.getLocation().getPitch();
                    float yaw = p.getLocation().getYaw();
                    
                    Location start = e.getEntity().getLocation();
                    Location teleportTo = new Location(p.getWorld(), start.getX(), start.getY() + 1, start.getZ(), yaw, pitch);
                    
                    if (teleportTo.getY() < maxHeight) {
                        p.teleport(teleportTo);
                    } else {
                        FancyPlayer.getFancyPlayer(p).sendMessageWithPrefix(PartlyFancy.getStringValue("message.gadget.ender-bow-y-too-high", "%player%-" + p.getName()));
                    }
                    
                    if (e.getEntity().isValid()) e.getEntity().remove();
                }
            }
        }
    }

}

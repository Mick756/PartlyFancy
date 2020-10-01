package fancy.cosmetics.events;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.FancyPlayer;
import fancy.cosmetics.gadgets.EnderBow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
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
            NBTItem nbt = new NBTItem(stack);
            String gadget = nbt.getString("gadgetitem");
            FancyPlayer fp = FancyPlayer.getFancyPlayer((Player) e.getEntity());
            if (fp.getGadget() != null && fp.getGadget() instanceof EnderBow && gadget != null && gadget.equals("ender-bow-bow")) {
                double y = (fp.getPlayer().isSneaking()) ? 1.25d : 1.5d;
                Entity enderpearl = fp.getPlayer().getWorld().spawn(fp.getPlayer().getLocation().add(0, y, 0), EnderPearl.class);
                e.setProjectile(enderpearl);
                enderpearl.setCustomName("pf-eb-" + fp.getPlayer().getUniqueId());
                enderpearl.setCustomNameVisible(false);
                enderpearl.setVelocity(fp.getPlayer().getLocation().getDirection().multiply(e.getForce() * 3f));
            }
        }
    }

    @EventHandler
    public void onEnderBowPearlLand(ProjectileHitEvent e) {
        if (e.getEntity() instanceof EnderPearl && e.getEntity().getCustomName() != null) {
            EnderPearl proj = (EnderPearl) e.getEntity();
            if (proj.getCustomName().contains("pf-eb-")) {
                Player p = Bukkit.getPlayer(UUID.fromString(proj.getCustomName().replace("pf-eb-", "")));
                if (p != null) {
                    int maxHeight = fancy.PartlyFancy.getIntValue("gadget.ender-bow.max-y-allowed");
                    float pitch = p.getLocation().getPitch();
                    float yaw = p.getLocation().getYaw();
                    Location start = e.getEntity().getLocation();
                    if (start != null) {
                        Location teleTo = new Location(p.getWorld(), start.getX(), start.getY() + 1, start.getZ(), yaw, pitch);
                        if (teleTo.getY() < maxHeight) {
                            p.teleport(teleTo);
                        } else {
                            FancyPlayer.getFancyPlayer(p).sendMessage(true, fancy.PartlyFancy.getStringValue("message.gadget.ender-bow-y-to-high", "%player%-" + p.getName()));
                        }
                    }
                    if (e.getEntity() != null && e.getEntity().isValid()) e.getEntity().remove();
                }
            }
        }
    }

}

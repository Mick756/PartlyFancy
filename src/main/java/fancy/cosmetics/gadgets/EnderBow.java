package fancy.cosmetics.gadgets;

import api.builders.ItemStackBuilder;
import api.builders.misc.TaskUtils;
import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.FancyPlayer;
import fancy.PartlyFancy;
import fancy.cosmetics.Gadget;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class EnderBow implements Gadget {

    public static final List<FancyPlayer> COOLDOWNS = new ArrayList<>();
    
    public EnderBow() {}
    private Player player;

    @Override
    public ItemStack[] getGadgetItems() {
        
        NBTItem bow = new NBTItem(new ItemStackBuilder(Material.BOW).unbreakable().setDisplayName("&5Ender Bow (&eRight Click!&5)").addEnchantment(Enchantment.ARROW_INFINITE, 1).setLore("&7Shoot the bow and teleport", "&7to where the arrow lands!").build());
        bow.setString("action", "cancel");
        bow.setString("gadget-item", "ender-bow-bow");
        
        NBTItem arrow = new NBTItem(new ItemStackBuilder(Material.ARROW).setDisplayName("&5Ender Arrow").addEnchantment(Enchantment.ARROW_INFINITE, 1).build());
        arrow.setString("action", "cancel");
        arrow.setString("gadget-item", "ender-bow-arrow");
        
        return new ItemStack[]{ bow.getItem(), arrow.getItem() };
    }

    @Override
    public void startCooldown() {
        final FancyPlayer fancyPlayer = FancyPlayer.getFancyPlayer(this.player);
        long cooldown = PartlyFancy.getIntValue("gadget.ender-bow.cooldown");
        
        COOLDOWNS.add(fancyPlayer);
        TaskUtils.doAsyncLater(PartlyFancy.getInstance(), () -> COOLDOWNS.remove(fancyPlayer), cooldown * 20L);
    }

    @Override
    public int cooldownInSeconds() {
        return PartlyFancy.getIntValue("gadget.ender-bow.cooldown");
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String name() {
        return "Ender Bow";
    }

    @Override
    public boolean start() {
        FancyPlayer fp = FancyPlayer.getFancyPlayer(this.getPlayer());
        ItemStack[] items = this.getGadgetItems();
        PlayerInventory playerInventory = this.getPlayer().getInventory();
        
        int bowSlot = PartlyFancy.getIntValue("gadget.ender-bow.slot.bow");
        int arrowSlot = PartlyFancy.getIntValue("gadget.ender-bow.slot.arrow");
        
        if (playerInventory.getItem(bowSlot) == null && playerInventory.getItem(arrowSlot) == null) {
            
            playerInventory.setItem(bowSlot, items[0]);
            playerInventory.setItem(arrowSlot, items[1]);
        } else {
            
            fp.sendMessageWithPrefix(PartlyFancy.getStringValue("message.gadget.slot-taken", "%slot%-" + bowSlot + " & " + arrowSlot));
            return false;
        }
        
        return true;
    }

    @Override
    public boolean stop() {
        int bowSlot = PartlyFancy.getIntValue("gadget.ender-bow.slot.bow");
        int arrowSlot = PartlyFancy.getIntValue("gadget.ender-bow.slot.arrow");
        
        this.getPlayer().getInventory().setItem(bowSlot, null);
        this.getPlayer().getInventory().setItem(arrowSlot, null);
        
        return true;
    }

    @Override
    public Gadget newInstance() {
        return new EnderBow();
    }
}

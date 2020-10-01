package fancy.cosmetics.gadgets;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fancy.FancyPlayer;
import fancy.cosmetics.Gadget;
import fancy.util.CosmeticUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EnderBow implements Gadget {

    public EnderBow() {}

    private Player player;
    public EnderBow(Player player) {
        this.player = player;
    }

    @Override
    public ItemStack[] getGadgetItems() {
        
        NBTItem bow = new NBTItem(CosmeticUtil.createItemStack(Material.BOW, 1, "&5Ender Bow (&eRight Click!&5)", null, "&7Shoot the bow and teleport", "&7to where the arrow lands!"));
        bow.setString("action", "cancel");
        bow.setString("gadgetitem", "ender-bow-bow");
        NBTItem arrow = new NBTItem(CosmeticUtil.createItemStack(Material.ARROW, 1, "&5Ender Arrow", null));
        arrow.setString("action", "cancel");
        arrow.setString("gadgetitem", "ender-bow-arrow");
        
        return new ItemStack[]{
                CosmeticUtil.addEnchantment(bow.getItem(), Enchantment.ARROW_INFINITE, 1),
                CosmeticUtil.addEnchantment(arrow.getItem(), Enchantment.ARROW_INFINITE, 1)
        };
    }

    @Override
    public void startCooldown() {

    }

    @Override
    public int cooldownInSeconds() {
        return fancy.PartlyFancy.getIntValue("gadget.ender-bow.cooldown");
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
        int bowSlot = fancy.PartlyFancy.getIntValue("gadget.ender-bow.slot.bow");
        int arrowSlot = fancy.PartlyFancy.getIntValue("gadget.ender-bow.slot.arrow");
        ItemStack[] items = this.getGadgetItems();
        PlayerInventory playerInventory = this.getPlayer().getInventory();
        if (playerInventory.getItem(bowSlot) == null && playerInventory.getItem(arrowSlot) == null) {
            playerInventory.setItem(bowSlot, items[0]);
            playerInventory.setItem(arrowSlot, items[1]);
        } else {
            fp.sendMessage(true, fancy.PartlyFancy.getStringValue("message.gadget.slot-taken", "%slot%-" + bowSlot + " & " + arrowSlot));
            return false;
        }
        return true;
    }

    @Override
    public boolean stop() {
        int bowSlot = fancy.PartlyFancy.getIntValue("gadget.ender-bow.slot.bow");
        int arrowSlot = fancy.PartlyFancy.getIntValue("gadget.ender-bow.slot.arrow");
        this.getPlayer().getInventory().setItem(bowSlot, null);
        this.getPlayer().getInventory().setItem(arrowSlot, null);
        return true;
    }

    @Override
    public Gadget newInstance() {
        return new EnderBow();
    }
}

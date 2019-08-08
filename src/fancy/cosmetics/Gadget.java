package fancy.cosmetics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public interface Gadget extends FancyCosmetic {

    ItemStack getGadgetItem();

    @EventHandler
    void onClickWithGadgetInHand(PlayerInteractEvent event);

    @EventHandler
    void onJumpWithGadgetInHand(PlayerMoveEvent event);

}

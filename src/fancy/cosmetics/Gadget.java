package fancy.cosmetics;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public interface Gadget extends FancyCosmetic{

    ItemStack getGadgetItem();

    int cooldownInSeconds();

    void onClickWithGadgetInHand(PlayerInteractEvent event);

    void onJumpWithGadgetInHand(PlayerMoveEvent event);

}

package fancy.cosmetics.events;

import fancy.cosmetics.gadgets.GadgetEnum;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class GadgetEvents implements Listener {
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		
		if (GadgetEnum.isGadgetItem(event.getItemDrop().getItemStack())) {
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onCreative(InventoryCreativeEvent event) {
		
		if (GadgetEnum.isGadgetItem(event.getCurrentItem())) {
			event.setCursor(new ItemStack(Material.AIR));
			event.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		if (GadgetEnum.isGadgetItem(event.getCurrentItem())) {
			event.setCancelled(true);
		}
		
	}
}

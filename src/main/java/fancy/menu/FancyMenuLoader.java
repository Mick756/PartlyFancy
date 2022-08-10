package fancy.menu;

import fancy.PartlyFancy;
import fancy.menu.menus.ParticleMenu;
import fancy.menu.menus.SettingsMenu;
import fancy.menu.menus.gadget.GadgetMenu;
import fancy.menu.menus.particle.AuraMenu;
import fancy.menu.menus.particle.CrownMenu;
import fancy.menu.menus.particle.OrbMenu;
import fancy.menu.menus.particle.WingsMenu;
import fancy.menu.themes.Rainbow;
import fancy.menu.themes.Snake;
import fancy.menu.themes.Solid;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class FancyMenuLoader {
	
	private static boolean init = false;
	
	public static List<FancyMenu> menus = new ArrayList<>();
	public static List<FancyMenuTheme> themes = new ArrayList<>();
	
	public static void initialize() {
		
		if (init) return;
		
		addTheme(new Solid());
		addTheme(new Rainbow());
		addTheme(new Snake());
		
		registerFancyMenu(new MainMenu());
		registerFancyMenu(new ParticleMenu());
		registerFancyMenu(new SettingsMenu());
		
		registerFancyMenu(new CrownMenu());
		registerFancyMenu(new AuraMenu());
		registerFancyMenu(new WingsMenu());
		registerFancyMenu(new OrbMenu());
		registerFancyMenu(new GadgetMenu());
		
		for (FancyMenu menu : menus) {
			Bukkit.getPluginManager().addPermission(menu.permission());
		}
		
		init = true;
	}
	
	public static boolean openMenu(Player player, FancyMenu inventory, boolean sound) {
		if (player.hasPermission(inventory.permission())) {
			
			if (sound) {
				player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.open"), 0.2f, 1f);
			}
			
			player.openInventory(inventory.getInventory());
			return true;
		} else {
			
			player.closeInventory();
			player.sendMessage(PartlyFancy.getPrefix() + PartlyFancy.getStringValue("message.menu.no-permission", "%player%-" + player.getCustomName(), "%permission%-" + inventory.permission().getName()));
			
			if (sound) {
				player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.no-permission"), 0.5f, 1f);
			}
		}
		
		return false;
	}
	
	public static void closeMenu(Player player, boolean sound) {
		player.closeInventory();
		if (sound) {
			player.playSound(player.getLocation(), PartlyFancy.getSound("sound.inventory.close"), 0.2f, 1f);
		}
	}
	
	public enum FancyMenuIds {
		MAIN(1), PARTICLE(2), SETTINGS(3),
		PARTICLE_CROWN(20), PARTICLE_AURA(21), PARTICLE_ORB(22), PARTICLE_WINGS(23),
		GADGET_PAGE_ONE(30);
		
		private final @Getter int id;
		FancyMenuIds(int id) {
			this.id = id;
		}
	}
	
	public static void registerFancyMenu(FancyMenu m) {
		menus.add(m);
	}
	
	static void addTheme(FancyMenuTheme theme) {
		themes.add(theme);
	}
	
	public static FancyMenu getFromId(int id) {
		for (FancyMenu m : menus) {
			if (m.inventoryId().equals(id)) {
				return m;
			}
		}
		return null;
	}
	
	public interface FancyMenu {
		
		Integer inventoryId();
		
		FancyMenu getInstance();
		
		Inventory getInventory();
		
		String getName();
		
		Permission permission();
		
		FancyMenuTheme getTheme();
		
	}
	
}

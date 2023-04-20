package fancy.command;

import fancy.FancyPlayer;
import fancy.PartlyFancy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdConfig implements FancyCommandLoader.FancyCommand {
	
	@Override
	public int run(Player player, String[] args) {
		FancyPlayer fancyPlayer = FancyPlayer.getFancyPlayer(player);
		
		if (!(args.length > 2)) {
			return 0;
		}
		
		if (player.hasPermission(permission())) {
			String path = args[2];
			YamlConfiguration yml = (YamlConfiguration) PartlyFancy.getInstance().getConfig();
			
			Object get = yml.get(path);
			if (get == null) {
				
				fancyPlayer.sendMessageWithPrefix(PartlyFancy.getStringValue("message.command.config.path-not-found", "%player%-" + player.getCustomName(), "%path%-" + path));
				return 1;
			} else {
				
				if (args.length == 3) {
					if (args[1].equalsIgnoreCase("get")) {
						
						fancyPlayer.sendMessageWithPrefix(PartlyFancy.getStringValue("message.command.config.value", "%player%-" + player.getCustomName(), "%path%-" + path, "%value%-" + get));
						return 1;
					}
				} else {
					if (args[1].equalsIgnoreCase("set")) {
						
						String value = condenseArgs(args);
						yml.set(path, value);
						PartlyFancy.getInstance().saveConfig();
						PartlyFancy.getInstance().reloadConfig();
						
						fancyPlayer.sendMessageWithPrefix(PartlyFancy.getStringValue("message.command.config.value-changed", "%player%-" + player.getCustomName(), "%path%-" + path, "%value%-" + value));
						return 1;
					}
				}
			}
		}
		
		return 0;
	}
	
	private String condenseArgs(String[] args) {
		StringBuilder s = new StringBuilder();
		for (int i = 3; i < args.length; i++) {
			s.append(args[i]);
			
			if (i != args.length - 1) {
				s.append(" ");
			}
		}
		
		return s.toString();
	}
	
	@Override
	public String[] subCommands() {
		return new String[]{"config", "conf"};
	}
	
	@Override
	public Permission permission() {
		return new Permission("fancy.command.config", "Permission to edit and get config values.");
	}
}

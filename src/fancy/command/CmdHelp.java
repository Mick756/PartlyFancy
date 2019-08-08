package fancy.command;

import fancy.PartlyFancy;
import fancy.util.FancyUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdHelp implements FancyCommandLoader.FancyCommand {

    private String[] helpPageOne = {
            "&9-[+]- PartlyFancy Help (1/1) -[+]-",
            "&e/fancy help(h) [page]",
            "&e/fancy inventory(i)"};

    @Override
    public int run(Player player, String[] args) {

        if (player.hasPermission(permission())) {
            if (args.length == 1) {
                for (String line : helpPageOne) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                }
                return 1;
            } else {
                if (FancyUtil.isInteger(args[1])) {
                    int page = Integer.parseInt(args[1]);
                    if (page == 1) {
                        for (String line : helpPageOne) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
                        }
                    }
                }
                return 1;
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', PartlyFancy.getValue("message.command.no-permission", "%player%-" + player.getDisplayName(), "%perm%-" + permission().getName())));
        }

        // return 0 if used incorrectly
        return 0;
    }

    @Override
    public String[] subCommands() {
        return new String[]{"help", "h"};
    }

    @Override
    public String usage() {
        return "/partlyfancy help [page]";
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.command.help");
    }
}

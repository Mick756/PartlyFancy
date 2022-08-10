package fancy.command;

import fancy.FancyPlayer;
import fancy.PartlyFancy;
import fancy.util.CosmeticUtil;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdHelp implements FancyCommandLoader.FancyCommand {


    private final String[] helpPageOne = {
            "&9----------------&e PartlyFancy Commands&9 ----------------",
            "&7[] optional argument - () alias - <> required argument",
            " ",
            "&e/fancy help(h) [page]&7 - Display the help pages.",
            "&e/fancy menu(m)&7 - Open the cosmetic menu.",
            "&e/fancy permissions(perms)&7 - View a list of PartlyFancy permissions.",
            "&e/fancy config(config) get|set <path> [value]&7 - Get or change a config value.&c Warning: Setting should only be used to change messages."
    };

    @Override
    public int run(Player player, String[] args) {
        FancyPlayer fancyPlayer = FancyPlayer.getFancyPlayer(player);

        if (player.hasPermission(permission())) {
            if (args.length == 1) {
                for (String line : helpPageOne) {
                    fancyPlayer.sendMessage(line);
                }
                return 1;
            } else {
                if (CosmeticUtil.isInteger(args[1])) {
                    int page = Integer.parseInt(args[1]);
                    if (page == 1) {
                        for (String line : helpPageOne) {
                            fancyPlayer.sendMessage(line);
                        }
                    } else {
                        fancyPlayer.sendMessageWithPrefix(PartlyFancy.getStringValue("message.command.page-does-not-exist", "%player%-" + player.getCustomName()));
                    }
                    return 1;
                }
            }
        } else {
            fancyPlayer.sendMessageWithPrefix(PartlyFancy.getStringValue("message.command.no-permission", "%player%-" + player.getCustomName(), "%perm%-" + permission().getName()));
        }
        return 0;
    }

    @Override
    public String[] subCommands() {
        return new String[]{"help", "h"};
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.command.help", "PartlyFancy help command permission");
    }
}

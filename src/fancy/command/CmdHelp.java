package fancy.command;

import fancy.FancyPlayer;
import fancy.PartlyFancy;
import fancy.util.FancyUtil;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdHelp implements FancyCommandLoader.FancyCommand {

    private String[] helpPageOne = {
            "&9----------------&e PartlyFancy Commands&9 ----------------",
            "&7[] optional argument - () alias - <> required argument",
            " ",
            "&e/fancy help(h) [page]&7 - Display the help pages.",
            "&e/fancy inventory(i)&7 - Open the cosmetic menu."
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

                if (FancyUtil.isInteger(args[1])) {
                    int page = Integer.parseInt(args[1]);

                    if (page == 1) {

                        for (String line : helpPageOne) {
                            fancyPlayer.sendMessage(line);
                        }

                    } else {

                        fancyPlayer.sendMessage(PartlyFancy.getValue("message.command.page-does-not-exist",
                                "%player%-" + player.getDisplayName()));
                    }
                    return 1;
                }
            }
        } else {

            fancyPlayer.sendMessage(PartlyFancy.getValue("message.command.no-permission",
                    "%player%-" + player.getDisplayName(), "%perm%-" + permission().getName()));
        }

        // return 0 if used incorrectly
        return 0;
    }

    @Override
    public String[] subCommands() {
        return new String[]{"help", "h"};
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.command.help");
    }
}

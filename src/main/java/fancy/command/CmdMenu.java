package fancy.command;

import fancy.FancyPlayer;
import fancy.menu.FancyMenuLoader;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdMenu implements FancyCommandLoader.FancyCommand {

    @Override
    public int run(Player player, String[] args) {
        FancyPlayer fancyPlayer = FancyPlayer.getFancyPlayer(player);

        if (player.hasPermission(permission())) {
            FancyMenuLoader.openMenu(player, FancyMenuLoader.getFromId(FancyMenuLoader.FancyMenuIds.MAIN.getId()), true);
            return 1;
        } else {
            fancyPlayer.sendMessage(true, fancy.PartlyFancy.getStringValue("message.command.no-permission", "%player%-" + player.getDisplayName(), "%perm%-" + permission().getName()));
        }
        return 0;
    }

    @Override
    public String[] subCommands() {
        return new String[]{"menu", "m"};
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.command.menu", "PartlyFancy menu command permission.");
    }
}

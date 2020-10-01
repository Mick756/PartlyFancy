package fancy.command;

import fancy.FancyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdPermissions implements FancyCommandLoader.FancyCommand {


    @Override
    public int run(Player player, String[] args) {
        FancyPlayer fancyPlayer = FancyPlayer.getFancyPlayer(player);
        if (player.hasPermission(permission())) {
            Bukkit.getPluginManager().getPermissions().forEach(permission -> {
                if (permission.getName().startsWith("fancy.")) {
                    fancyPlayer.sendMessage(false, "&a" + permission.getName() + "&7: " + permission.getDescription());
                }
            });
            return 1;
        } else {
            fancyPlayer.sendMessage(true, fancy.PartlyFancy.getStringValue("message.command.no-permission", "%player%-" + player.getDisplayName(), "%perm%-" + permission().getName()));
        }
        return 0;
    }

    @Override
    public String[] subCommands() {
        return new String[]{"perms", "prms"};
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.command.permissions", "PartlyFancy permissions command permission.");
    }
}

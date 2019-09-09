package fancy.command;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class CmdMenu implements FancyCommandLoader.FancyCommand {

    @Override
    public int run(Player player, String[] args) {
        
        return 0;
    }

    @Override
    public String[] subCommands() {
        return new String[]{"menu", "m"};
    }

    @Override
    public Permission permission() {
        return new Permission("fancy.command.menu", "PartlyFancy menu command permission.", PermissionDefault.FALSE);
    }
}

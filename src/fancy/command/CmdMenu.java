package fancy.command;

import fancy.menu.FancyMenuLoader;
import fancy.menu.menus.MainMenu;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CmdMenu implements FancyCommandLoader.FancyCommand {

    @Override
    public int run(Player player, String[] args) {
        FancyMenuLoader.openMenu(player, new MainMenu(true), true);
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

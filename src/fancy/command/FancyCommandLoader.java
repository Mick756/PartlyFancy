package fancy.command;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class FancyCommandLoader {

    public static FancyCommand[] commands = {new CmdHelp()};

    public static int runCommand(Player player, String... args) {
        for (FancyCommand command : commands) {
            for (int i = 0; i < command.subCommands().length; i++) {
                if (command.subCommands()[i].equalsIgnoreCase(args[0])) {
                    return command.run(player, args);
                }
            }
        }
        return -1;
    }

    public interface FancyCommand {

        /**
         * @param player
         * @param args
         * @return
         */
        int run(Player player, String[] args);

        /**
         * @return first argument.
         * Ex. /fancy help
         * command.argument() = "help"
         */
        String[] subCommands();

        String usage();

        Permission permission();

    }
}

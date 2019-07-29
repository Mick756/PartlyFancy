package fancy.command;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.List;

public class FancyCommandLoader {

    public static List<FancyCommand> commands = new ArrayList<>();

    public static int runCommand(Player player, String... args) {
        for (FancyCommand command : commands) {
            if (command.argument().equalsIgnoreCase(args[0])) {
                return command.run(player);
            }
        }
        return -1;
    }

    public interface FancyCommand {

        int run(Player player);

        /**
         * @return first argument.
         * Ex. /fancy help
         * command.argument() = "help"
         */
        String argument();

        String usage();

        Permission permission();

    }
}

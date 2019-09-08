package fancy.command;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FancyCommandLoader {

    /*
     To add a command from an external plugin, add an interface implementing FancyCommand to this array
     */
    private static List<FancyCommand> commands = new ArrayList<>();

    // Add default commands statically
    static {
        addFancyCommand(new CmdHelp());
    }

    /**
     * A function to execute command that searches through all available commands
     * @param player Player that ran the command (As you can see, ConsoleSender is NOT permitted)
     * @param args   Arguments to pass into the commands
     * @return       An error ID int (-1 = not found, 0 = used incorrectly, 1 = successful)
     */
    public static int runCommand(Player player, String... args) {
        // Cycle through available commands
        for (FancyCommand command : commands) {

            // Cycle through sub-commands of each command
            for (String sub_command : command.subCommands()) {

                // If sub_command equals first argument the command is exectued
                if (sub_command.equalsIgnoreCase(args[0])) {
                    return command.run(player, args);
                }
            }
        }
        return -1;
    }

    /**
     * Add a fancy command to the registry
     * @param fancyCommands Add any amount of FancyCommands at once
     */
    public static void addFancyCommand(FancyCommand... fancyCommands) {
        Collections.addAll(commands, fancyCommands);
    }

    public interface FancyCommand {

        int run(Player player, String[] args);

        String[] subCommands();

        Permission permission();

    }
}

package fancy.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FancyCommandLoader {
    
    private static List<FancyCommand> commands = new ArrayList<>();

    static {
        
        addFancyCommand(new CmdHelp(), new CmdPermissions(), new CmdMenu());
        
        commands.forEach(command -> {
            Bukkit.getPluginManager().addPermission(command.permission());
        });
    }
    
    public static int runCommand(Player player, String... args) {
        for (FancyCommand command : commands) {
            for (String sub_command : command.subCommands()) {
                if (sub_command.equalsIgnoreCase(args[0])) {
                    return command.run(player, args);
                }
            }
        }
        return -1;
    }

    public static void addFancyCommand(FancyCommand... fancyCommands) {
        Collections.addAll(commands, fancyCommands);
        
    }

    public interface FancyCommand {

        int run(Player player, String[] args);

        String[] subCommands();

        Permission permission();

    }
}

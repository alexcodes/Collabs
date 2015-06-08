package com.collabs.server.command.console;

import com.collabs.server.command.CommandException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class CommandFactory implements ConsoleConstants {
    private static Map<String, Class<? extends ConsoleCommand>> commands = new ConcurrentHashMap<String, Class<? extends ConsoleCommand>>();

    static {
        commands.put(STOP, ExitCommand.class);
        commands.put(HELP, HelpCommand.class);
    }

    public static ConsoleCommand get(String line) throws CommandException {
        if (! isValid(line)) {
            throw new CommandException("Command cannot be empty");
        }
        line = line.trim();
        String name = getName(line);
        if (commands.containsKey(name)) {
            try {
                return commands.get(name).newInstance();
            } catch (Exception e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            throw new CommandException("Unknown command");
        }
    }

    public static Collection<Class<? extends ConsoleCommand>> getCommands() {
        return commands.values();
    }

    private static String getName(String line) throws CommandException {
        if (line == null || line.isEmpty()) {
            throw new CommandException("Empty line received");
        }
        String name = line.split(" ")[0];
        if (! isValid(name)) {
            throw new CommandException("Invalid command name");
        }
        return name;
    }

    private static boolean isValid(String s) {
        return !(s == null || s.isEmpty() || s.trim().isEmpty());
    }
}

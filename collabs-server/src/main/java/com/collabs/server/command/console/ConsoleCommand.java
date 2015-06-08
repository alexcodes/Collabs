package com.collabs.server.command.console;

import com.collabs.server.command.Command;

/**
 * @author Aleksey A.
 */
public interface ConsoleCommand extends Command, ConsoleConstants {
    String getName();
    String getHelp();
}

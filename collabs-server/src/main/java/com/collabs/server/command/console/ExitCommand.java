package com.collabs.server.command.console;

/**
 * @author Aleksey A.
 */
public class ExitCommand implements ConsoleCommand {
    @Override
    public String getName() {
        return STOP;
    }

    @Override
    public String getHelp() {
        return "stop\t\tStops server";
    }

    @Override
    public void execute() throws Exception {
        System.out.println("Stopping server...");
        //save necessary data
        //close all resources
        //close all connections
        System.exit(0);
    }
}

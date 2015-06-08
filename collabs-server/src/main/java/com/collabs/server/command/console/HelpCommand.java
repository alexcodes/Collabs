package com.collabs.server.command.console;

/**
 * @author Aleksey A.
 */
public class HelpCommand implements ConsoleCommand {
    @Override
    public String getName() {
        return HELP;
    }

    @Override
    public String getHelp() {
        return "help\t\tProvides help to the application";
    }

    @Override
    public void execute() throws Exception {
        System.out.println("\nSupported commands:");
        for (Class<? extends ConsoleCommand> commandClass : CommandFactory.getCommands()) {
            System.out.println(commandClass.newInstance().getHelp());
        }
        System.out.println();
    }
}

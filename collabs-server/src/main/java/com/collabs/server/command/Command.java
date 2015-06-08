package com.collabs.server.command;

/**
 * Common interface for all commands on server side.
 *
 * @author Aleksey A.
 */
public interface Command {
    void execute() throws Exception;
}

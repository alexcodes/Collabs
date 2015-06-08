package com.collabs.server;

import com.collabs.server.command.Command;
import com.collabs.server.command.CommandException;
import com.collabs.server.command.LoadPlugins;
import com.collabs.server.command.console.CommandFactory;
import com.collabs.server.command.console.ConsoleCommand;
import com.collabs.server.core.Server;
import com.collabs.server.core.ServerHandler;
import com.collabs.server.core.http.HttpServer;

import java.io.*;

/**
 * Starts the server from console
 *
 * @author Aleksey A.
 */
public class Main {
    public static void main(String[] args) {
        startServer(args);
        startHttpServer();
        loadPlugins();
        listenCommands(System.in);
    }

    private static void startServer(String[] args) {
        Server server;
        if (args != null && args.length > 0) {
            int port = extractPort(args[0]);
            if (port > 1000) {
                server = new Server(port, ServerHandler.class);
            } else {
                System.out.println("Port number should be in range 1000-65535");
                return;
            }
        } else {
            server = new Server(ServerHandler.class);
        }
        Thread thread = new Thread(server);
        thread.start();
    }

    private static void startHttpServer() {
        HttpServer server = new HttpServer();
        Thread thread = new Thread(server);
        thread.start();
    }

    private static int extractPort(String s) {
        int port;
        try {
            port = Integer.parseInt(s);
        } catch (Exception e) {
            port = -1;
        }
        return port;
    }

    private static void loadPlugins() {
        File file = new File("");
        String path = file.getAbsolutePath();
        path += (path.endsWith(File.separator) ? "" : File.separator) + "plugins";
        Command command = new LoadPlugins(path);
        try {
            command.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("all")
    private static void listenCommands(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while (true) {
            System.out.print(">");
            try {
                line = reader.readLine();
                if (line != null) {
                    ConsoleCommand command = CommandFactory.get(line);
                    command.execute();
                }
            } catch (IOException e) {
                System.out.println("Cannot read from console");
            } catch (CommandException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

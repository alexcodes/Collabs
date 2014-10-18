package collabs.connection;

import collabs.connection.server.Server;
import collabs.connection.server.ServerCore;

/*
 *  This class is used only as server example
 */
public class ServerMain {
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            Server server = new ServerCore(Integer.parseInt(args[0]));
            new Thread(server).start();
        } else {
            Server server = new ServerCore();
            new Thread(server).start();
        }
    }
}

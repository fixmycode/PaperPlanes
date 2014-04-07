package cl.blackbird.paper;

import cl.blackbird.paper.server.Server;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    private static Logger LOG = Logger.getLogger("cl.blackbird.paper");

    public static void main(String[] args){
        int port = 7070;
        Server server;
        try {
            port = Integer.parseInt(args[0]);
        } catch (IndexOutOfBoundsException e){
            LOG.fine("Using port 7070.");
        } catch (NumberFormatException e){
            LOG.warning("Invalid Port. Server will fallback to 7070.");
        }
        do {
            server = new Server(port);
            try {
                server.start();
            } catch (IOException e) {
                server = null;
                port++;
            }
        } while (server == null);
    }
}

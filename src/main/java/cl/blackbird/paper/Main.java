package cl.blackbird.paper;

import cl.blackbird.paper.api.APIConfiguration;
import cl.blackbird.paper.server.Server;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    private static Logger LOG = Logger.getLogger("cl.blackbird.paper");

    public static void main(String[] args){
        int port = 7070;
        Server.setConfiguration(new APIConfiguration());
        Server server = new Server();
        try {
            port = Integer.parseInt(args[0]);
        } catch (IndexOutOfBoundsException e){
            LOG.fine("Using port 7070.");
        } catch (NumberFormatException e){
            LOG.warning("Invalid Port. Server will fallback to 7070.");
        }
        do {
            try {
                server.start();
            } catch (IOException e) {
                server.setPort(++port);
            }
        } while (!server.isListening());
    }
}

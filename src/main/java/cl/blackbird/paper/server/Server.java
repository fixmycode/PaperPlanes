package cl.blackbird.paper.server;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * El programa que crea la puerta de conexión con la red via HTTP. Esta clase en particular se encarga de levantar el
 * servidor en un hilo del sistema y de crear un nuevo hilo por cada conexión entrante.
 */
public class Server {
    private static Configuration configuration;
    private int port;
    private boolean listening = false;

    public Server(int port){
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println(String.format("Server started at port %d", port));
        listening = true;
        while (listening) {
            new ServerThread(socket.accept()).start();
        }
    }

    public void stop(){
        this.listening = false;
        System.out.println("Server stopped");
    }

    public static void setConfiguration(Configuration config){
        Server.configuration = config;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}

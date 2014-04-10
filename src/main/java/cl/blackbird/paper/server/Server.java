package cl.blackbird.paper.server;


import java.io.IOException;
import java.net.ServerSocket;

/**
 * El programa que crea la puerta de conexión con la red via HTTP. Esta clase en particular se encarga de levantar el
 * servidor en un hilo del sistema y de crear un nuevo hilo por cada conexión entrante.
 */
public class Server implements Runnable {
    private static Configuration configuration;
    private Integer port;
    private boolean listening;

    public Server(){
        this(null);
    }

    public Server(Integer port){
        this.listening = false;
        this.setPort(port);
    }

    public void start(int port) throws IOException {
        this.setPort(port);
        ServerSocket socket = new ServerSocket(port);
        System.out.println(String.format("Server started at port %d", port));
        listening = true;
        while (listening) {
            new ServerThread(socket.accept()).start();
        }
    }

    public void start() throws IOException {
        if(port != null){
            this.start(this.port);
        } else {
            this.start(7070);
        }
    }

    public boolean isListening(){
        return listening;
    }

    public void setPort(Integer port){
        if(!listening){
            this.port = port;
        }
    }

    public Integer getPort(){
        return this.port;
    }

    public void stop(){
        this.listening = false;
        System.out.println(String.format("Server stopped listening to port %d", port));
    }

    public static void setConfiguration(Configuration config){
        Server.configuration = config;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            System.out.println("System couldn't start...");
            e.printStackTrace();
        }
    }
}

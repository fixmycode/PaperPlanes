package cl.blackbird.paper.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
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
}

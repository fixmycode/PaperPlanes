package cl.blackbird.paper.server;

import cl.blackbird.paper.protocol.Request;
import cl.blackbird.paper.protocol.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket = null;

    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }

    public void run(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Response response = Request.createFromInput(reader).handle();
            response.sendToWriter(writer);
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
